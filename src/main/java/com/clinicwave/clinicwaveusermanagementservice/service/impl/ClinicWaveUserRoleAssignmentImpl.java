package com.clinicwave.clinicwaveusermanagementservice.service.impl;

import com.clinicwave.clinicwaveusermanagementservice.domain.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserRoleAssignmentDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserStatusEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.*;
import com.clinicwave.clinicwaveusermanagementservice.repository.ClinicWaveUserRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserRoleAssignment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class implements the ClinicWaveUserRoleAssignment interface and provides methods to assign and de-assign roles to ClinicWaveUser entities.
 * It uses the ClinicWaveUserRepository and RoleRepository to interact with the database. and data transfer objects.
 * The class is annotated with @Service to indicate that it is a service component in the Spring framework.
 *
 * @author aamir on 6/30/24
 */
@Service
public class ClinicWaveUserRoleAssignmentImpl implements ClinicWaveUserRoleAssignment {
  private final ClinicWaveUserRepository clinicWaveUserRepository;
  private final RoleRepository roleRepository;

  /**
   * Constructor for the ClinicWaveUserRoleAssignmentImpl class.
   *
   * @param clinicWaveUserRepository the ClinicWaveUserRepository to be used for database operations
   * @param roleRepository           the RoleRepository to be used for database operations
   */
  @Autowired
  public ClinicWaveUserRoleAssignmentImpl(ClinicWaveUserRepository clinicWaveUserRepository, RoleRepository roleRepository) {
    this.clinicWaveUserRepository = clinicWaveUserRepository;
    this.roleRepository = roleRepository;
  }

  /**
   * Assigns a role to a ClinicWaveUser entity.
   * The role to be assigned must not be the user's current role.
   * The user's role will be set to the assigned role.
   *
   * @param userId the ID of the ClinicWaveUser entity to be assigned the role
   * @param roleId the ID of the role to be assigned
   * @return the UserRoleAssignmentDto data transfer object
   * @throws DuplicateRoleAssignmentException if the role to be assigned is the user's current role
   */
  @Override
  public ClinicWaveUserRoleAssignmentDto provisionUser(Long userId, Long roleId) {
    // Find the user and role entities
    ClinicWaveUser clinicWaveUser = findClinicWaveUserById(userId);
    Role role = findRoleById(roleId);

    // Check if the user is inactive
    if (isUserInactive(clinicWaveUser)) {
      throw new InactiveUserException("User", "id", userId);
    }

    // Check if the role to be assigned is the user's current role
    if (isCurrentRole(roleId, clinicWaveUser.getRole().getId())) {
      throw new DuplicateRoleAssignmentException("User", "id", userId, role.getRoleName());
    }

    // Assign the role to the user
    clinicWaveUser.setRole(role);
    ClinicWaveUser savedClinicWaveUser = clinicWaveUserRepository.save(clinicWaveUser);

    return new ClinicWaveUserRoleAssignmentDto(
            savedClinicWaveUser.getId(),
            savedClinicWaveUser.getUsername(),
            role.getRoleName(),
            LocalDateTime.now()
    );
  }

  /**
   * De-assigns a role from a ClinicWaveUser entity.
   * The role to be de-assigned must not be the default role.
   * The role to be de-assigned must match the user's current role.
   * The user's role will be set to the default role.
   *
   * @param userId the ID of the ClinicWaveUser entity to be de-assigned the role
   * @param roleId the ID of the role to be de-assigned
   * @return the UserRoleAssignmentDto data transfer object
   * @throws RoleMismatchException       if the role to be de-assigned does not match the user's current role
   * @throws DefaultRoleRemovalException if the role to be de-assigned is the default role
   */
  @Override
  @Transactional
  public ClinicWaveUserRoleAssignmentDto deProvisionUser(Long userId, Long roleId) {
    // Find the user and role entities
    ClinicWaveUser clinicWaveUser = findClinicWaveUserById(userId);
    Role role = findRoleById(roleId);

    // Check if the user is inactive
    if (isUserInactive(clinicWaveUser)) {
      throw new InactiveUserException("User", "id", userId);
    }

    // Check if the role to be de-provisioned matches the user's current role
    if (!isCurrentRole(roleId, clinicWaveUser.getRole().getId())) {
      throw new RoleMismatchException("Role", "roleId", roleId);
    }

    // Check if the role to be de-provisioned is the default role
    if (isDefaultRole(role)) {
      throw new DefaultRoleRemovalException("User", "id", userId);
    }

    // Set the user's role to the default role
    Role defaultRole = findDefaultRole();
    clinicWaveUser.setRole(defaultRole);
    ClinicWaveUser savedClinicWaveUser = clinicWaveUserRepository.save(clinicWaveUser);

    return new ClinicWaveUserRoleAssignmentDto(
            savedClinicWaveUser.getId(),
            savedClinicWaveUser.getUsername(),
            defaultRole.getRoleName(),
            LocalDateTime.now()
    );
  }

  /**
   * Checks if the specified ClinicWaveUser entity is inactive.
   * @param user the ClinicWaveUser entity to be checked
   * @return true if the ClinicWaveUser entity is inactive, false otherwise
   */
  private boolean isUserInactive(ClinicWaveUser user) {
    return user.getStatus() != UserStatusEnum.ACTIVE;
  }

  /**
   * Checks if the specified role ID is the same as the user's current role ID.
   *
   * @param roleId     the role ID to be checked
   * @param userRoleId the user's current role ID
   * @return true if the role ID is the same as the user's current role ID, false otherwise
   */
  private boolean isCurrentRole(Long roleId, Long userRoleId) {
    return Objects.equals(roleId, userRoleId);
  }

  /**
   * Checks if the specified Role entity is the default role.
   *
   * @param role the Role entity to be checked
   * @return true if the Role entity is the default role, false otherwise
   */
  private boolean isDefaultRole(Role role) {
    return role.getRoleName().equals(RoleNameEnum.ROLE_DEFAULT);
  }

  /**
   * Finds the default Role entity.
   *
   * @return the Role entity with the role name ROLE_DEFAULT
   * @throws ResourceNotFoundException if the default Role entity is not found
   */
  private Role findDefaultRole() {
    return roleRepository.findByRoleName(RoleNameEnum.ROLE_DEFAULT)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", RoleNameEnum.ROLE_DEFAULT));
  }

  /**
   * Finds a ClinicWaveUser entity by its ID.
   *
   * @param userId the ID of the ClinicWaveUser entity to be found
   * @return the ClinicWaveUser entity
   * @throws ResourceNotFoundException if the ClinicWaveUser entity with the specified ID is not found
   */
  private ClinicWaveUser findClinicWaveUserById(Long userId) {
    return clinicWaveUserRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("ClinicWaveUser", "id", userId));
  }

  /**
   * Finds a Role entity by its ID.
   *
   * @param roleId the ID of the Role entity to be found
   * @return the Role entity
   * @throws ResourceNotFoundException if the Role entity with the specified ID is not found
   */
  private Role findRoleById(Long roleId) {
    return roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
  }
}
