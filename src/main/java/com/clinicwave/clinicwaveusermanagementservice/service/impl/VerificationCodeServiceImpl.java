package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.InvalidVerificationCodeException;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeAlreadyUsedException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeExpiredException;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.VerificationCodeRepository;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * This class implements the VerificationCodeService interface and provides methods to generate verification codes for users.
 * It uses the VerificationCodeRepository to interact with the database.
 * The class is annotated with @Service to indicate that it is a service component in the Spring framework.
 *
 * @author aamir on 7/7/24
 */
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
  private static final String VERIFICATION_CODE = "VerificationCode";
  private static final String CODE = "code";
  private static final String CLINIC_WAVE_USER = "clinicWaveUser";
  private static final String CLINIC_WAVE_USER_AND_TYPE = "clinicWaveUser and type";
  private static final String EMAIL = "email";

  private final VerificationCodeRepository verificationCodeRepository;
  private final ClinicWaveUserRepository clinicWaveUserRepository;

  /**
   * Constructs a new VerificationCodeServiceImpl with the given VerificationCodeRepository.
   *
   * @param verificationCodeRepository the VerificationCodeRepository to be used for database operations
   * @param clinicWaveUserRepository   the ClinicWaveUserRepository to be used for database operations
   */
  @Autowired
  public VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository, ClinicWaveUserRepository clinicWaveUserRepository) {
    this.verificationCodeRepository = verificationCodeRepository;
    this.clinicWaveUserRepository = clinicWaveUserRepository;
  }

  /**
   * Generates a verification code for the specified user and verification code type.
   *
   * @param clinicWaveUser       the user for whom the verification code is generated
   * @param verificationCodeType the type of verification code to be generated
   * @return the generated verification code
   */
  @Override
  public VerificationCode getVerificationCode(ClinicWaveUser clinicWaveUser, VerificationCodeTypeEnum verificationCodeType) {
    VerificationCode verificationCode = new VerificationCode();
    verificationCode.setCode(generateRandomCode());
    verificationCode.setType(verificationCodeType);
    verificationCode.setClinicWaveUser(clinicWaveUser);
    return verificationCodeRepository.save(verificationCode);
  }

  /**
   * Verifies the account of the user with the specified email using the verification code provided in the request.
   *
   * @param verificationRequestDto the verification request containing the email and verification code
   */
  @Override
  public void verifyAccount(VerificationRequestDto verificationRequestDto) {
    ClinicWaveUser clinicWaveUser = findClinicWaveUserByEmail(verificationRequestDto.email());
    VerificationCode verificationCode = findMostRecentVerificationCode(clinicWaveUser, VerificationCodeTypeEnum.EMAIL_VERIFICATION);

    incrementAttemptCount(verificationCode);
    validateVerificationCode(verificationCode, verificationRequestDto.code());
    markVerificationCodeAsUsed(verificationCode);
    updateUserStatus(clinicWaveUser);
  }

  /**
   * Increments the attempt count of the specified verification code.
   *
   * @param verificationCode the verification code whose attempt count is to be incremented
   */
  private void incrementAttemptCount(VerificationCode verificationCode) {
    verificationCode.setAttemptCount(verificationCode.getAttemptCount() + 1);
    verificationCodeRepository.save(verificationCode);
  }

  /**
   * Validates the verification code submitted by the user.
   *
   * @param verificationCode the verification code to be validated
   * @param submittedCode    the verification code submitted by the user
   * @throws InvalidVerificationCodeException     if the submitted code is invalid
   * @throws VerificationCodeAlreadyUsedException if the verification code has already been used
   * @throws VerificationCodeExpiredException     if the verification code has expired
   */
  private void validateVerificationCode(VerificationCode verificationCode, String submittedCode) {
    if (!verificationCode.getCode().equals(submittedCode)) {
      throw new InvalidVerificationCodeException(VERIFICATION_CODE, CODE, submittedCode);
    }

    if (verificationCode.getIsUsed() || verificationCode.getIsVerified()) {
      throw new VerificationCodeAlreadyUsedException(VERIFICATION_CODE, CODE, submittedCode);
    }

    if (verificationCode.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new VerificationCodeExpiredException(VERIFICATION_CODE, CODE, submittedCode);
    }
  }

  /**
   * Marks the specified verification code as used and verified.
   *
   * @param verificationCode the verification code to be marked as used and verified
   */
  private void markVerificationCodeAsUsed(VerificationCode verificationCode) {
    verificationCode.setIsUsed(true);
    verificationCode.setIsVerified(true);
    verificationCode.setVerifiedAt(LocalDateTime.now());
    verificationCodeRepository.save(verificationCode);
  }

  /**
   * Updates the status of the specified user to 'VERIFIED'.
   *
   * @param clinicWaveUser the user whose status is to be updated
   */
  private void updateUserStatus(ClinicWaveUser clinicWaveUser) {
    clinicWaveUser.setStatus(UserStatusEnum.VERIFIED);
    clinicWaveUserRepository.save(clinicWaveUser);
    log.info("User {} has been verified successfully", clinicWaveUser.getUsername());
  }

  /**
   * Finds the most recent verification code of the specified type for the specified user.
   *
   * @param clinicWaveUser the user whose verification code is to be found
   * @param type           the type of verification code to be found
   * @return the most recent verification code of the specified type for the specified user
   * @throws ResourceNotFoundException if the most recent verification code is not found
   */
  private VerificationCode findMostRecentVerificationCode(ClinicWaveUser clinicWaveUser, VerificationCodeTypeEnum type) {
    return verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(clinicWaveUser, type)
            .orElseThrow(() -> new ResourceNotFoundException(
                    VERIFICATION_CODE,
                    CLINIC_WAVE_USER_AND_TYPE, clinicWaveUser.getUsername() + " and " + type)
            );
  }

  /**
   * Finds the ClinicWaveUser with the specified email.
   *
   * @param email the email of the user to be found
   * @return the ClinicWaveUser with the specified email
   * @throws ResourceNotFoundException if the user with the specified email is not found
   */
  private ClinicWaveUser findClinicWaveUserByEmail(String email) {
    return clinicWaveUserRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(
                    CLINIC_WAVE_USER, EMAIL, email)
            );
  }

  /**
   * Generates a random 6-digit verification code.
   *
   * @return the generated verification code
   */
  private String generateRandomCode() {
    return String.format("%06d", new SecureRandom().nextInt(999999));
  }
}
