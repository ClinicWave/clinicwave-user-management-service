package com.clinicwave.clinicwaveusermanagementservice.domain;

import com.clinicwave.clinicwaveusermanagementservice.repository.PermissionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a test class for the Permission entity.
 * It uses JUnit 5 and Spring Boot's testing capabilities.
 * It tests the basic CRUD operations for the Permission entity.
 *
 * @author aamir on 6/8/24
 */
@ActiveProfiles("h2")
@SpringBootTest
class PermissionTest {
  private Permission permission;
  private final PermissionRepository permissionRepository;

  /**
   * Constructor for dependency injection.
   *
   * @param permissionRepository The repository for Permission entities.
   */
  @Autowired
  public PermissionTest(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  /**
   * Set up method that runs before each test.
   * It initializes a new Permission entity.
   */
  @BeforeEach
  void setUp() {
    permission = new Permission();
    permission.setPermissionName("TEST_PERMISSION");
  }

  /**
   * Tear down method that runs after each test.
   * It deletes all Permission entities from the repository.
   */
  @AfterEach
  void tearDown() {
    permissionRepository.deleteAll();
  }

  @Test
  @DisplayName("Permission ID should be set after saving to repository")
  void permissionIdShouldBeSetAfterSavingToRepository() {
    permissionRepository.save(permission);
    assertNotNull(permission.getId());
  }

  @Test
  @DisplayName("Permission should be saved in the repository")
  void permissionShouldBeSavedInRepository() {
    permissionRepository.save(permission);
    assertTrue(permissionRepository.findById(permission.getId()).isPresent());
  }

  @Test
  @DisplayName("Permission should be saved and retrieved from repository")
  void permissionShouldBeSavedAndRetrievedFromRepository() {
    permissionRepository.save(permission);
    Optional<Permission> retrievedPermission = permissionRepository.findById(permission.getId());
    assertTrue(retrievedPermission.isPresent());
    assertEquals(permission.getId(), retrievedPermission.get().getId());
    assertEquals(permission.getPermissionName(), retrievedPermission.get().getPermissionName());
  }

  @Test
  @DisplayName("Permission should be updated")
  void permissionShouldBeUpdated() {
    Permission savedPermission = permissionRepository.save(permission);
    savedPermission.setPermissionName("UPDATED_PERMISSION");
    Permission updatedPermission = permissionRepository.save(savedPermission);
    assertNotNull(updatedPermission);
    assertEquals("UPDATED_PERMISSION", updatedPermission.getPermissionName());
  }

  @Test
  @DisplayName("Permission should be deleted from repository")
  void permissionShouldBeDeletedFromRepository() {
    permissionRepository.save(permission);
    permissionRepository.delete(permission);
    Optional<Permission> retrievedPermission = permissionRepository.findById(permission.getId());
    assertFalse(retrievedPermission.isPresent());
  }

  @Test
  @DisplayName("Duplicate permission should throw an exception")
  void duplicatePermissionShouldThrowException() {
    permissionRepository.save(permission);
    Permission duplicatePermission = new Permission();
    duplicatePermission.setPermissionName("TEST_PERMISSION");
    assertThrows(Exception.class, () -> permissionRepository.save(duplicatePermission));
  }
}