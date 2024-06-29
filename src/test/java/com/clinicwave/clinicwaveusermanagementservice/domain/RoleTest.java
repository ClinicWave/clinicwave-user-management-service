package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.repository.PermissionRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the Role entity.
 * It uses JUnit 5 and Spring Boot's testing capabilities.
 * It tests the basic CRUD operations for the Role entity.
 *
 * @author aamir on 6/8/24
 */
@ActiveProfiles("h2")
@SpringBootTest
class RoleTest {
  private Role role;
  private Permission permission;

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  /**
   * Constructor for dependency injection.
   *
   * @param roleRepository       The repository for Role entities.
   * @param permissionRepository The repository for Permission entities.
   */
  @Autowired
  public RoleTest(RoleRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a new Role and Permission entity.
   */
  @BeforeEach
  void setUp() {
    role = new Role();
    role.setRoleName("TEST_ROLE");
    role.setRoleDescription("TEST_ROLE_DESCRIPTION");

    permission = new Permission();
    permission.setPermissionName("TEST_PERMISSION");
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all Role and Permission entities from the repository.
   */
  @AfterEach
  void tearDown() {
    roleRepository.deleteAll();
    permissionRepository.deleteAll();
  }

  @Test
  @DisplayName("Role ID should be set after saving to repository")
  void roleIdShouldBeSetAfterSavingToRepository() {
    roleRepository.save(role);
    assertNotNull(role.getId());
  }

  @Test
  @DisplayName("Role ID should be set and retrieved correctly")
  void roleIdShouldBeSetAndRetrievedCorrectly() {
    Long id = 1L;
    role.setId(id);
    assertEquals(id, role.getId());
  }

  @Test
  @DisplayName("Role should be saved and retrieved from repository")
  void roleShouldBeSavedAndRetrievedFromRepository() {
    roleRepository.save(role);
    Optional<Role> retrievedRole = roleRepository.findById(role.getId());
    assertTrue(retrievedRole.isPresent());
    assertEquals(role.getId(), retrievedRole.get().getId());
    assertEquals(role.getRoleName(), retrievedRole.get().getRoleName());
    assertEquals(role.getRoleDescription(), retrievedRole.get().getRoleDescription());
  }

  @Test
  @DisplayName("Role should be saved and retrieved from repository with role permissions")
  void roleShouldBeSavedAndRetrievedFromRepositoryWithRolePermissions() {
    permissionRepository.save(permission);

    RolePermission rolePermission = new RolePermission();
    rolePermission.setRole(role);
    rolePermission.setPermission(permission);

    Set<RolePermission> rolePermissions = new HashSet<>();
    rolePermissions.add(rolePermission);
    role.setRolePermissionSet(rolePermissions);

    roleRepository.save(role);

    Optional<Role> retrievedRole = roleRepository.findById(role.getId());
    assertTrue(retrievedRole.isPresent());
    Role retrievedRoleValue = retrievedRole.get();

    assertEquals(role.getId(), retrievedRoleValue.getId());
    assertEquals(role.getRoleName(), retrievedRoleValue.getRoleName());

    assertNotNull(retrievedRoleValue.getRolePermissionSet());
    assertEquals(1, retrievedRoleValue.getRolePermissionSet().size());

    RolePermission retrievedRolePermission = retrievedRoleValue.getRolePermissionSet().iterator().next();
    assertEquals(permission.getPermissionName(), retrievedRolePermission.getPermission().getPermissionName());
  }

  @Test
  @DisplayName("Role should be deleted from repository")
  void roleShouldBeDeletedFromRepository() {
    roleRepository.save(role);
    roleRepository.delete(role);
    Optional<Role> retrievedRole = roleRepository.findById(role.getId());
    assertFalse(retrievedRole.isPresent());
  }

  @Test
  @DisplayName("Duplicate role should throw an exception")
  void duplicateRoleShouldThrowException() {
    roleRepository.save(role);
    Role duplicateRole = new Role();
    duplicateRole.setRoleName("TEST_ROLE");
    assertThrows(Exception.class, () -> roleRepository.save(duplicateRole));
  }
}