package com.clinicwave.clinicwaveusermanagementservice.initializer;

import com.clinicwave.clinicwaveusermanagementservice.domain.Permission;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.RolePermission;
import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import com.clinicwave.clinicwaveusermanagementservice.enums.PermissionNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.UserTypeEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.repository.PermissionRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.RoleRepository;
import com.clinicwave.clinicwaveusermanagementservice.repository.UserTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class initializes the database with default values for roles, user types, and permissions.
 * It implements the CommandLineRunner interface to run the initialization code when the application starts.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/30/24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RolePermissionUserTypeInitializer implements CommandLineRunner {
  private static final String PERMISSION = "Permission";
  private static final String PERMISSION_NAME = "permissionName";
  private static final String ROLE = "Role";
  private static final String ROLE_NAME = "roleName";

  private final RoleRepository roleRepository;
  private final UserTypeRepository userTypeRepository;
  private final PermissionRepository permissionRepository;

  /**
   * This method initializes the database with default values.
   *
   * @param args the command line arguments
   */
  @Override
  @Transactional
  public void run(String... args) {
    initializeRoles();
    initializeUserTypes();
    initializePermissions();
    initializeRolePermissions();
  }

  /**
   * This method initializes the roles in the database.
   */
  private void initializeRoles() {
    Set<RoleNameEnum> existingRoles = roleRepository.findAll().stream()
            .map(Role::getRoleName)
            .collect(Collectors.toSet());

    List<Role> newRoles = Arrays.stream(RoleNameEnum.values())
            .filter(roleName -> !existingRoles.contains(roleName))
            .map(roleName -> {
              Role role = new Role();
              role.setRoleName(roleName);
              return role;
            })
            .toList();

    if (!newRoles.isEmpty()) {
      roleRepository.saveAll(newRoles);
      log.info("Initialized {} new roles", newRoles.size());
    }
  }

  /**
   * This method initializes the user types in the database.
   */
  private void initializeUserTypes() {
    Set<UserTypeEnum> existingTypes = userTypeRepository.findAll().stream()
            .map(UserType::getType)
            .collect(Collectors.toSet());

    List<UserType> newTypes = Arrays.stream(UserTypeEnum.values())
            .filter(type -> !existingTypes.contains(type))
            .map(type -> {
              UserType userType = new UserType();
              userType.setType(type);
              return userType;
            })
            .toList();

    if (!newTypes.isEmpty()) {
      userTypeRepository.saveAll(newTypes);
      log.info("Initialized {} new user types", newTypes.size());
    }
  }

  /**
   * This method initializes the permissions in the database.
   */
  private void initializePermissions() {
    Set<PermissionNameEnum> existingPermissions = permissionRepository.findAll().stream()
            .map(Permission::getPermissionName)
            .collect(Collectors.toSet());

    List<Permission> newPermissions = Arrays.stream(PermissionNameEnum.values())
            .filter(permissionName -> !existingPermissions.contains(permissionName))
            .map(permissionName -> {
              Permission permission = new Permission();
              permission.setPermissionName(permissionName);
              return permission;
            })
            .toList();

    if (!newPermissions.isEmpty()) {
      permissionRepository.saveAll(newPermissions);
      log.info("Initialized {} new permissions", newPermissions.size());
    }
  }

  /**
   * This method initializes the role permissions in the database.
   */
  private void initializeRolePermissions() {
    initializeRolePermission(RoleNameEnum.ROLE_DEFAULT, Collections.singleton(PermissionNameEnum.PERMISSION_READ));
    initializeRolePermission(RoleNameEnum.ROLE_ADMIN,
            Set.of(PermissionNameEnum.PERMISSION_READ, PermissionNameEnum.PERMISSION_WRITE));
  }

  /**
   * This method initializes the permissions for a given role.
   *
   * @param roleName              the name of the role
   * @param permissionNameEnumSet the names of the permissions
   */
  private void initializeRolePermission(RoleNameEnum roleName, Set<PermissionNameEnum> permissionNameEnumSet) {
    Role role = roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new ResourceNotFoundException(ROLE, ROLE_NAME, roleName));

    Set<RolePermission> rolePermissionSet = new HashSet<>();

    for (PermissionNameEnum permissionName : permissionNameEnumSet) {
      Permission permission = permissionRepository.findByPermissionName(permissionName)
              .orElseThrow(() -> new ResourceNotFoundException(PERMISSION, PERMISSION_NAME, permissionName));

      RolePermission rolePermission = new RolePermission();
      rolePermission.setPermission(permission);
      rolePermission.setRole(role);
      rolePermissionSet.add(rolePermission);
    }

    role.setRolePermissionSet(rolePermissionSet);
    roleRepository.save(role);
    log.info("Initialized permissions for role {}", roleName);
  }
}
