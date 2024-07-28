package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.VerificationCode;
import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.InvalidVerificationCodeException;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeAlreadyUsedException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeExpiredException;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.VerificationCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains the unit tests for the VerificationCodeServiceImpl class.
 * It uses Mockito to mock the VerificationCodeRepository and test the methods in the VerificationCodeServiceImpl class.
 * The tests verify that the getVerificationCode method generates a verification code for a user and a verification code type.
 *
 * @author aamir on 7/8/24
 */
@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceImplTest {
  @Mock
  private VerificationCodeRepository verificationCodeRepository;

  @Mock
  private ClinicWaveUserRepository clinicWaveUserRepository;

  @InjectMocks
  private VerificationCodeServiceImpl verificationCodeService;

  private ClinicWaveUser user;
  private VerificationCode verificationCode;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    user = new ClinicWaveUser();
    user.setFirstName("Test");
    user.setLastName("User");
    user.setMobileNumber("1234567890");
    user.setUsername("testuser");
    user.setEmail("testuser@example.com");
    user.setDateOfBirth(LocalDate.of(1990, 1, 1));
    user.setGender(GenderEnum.MALE);
    user.setStatus(UserStatusEnum.PENDING);

    verificationCode = new VerificationCode();
    verificationCode.setCode("123456");
    verificationCode.setType(VerificationCodeTypeEnum.PASSWORD_RESET);
    verificationCode.setClinicWaveUser(user);
  }

  @Test
  @DisplayName("Test Get verification code")
  void testGetVerificationCode() {
    when(verificationCodeRepository.save(any(VerificationCode.class))).thenAnswer(invocation -> invocation.getArgument(0));

    VerificationCode actualVerificationCode = verificationCodeService.getVerificationCode(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION);

    assertNotNull(actualVerificationCode);
    assertNotNull(actualVerificationCode.getCode());
    assertEquals(6, actualVerificationCode.getCode().length());
    assertTrue(actualVerificationCode.getCode().matches("\\d+"));
    assertEquals(VerificationCodeTypeEnum.EMAIL_VERIFICATION, actualVerificationCode.getType());
    assertEquals(user, actualVerificationCode.getClinicWaveUser());

    verify(verificationCodeRepository, times(1)).save(any(VerificationCode.class));
  }

  @Test
  @DisplayName("Test Generate unique random codes")
  void testGenerateUniqueRandomCodes() {
    when(verificationCodeRepository.save(any(VerificationCode.class))).thenAnswer(invocation -> invocation.getArgument(0));

    VerificationCode verificationCode1 = verificationCodeService.getVerificationCode(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION);
    VerificationCode verificationCode2 = verificationCodeService.getVerificationCode(user, VerificationCodeTypeEnum.PASSWORD_RESET);

    assertNotNull(verificationCode1.getCode());
    assertNotNull(verificationCode2.getCode());
    assertNotEquals(verificationCode1.getCode(), verificationCode2.getCode());
    assertEquals(6, verificationCode1.getCode().length());
    assertEquals(6, verificationCode2.getCode().length());
    assertTrue(verificationCode1.getCode().matches("\\d+"));
    assertTrue(verificationCode2.getCode().matches("\\d+"));
  }

  @Test
  @DisplayName("Test Verification code is linked to correct user")
  void verificationCodeIsLinkedToCorrectUser() {
    when(verificationCodeRepository.save(any(VerificationCode.class))).thenAnswer(invocation -> invocation.getArgument(0));

    VerificationCode actualVerificationCode = verificationCodeService.getVerificationCode(user, VerificationCodeTypeEnum.TWO_FACTOR_AUTHENTICATION);

    assertNotNull(actualVerificationCode.getClinicWaveUser());
    assertEquals(user.getId(), actualVerificationCode.getClinicWaveUser().getId());
  }

  @Test
  @DisplayName("verifyAccount returns VerificationRequestDto when verification is successful")
  void verifyAccountReturnsVerificationRequestDtoWhenVerificationIsSuccessful() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("testuser@example.com", "123456");

    when(clinicWaveUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
    when(verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION)).thenReturn(Optional.of(verificationCode));
    when(verificationCodeRepository.save(any(VerificationCode.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(clinicWaveUserRepository.save(any(ClinicWaveUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

    verificationCodeService.verifyAccount(verificationRequestDto);

    assertTrue(verificationCode.getIsUsed());
    assertTrue(verificationCode.getIsVerified());
    assertEquals(UserStatusEnum.VERIFIED, user.getStatus());
  }

  @Test
  @DisplayName("verifyAccount throws InvalidVerificationCodeException when code is invalid")
  void verifyAccountThrowsInvalidVerificationCodeExceptionWhenCodeIsInvalid() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("testuser@example.com", "654321");

    when(clinicWaveUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
    when(verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION)).thenReturn(Optional.of(verificationCode));

    assertThrows(InvalidVerificationCodeException.class, () -> verificationCodeService.verifyAccount(verificationRequestDto));
  }

  @Test
  @DisplayName("verifyAccount throws VerificationCodeAlreadyUsedException when code is already used")
  void verifyAccountThrowsVerificationCodeAlreadyUsedExceptionWhenCodeIsAlreadyUsed() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("testuser@example.com", "123456");

    verificationCode.setIsUsed(true);

    when(clinicWaveUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
    when(verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION)).thenReturn(Optional.of(verificationCode));

    assertThrows(VerificationCodeAlreadyUsedException.class, () -> verificationCodeService.verifyAccount(verificationRequestDto));
  }

  @Test
  @DisplayName("verifyAccount throws VerificationCodeExpiredException when code is expired")
  void verifyAccountThrowsVerificationCodeExpiredExceptionWhenCodeIsExpired() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("testuser@example.com", "123456");

    verificationCode.setExpiryDate(LocalDateTime.now().minusDays(1));

    when(clinicWaveUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
    when(verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION)).thenReturn(Optional.of(verificationCode));

    assertThrows(VerificationCodeExpiredException.class, () -> verificationCodeService.verifyAccount(verificationRequestDto));
  }

  @Test
  @DisplayName("verifyAccount throws ResourceNotFoundException when user is not found")
  void verifyAccountThrowsResourceNotFoundExceptionWhenUserIsNotFound() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("nonexistent@example.com", "123456");

    when(clinicWaveUserRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> verificationCodeService.verifyAccount(verificationRequestDto));
  }

  @Test
  @DisplayName("verifyAccount throws ResourceNotFoundException when verification code is not found")
  void verifyAccountThrowsResourceNotFoundExceptionWhenVerificationCodeIsNotFound() {
    VerificationRequestDto verificationRequestDto = new VerificationRequestDto("testuser@example.com", "123456");

    when(clinicWaveUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
    when(verificationCodeRepository.findTopByClinicWaveUserAndTypeOrderByCreatedAtDesc(user, VerificationCodeTypeEnum.EMAIL_VERIFICATION)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> verificationCodeService.verifyAccount(verificationRequestDto));
  }
}