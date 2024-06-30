package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.enums.*;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.PermissionRepository;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
  private final PermissionRepository permissionRepository;
  private final UserTypeRepository userTypeRepository;

  private ClinicWaveUser user;
  private Role role;
  private Permission permission;
  private RolePermission rolePermission;
  private UserType userType;

  /**
   * Constructor for dependency injection.
   *
   * @param userRepository       The repository for ClinicWaveUser entities.
   * @param roleRepository       The repository for Role entities.
   * @param permissionRepository The repository for Permission entities.
   * @param userTypeRepository   The repository for UserType entities.
   */
  @Autowired
  public ClinicWaveUserTest(ClinicWaveUserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, UserTypeRepository userTypeRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
    this.userTypeRepository = userTypeRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a ClinicWaveUser, Role, Permission, RolePermission, and UserType entity.
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

    role = new Role();
    role.setRoleName(RoleNameEnum.ROLE_DEFAULT);

    permission = new Permission();
    permission.setPermissionName(PermissionNameEnum.PERMISSION_READ);

    rolePermission = new RolePermission();
    rolePermission.setRole(role);
    rolePermission.setPermission(permission);

    userType = new UserType();
    userType.setType(UserTypeEnum.USER_TYPE_DEFAULT);
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all ClinicWaveUser, Role, Permission, and UserType entities from the repository.
   */
  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
    permissionRepository.deleteAll();
    userTypeRepository.deleteAll();
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
    assertEquals(user.getStatus(), retrievedUser.get().getStatus());
  }

  @Test
  @DisplayName("User role, user type, and permissions should be saved and retrieved correctly")
  void userRoleUserTypeAndPermissionShouldBeSavedAndRetrievedCorrectly() {
    permissionRepository.save(permission);

    Set<RolePermission> rolePermissionSet = new HashSet<>();
    rolePermissionSet.add(rolePermission);

    role.setRolePermissionSet(rolePermissionSet);
    roleRepository.save(role);

    userTypeRepository.save(userType);

    user.setRole(role);
    user.setUserType(userType);

    userRepository.save(user);

    Optional<ClinicWaveUser> retrievedUser = userRepository.findById(user.getId());
    assertTrue(retrievedUser.isPresent());
    assertEquals(user.getId(), retrievedUser.get().getId());
    assertEquals(user.getRole().getId(), retrievedUser.get().getRole().getId());
    assertEquals(user.getRole().getRoleName(), retrievedUser.get().getRole().getRoleName());
    assertEquals(user.getRole().getRoleDescription(), retrievedUser.get().getRole().getRoleDescription());
    assertEquals(user.getUserType().getId(), retrievedUser.get().getUserType().getId());
    assertEquals(user.getUserType().getType(), retrievedUser.get().getUserType().getType());
    assertEquals(user.getRole().getRolePermissionSet().size(), retrievedUser.get().getRole().getRolePermissionSet().size());
    assertEquals(user.getRole().getRolePermissionSet().iterator().next().getRole().getId(), retrievedUser.get().getRole().getRolePermissionSet().iterator().next().getRole().getId());
    assertEquals(user.getRole().getRolePermissionSet().iterator().next().getPermission().getId(), retrievedUser.get().getRole().getRolePermissionSet().iterator().next().getPermission().getId());
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