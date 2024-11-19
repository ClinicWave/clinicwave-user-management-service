package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.enums.UserTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.repository.UserTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the UserType entity.
 * It uses JUnit 5 and Spring Boot's testing capabilities.
 * It tests the basic CRUD operations for the UserType entity.
 *
 * @author aamir on 6/20/24
 */
@SpringBootTest
class UserTypeTest {
  private final UserTypeRepository userTypeRepository;
  private UserType userType;

  /**
   * Constructor for dependency injection.
   *
   * @param userTypeRepository The repository for UserType entities.
   */
  @Autowired
  public UserTypeTest(UserTypeRepository userTypeRepository) {
    this.userTypeRepository = userTypeRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a new UserType entity.
   */
  @BeforeEach
  void setUp() {
    userTypeRepository.deleteAll();
    userType = new UserType();
    userType.setType(UserTypeEnum.USER_TYPE_DEFAULT);
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all UserType entities from the repository.
   */
  @AfterEach
  void tearDown() {
    userTypeRepository.deleteAll();
  }

  @Test
  @DisplayName("UserType ID should be set after saving to repository")
  void userTypeIdShouldBeSetAfterSavingToRepository() {
    userTypeRepository.save(userType);
    assertNotNull(userType.getId());
  }

  @Test
  @DisplayName("UserType ID should be set and retrieved correctly")
  void userTypeIdShouldBeSetAndRetrievedCorrectly() {
    Long id = 1L;
    userType.setId(id);
    assertEquals(id, userType.getId());
  }

  @Test
  @DisplayName("UserType should be saved and retrieved from repository")
  void userTypeShouldBeSavedAndRetrievedFromRepository() {
    userTypeRepository.save(userType);
    Optional<UserType> retrievedUserType = userTypeRepository.findById(userType.getId());
    assertTrue(retrievedUserType.isPresent());
    assertEquals(userType.getId(), retrievedUserType.get().getId());
    assertEquals(userType.getType(), retrievedUserType.get().getType());
  }

  @Test
  @DisplayName("UserType should be deleted from repository")
  void userTypeShouldBeDeletedFromRepository() {
    userTypeRepository.save(userType);
    userTypeRepository.delete(userType);
    Optional<UserType> retrievedUserType = userTypeRepository.findById(userType.getId());
    assertFalse(retrievedUserType.isPresent());
  }

  @Test
  @DisplayName("Duplicate UserType should throw an exception")
  void duplicateUserTypeShouldThrowException() {
    userTypeRepository.save(userType);
    UserType duplicateUserType = new UserType();
    duplicateUserType.setType(UserTypeEnum.USER_TYPE_DEFAULT);
    assertThrows(Exception.class, () -> userTypeRepository.save(duplicateUserType));
  }
}