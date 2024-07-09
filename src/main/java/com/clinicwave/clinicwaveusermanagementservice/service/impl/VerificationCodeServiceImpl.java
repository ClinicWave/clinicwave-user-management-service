package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.repository.VerificationCodeRepository;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * This class implements the VerificationCodeService interface and provides methods to generate verification codes for users.
 * It uses the VerificationCodeRepository to interact with the database.
 * The class is annotated with @Service to indicate that it is a service component in the Spring framework.
 *
 * @author aamir on 7/7/24
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
  private final VerificationCodeRepository verificationCodeRepository;

  /**
   * Constructs a new VerificationCodeServiceImpl with the given VerificationCodeRepository.
   *
   * @param verificationCodeRepository the VerificationCodeRepository to be used for database operations
   */
  @Autowired
  public VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository) {
    this.verificationCodeRepository = verificationCodeRepository;
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
   * Generates a random 6-digit verification code.
   *
   * @return the generated verification code
   */
  private String generateRandomCode() {
    return String.format("%06d", new SecureRandom().nextInt(999999));
  }
}
