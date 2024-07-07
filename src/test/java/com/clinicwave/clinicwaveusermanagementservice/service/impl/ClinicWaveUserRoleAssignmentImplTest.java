package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserRoleAssignmentDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.DefaultRoleRemovalException;
import com.clinicwave.clinicwaveusermanagementservice.exception.DuplicateRoleAssignmentException;
import com.clinicwave.clinicwaveusermanagementservice.exception.InactiveUserException;
import com.clinicwave.clinicwaveusermanagementservice.exception.RoleMismatchException;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the ClinicWaveUserRoleAssignmentImpl class.
 * It uses the Mockito framework to mock the ClinicWaveUserRepository and RoleRepository.
 * The class is annotated with @ExtendWith(MockitoExtension.class) to enable the use of Mockito annotations.
 *
 * @author aamir on 6/30/24
 */
@ActiveProfiles("h2")
@ExtendWith(MockitoExtension.class)
class ClinicWaveUserRoleAssignmentImplTest {
  @Mock
  private ClinicWaveUserRepository clinicWaveUserRepository;

  @Mock
  private RoleRepository roleRepository;

  @InjectMocks
  private ClinicWaveUserRoleAssignmentImpl clinicWaveUserRoleAssignment;

  @Test
  void provisionUser_Success() {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_DEFAULT);
    Role newRole = createMockRole(roleId, RoleNameEnum.ROLE_ADMIN);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(newRole));
    when(clinicWaveUserRepository.save(any(ClinicWaveUser.class))).thenReturn(user);

    // Act
    ClinicWaveUserRoleAssignmentDto result = clinicWaveUserRoleAssignment.provisionUser(userId, roleId);

    // Assert
    assertNotNull(result);
    assertEquals(userId, result.userId());
    assertEquals(user.getUsername(), result.username());
    assertEquals(RoleNameEnum.ROLE_ADMIN, result.roleName());
    assertNotNull(result.assignmentTimestamp());

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(clinicWaveUserRepository, times(1)).save(user);
  }

  @Test
  void provisionUser_DuplicateRoleAssignment() {
    // Arrange
    Long userId = 1L;
    Long roleId = 1L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_ADMIN);
    Role role = createMockRole(roleId, RoleNameEnum.ROLE_ADMIN);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

    // Act & Assert
    assertThrows(DuplicateRoleAssignmentException.class,
            () -> clinicWaveUserRoleAssignment.provisionUser(userId, roleId));

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
  }

  @Test
  void provisionUser_InactiveUser() {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_DEFAULT);
    user.setStatus(UserStatusEnum.INACTIVE); // Set the user status to INACTIVE
    Role newRole = createMockRole(roleId, RoleNameEnum.ROLE_ADMIN);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(newRole));

    // Act & Assert
    assertThrows(InactiveUserException.class,
            () -> clinicWaveUserRoleAssignment.provisionUser(userId, roleId));

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
  }

  @Test
  void deProvisionUser_Success() {
    // Arrange
    Long userId = 1L;
    Long roleId = 1L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_ADMIN);
    Role currentRole = createMockRole(roleId, RoleNameEnum.ROLE_ADMIN);
    Role defaultRole = createMockRole(3L, RoleNameEnum.ROLE_DEFAULT);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(currentRole));
    when(roleRepository.findByRoleName(RoleNameEnum.ROLE_DEFAULT)).thenReturn(Optional.of(defaultRole));
    when(clinicWaveUserRepository.save(any(ClinicWaveUser.class))).thenReturn(user);

    // Act
    ClinicWaveUserRoleAssignmentDto result = clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId);

    // Assert
    assertNotNull(result);
    assertEquals(userId, result.userId());
    assertEquals(user.getUsername(), result.username());
    assertEquals(RoleNameEnum.ROLE_DEFAULT, result.roleName());
    assertNotNull(result.assignmentTimestamp());

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(roleRepository, times(1)).findByRoleName(RoleNameEnum.ROLE_DEFAULT);
    verify(clinicWaveUserRepository, times(1)).save(user);
  }

  @Test
  void deProvisionUser_RoleMismatch() {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_ADMIN);
    Role role = createMockRole(roleId, RoleNameEnum.ROLE_USER);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

    // Act & Assert
    assertThrows(RoleMismatchException.class,
            () -> clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId));

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
  }

  @Test
  void deProvisionUser_DefaultRoleRemoval() {
    // Arrange
    Long userId = 1L;
    Long roleId = 1L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_DEFAULT);
    Role defaultRole = createMockRole(roleId, RoleNameEnum.ROLE_DEFAULT);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(defaultRole));

    // Act & Assert
    assertThrows(DefaultRoleRemovalException.class,
            () -> clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId));

    verify(clinicWaveUserRepository).findById(userId);
    verify(roleRepository).findById(roleId);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
  }

  @Test
  void deProvisionUser_InactiveUser() {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUser user = createMockUser(userId, RoleNameEnum.ROLE_DEFAULT);
    user.setStatus(UserStatusEnum.INACTIVE); // Set the user status to INACTIVE
    Role newRole = createMockRole(roleId, RoleNameEnum.ROLE_ADMIN);

    when(clinicWaveUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(newRole));

    // Act & Assert
    assertThrows(InactiveUserException.class,
            () -> clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId));

    verify(clinicWaveUserRepository, times(1)).findById(userId);
    verify(roleRepository, times(1)).findById(roleId);
    verify(clinicWaveUserRepository, never()).save(any(ClinicWaveUser.class));
  }

  private ClinicWaveUser createMockUser(Long id, RoleNameEnum roleName) {
    ClinicWaveUser user = new ClinicWaveUser();
    user.setId(id);
    user.setUsername("testuser");
    user.setFirstName("Test");
    user.setLastName("User");
    user.setEmail("test@example.com");
    user.setMobileNumber("1234567890");
    user.setDateOfBirth(LocalDate.of(1990, 1, 1));
    user.setGender(GenderEnum.MALE);
    user.setStatus(UserStatusEnum.ACTIVE);
    user.setRole(createMockRole(id, roleName));
    return user;
  }

  private Role createMockRole(Long id, RoleNameEnum roleName) {
    Role role = new Role();
    role.setId(id);
    role.setRoleName(roleName);
    return role;
  }
}