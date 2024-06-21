package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.RolePermission;
import com.clinicwave.clinicwaveusermanagementservice.dto.RoleDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.RolePermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for mapping between the Role domain object and the RoleDto data transfer object.
 * It uses the RolePermissionMapper to handle the mapping of nested RolePermission objects.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/16/24
 */
@Component
public class RoleMapper {
  private final RolePermissionMapper rolePermissionMapper;

  /**
   * Constructor for the RoleMapper class.
   * It initializes the rolePermissionMapper with the provided mapper.
   *
   * @param rolePermissionMapper the RolePermissionMapper to be used for mapping RolePermission objects
   */
  @Autowired
  public RoleMapper(@Lazy RolePermissionMapper rolePermissionMapper) {
    this.rolePermissionMapper = rolePermissionMapper;
  }

  /**
   * Converts a Role domain object into a RoleDto data transfer object.
   * It uses the RolePermissionMapper to convert the nested RolePermission objects.
   *
   * @param role the Role object to be converted
   * @return the converted RoleDto object
   */
  public RoleDto toDto(Role role) {
    Set<RolePermissionDto> rolePermissionDtoSet = role.getRolePermissionSet() != null ?
            role.getRolePermissionSet().stream()
                    .map(rolePermissionMapper::toDto)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()) : null;

    return new RoleDto(
            role.getId(),
            role.getRoleName(),
            role.getRoleDescription(),
            rolePermissionDtoSet
    );
  }

  /**
   * Converts a RoleDto data transfer object into a Role domain object.
   * It uses the RolePermissionMapper to convert the nested RolePermissionDto objects.
   *
   * @param roleDto the RoleDto object to be converted
   * @return the converted Role object
   */
  public Role toEntity(RoleDto roleDto) {
    Set<RolePermission> rolePermissionSet = roleDto.rolePermissionSet() != null ?
            roleDto.rolePermissionSet().stream()
                    .map(rolePermissionMapper::toEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()) : null;

    return new Role(
            roleDto.id(),
            roleDto.roleName(),
            roleDto.roleDescription(),
            rolePermissionSet
    );
  }
}