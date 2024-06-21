package com.clinicwave.clinicwaveusermanagementservice.mapper;

import com.clinicwave.clinicwaveusermanagementservice.domain.Permission;
import com.clinicwave.clinicwaveusermanagementservice.dto.PermissionDto;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between the Permission domain object and the PermissionDto data transfer object.
 * The class is annotated with @Component to allow Spring to handle its lifecycle.
 *
 * @author aamir on 6/16/24
 */
@Component
public class PermissionMapper {
  /**
   * Converts a Permission domain object into a PermissionDto data transfer object.
   *
   * @param permission the Permission object to be converted
   * @return the converted PermissionDto object
   */
  public PermissionDto toDto(Permission permission) {
    return new PermissionDto(
            permission.getId(),
            permission.getPermissionName()
    );
  }

  /**
   * Converts a PermissionDto data transfer object into a Permission domain object.
   *
   * @param permissionDto the PermissionDto object to be converted
   * @return the converted Permission object
   */
  public Permission toEntity(PermissionDto permissionDto) {
    return new Permission(
            permissionDto.id(),
            permissionDto.permissionName()
    );
  }
}