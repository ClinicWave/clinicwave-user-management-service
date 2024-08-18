package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.VerificationCodeTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.VerificationCodeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the VerificationCode entity.
 * It uses JUnit 5 and Spring Boot's testing capabilities.
 * It tests the basic CRUD operations for the VerificationCode entity.
 *
 * @author aamir on 7/8/24
 */
@SpringBootTest
class VerificationCodeTest {
  private final VerificationCodeRepository verificationCodeRepository;
  private final ClinicWaveUserRepository clinicWaveUserRepository;
  private VerificationCode verificationCode;
  private ClinicWaveUser user;

  /**
   * Constructor for dependency injection.
   *
   * @param verificationCodeRepository The repository for VerificationCode entities.
   * @param clinicWaveUserRepository   The repository for ClinicWaveUser entities.
   */
  @Autowired
  public VerificationCodeTest(VerificationCodeRepository verificationCodeRepository, ClinicWaveUserRepository clinicWaveUserRepository) {
    this.verificationCodeRepository = verificationCodeRepository;
    this.clinicWaveUserRepository = clinicWaveUserRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a new VerificationCode entity and a new ClinicWaveUser entity.
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
    clinicWaveUserRepository.save(user);

    verificationCode = new VerificationCode();
    verificationCode.setCode("123456");
    verificationCode.setType(VerificationCodeTypeEnum.EMAIL_VERIFICATION);
    verificationCode.setToken("123456");
    verificationCode.setClinicWaveUser(user);
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all VerificationCode and ClinicWaveUser entities from the repository.
   */
  @AfterEach
  void tearDown() {
    verificationCodeRepository.deleteAll();
    clinicWaveUserRepository.deleteAll();
  }

  @Test
  @DisplayName("Verification code should be saved to repository")
  void testSaveVerificationCode() {
    verificationCodeRepository.save(verificationCode);
    assertNotNull(verificationCode.getId());
  }

  @Test
  @DisplayName("Verification code should be retrieved from repository")
  void testGetVerificationCode() {
    verificationCodeRepository.save(verificationCode);
    VerificationCode savedVerificationCode = verificationCodeRepository.findById(verificationCode.getId()).orElse(null);
    assertNotNull(savedVerificationCode);
    assertEquals(verificationCode.getCode(), savedVerificationCode.getCode());
    assertEquals(verificationCode.getType(), savedVerificationCode.getType());
    assertEquals(verificationCode.getToken(), savedVerificationCode.getToken());
    assertEquals(verificationCode.getClinicWaveUser().getId(), savedVerificationCode.getClinicWaveUser().getId());
  }

  @Test
  @DisplayName("Verification code should be deleted from repository")
  void testDeleteVerificationCode() {
    verificationCodeRepository.save(verificationCode);
    verificationCodeRepository.deleteById(verificationCode.getId());
    VerificationCode deletedVerificationCode = verificationCodeRepository.findById(verificationCode.getId()).orElse(null);
    assertNull(deletedVerificationCode);
  }

  @Test
  @DisplayName("Should find all verification codes for a user")
  void shouldFindAllVerificationCodesForUser() {
    verificationCodeRepository.save(verificationCode);
    VerificationCode anotherCode = new VerificationCode();
    anotherCode.setCode("654321");
    anotherCode.setType(VerificationCodeTypeEnum.PASSWORD_RESET);
    anotherCode.setToken("654321");
    anotherCode.setClinicWaveUser(user);
    verificationCodeRepository.save(anotherCode);

    List<VerificationCode> verificationCodeList = verificationCodeRepository.findAllByClinicWaveUser(user);
    assertEquals(2, verificationCodeList.size());
  }
}