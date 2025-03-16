package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.RolePermission;
import com.clinicwave.clinicwaveusermanagementservice.dto.PermissionDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for mapping between the Role domain object and the RoleDto data transfer object.
 * It uses the permissionMapper to handle the mapping of nested Permission objects.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/16/24
 */
@Component
@AllArgsConstructor
public class RoleMapper {
  private final PermissionMapper permissionMapper;

  /**
   * Converts a Role domain object into a RoleDto data transfer object.
   * It uses the PermissionMapper to convert the nested Permission objects.
   *
   * @param role the Role object to be converted
   * @return the converted RoleDto object
   */
  public RoleDto toDto(Role role) {
    Set<PermissionDto> permissionDtoSet = Optional.ofNullable(role.getRolePermissionSet())
            .orElse(Set.of())
            .stream()
            .map(rolePermission -> permissionMapper.toDto(rolePermission.getPermission()))
            .collect(Collectors.toSet());

    return new RoleDto(
            role.getId(),
            role.getRoleName(),
            role.getRoleDescription(),
            permissionDtoSet
    );
  }

  /**
   * Converts a RoleDto data transfer object into a Role domain object.
   * It uses the PermissionMapper to convert the nested Permission objects.
   *
   * @param roleDto the RoleDto object to be converted
   * @return the converted Role object
   */
  public Role toEntity(RoleDto roleDto) {
    Role role = new Role();
    role.setId(roleDto.id());
    role.setRoleName(roleDto.roleName());
    role.setRoleDescription(roleDto.roleDescription());

    Optional.ofNullable(roleDto.permissionSet()).ifPresent(permissionDtoSet -> {
      Set<RolePermission> rolePermissions = permissionDtoSet.stream()
              .map(permissionDto -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permissionMapper.toEntity(permissionDto));
                return rolePermission;
              })
              .collect(Collectors.toSet());
      role.setRolePermissionSet(rolePermissions);
    });

    return role;
  }
}