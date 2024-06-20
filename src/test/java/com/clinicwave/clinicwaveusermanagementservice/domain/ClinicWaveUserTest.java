package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.UserTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the ClinicWaveUser entity.
 * It uses JUnit 5 and Spring Boot's testing capabilities.
 * It tests the basic CRUD operations for the ClinicWaveUser entity.
 *
 * @author aamir on 6/20/24
 */
@ActiveProfiles("h2")
@SpringBootTest
class ClinicWaveUserTest {
  private final ClinicWaveUserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserTypeRepository userTypeRepository;
  private ClinicWaveUser user;

  /**
   * Constructor for dependency injection.
   *
   * @param userRepository     The repository for ClinicWaveUser entities.
   * @param roleRepository     The repository for Role entities.
   * @param userTypeRepository The repository for UserType entities.
   */
  @Autowired
  public ClinicWaveUserTest(ClinicWaveUserRepository userRepository, RoleRepository roleRepository, UserTypeRepository userTypeRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userTypeRepository = userTypeRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a new ClinicWaveUser entity.
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
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all ClinicWaveUser entities from the repository.
   */
  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("User ID should be set after saving to repository")
  void userIdShouldBeSetAfterSavingToRepository() {
    userRepository.save(user);
    assertNotNull(user.getId());
  }

  @Test
  @DisplayName("User ID should be set and retrieved correctly")
  void userIdShouldBeSetAndRetrievedCorrectly() {
    Long id = 1L;
    user.setId(id);
    assertEquals(id, user.getId());
  }

  @Test
  @DisplayName("User should be saved and retrieved from repository")
  void userShouldBeSavedAndRetrievedFromRepository() {
    userRepository.save(user);
    Optional<ClinicWaveUser> retrievedUser = userRepository.findById(user.getId());
    assertTrue(retrievedUser.isPresent());
    assertEquals(user.getId(), retrievedUser.get().getId());
    assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
    assertEquals(user.getLastName(), retrievedUser.get().getLastName());
    assertEquals(user.getMobileNumber(), retrievedUser.get().getMobileNumber());
    assertEquals(user.getUsername(), retrievedUser.get().getUsername());
    assertEquals(user.getEmail(), retrievedUser.get().getEmail());
    assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
    assertEquals(user.getGender(), retrievedUser.get().getGender());
  }

  @Test
  @DisplayName("User role and userType should be saved and retrieved correctly")
  void userRoleAndUserTypeShouldBeSavedAndRetrievedCorrectly() {
    Role role = new Role();
    role.setRoleName("TEST_ROLE");
    Role savedRole = roleRepository.save(role);

    UserType userType = new UserType();
    userType.setType("TEST_TYPE");
    UserType savedUserType = userTypeRepository.save(userType);

    user.setRole(savedRole);
    user.setUserType(savedUserType);

    userRepository.save(user);
    Optional<ClinicWaveUser> retrievedUser = userRepository.findById(user.getId());
    assertTrue(retrievedUser.isPresent());
    assertEquals(user.getId(), retrievedUser.get().getId());
    assertEquals(user.getRole().getRoleName(), retrievedUser.get().getRole().getRoleName());
    assertEquals(user.getUserType().getType(), retrievedUser.get().getUserType().getType());
  }

  @Test
  @DisplayName("User should be deleted from repository")
  void userShouldBeDeletedFromRepository() {
    userRepository.save(user);
    userRepository.delete(user);
    Optional<ClinicWaveUser> retrievedUser = userRepository.findById(user.getId());
    assertFalse(retrievedUser.isPresent());
  }

  @Test
  @DisplayName("Duplicate user should throw an exception")
  void duplicateUserShouldThrowException() {
    userRepository.save(user);
    ClinicWaveUser duplicateUser = new ClinicWaveUser();
    duplicateUser.setUsername("testuser");
    assertThrows(Exception.class, () -> userRepository.save(duplicateUser));
  }
}