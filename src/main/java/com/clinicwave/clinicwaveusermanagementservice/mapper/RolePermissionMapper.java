package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.Permission;
import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.domain.RolePermission;
import com.clinicwave.clinicwaveusermanagementservice.dto.PermissionDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.RoleDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.RolePermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between the RolePermission domain object and the RolePermissionDto data transfer object.
 * It uses the RoleMapper and PermissionMapper to handle the mapping of nested Role and Permission objects.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/16/24
 */
@Component
public class RolePermissionMapper {
  private final RoleMapper roleMapper;
  private final PermissionMapper permissionMapper;

  /**
   * Constructor for the RolePermissionMapper class.
   * It initializes the roleMapper and permissionMapper with the provided mappers.
   *
   * @param roleMapper       the RoleMapper to be used for mapping Role objects
   * @param permissionMapper the PermissionMapper to be used for mapping Permission objects
   */
  @Autowired
  public RolePermissionMapper(@Lazy RoleMapper roleMapper, PermissionMapper permissionMapper) {
    this.roleMapper = roleMapper;
    this.permissionMapper = permissionMapper;
  }

  /**
   * Converts a RolePermission domain object into a RolePermissionDto data transfer object.
   * It uses the RoleMapper and PermissionMapper to convert the nested Role and Permission objects.
   *
   * @param rolePermission the RolePermission object to be converted
   * @return the converted RolePermissionDto object
   */
  public RolePermissionDto toDto(RolePermission rolePermission) {
    RoleDto roleDto = rolePermission.getRole() != null ?
            roleMapper.toDto(rolePermission.getRole()) : null;
    PermissionDto permissionDto = rolePermission.getPermission() != null ?
            permissionMapper.toDto(rolePermission.getPermission()) : null;

    return new RolePermissionDto(
            rolePermission.getId(),
            roleDto,
            permissionDto
    );
  }

  /**
   * Converts a RolePermissionDto data transfer object into a RolePermission domain object.
   * It uses the RoleMapper and PermissionMapper to convert the nested RoleDto and PermissionDto objects.
   *
   * @param rolePermissionDto the RolePermissionDto object to be converted
   * @return the converted RolePermission object
   */
  public RolePermission toEntity(RolePermissionDto rolePermissionDto) {
    Role role = rolePermissionDto.role() != null ?
            roleMapper.toEntity(rolePermissionDto.role()) : null;
    Permission permission = rolePermissionDto.permission() != null ?
            permissionMapper.toEntity(rolePermissionDto.permission()) : null;

    return new RolePermission(
            rolePermissionDto.id(),
            role,
            permission
    );
  }
}