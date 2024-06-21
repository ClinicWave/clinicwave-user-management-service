package com.clinicwave.clinicwaveusermanagementservice.dto;

import jakarta.validation.constraints.NotNull;

/**
 * A DTO (Data Transfer Object) for RolePermission.
 * This is used to transfer data about a RolePermission between processes or across network links.
 * It includes various fields related to a RolePermission.
 *
 * @author aamir on 6/16/24
 */
public record RolePermissionDto(
        @NotNull(message = "Role permission ID cannot be null")
        Long id,

        @NotNull(message = "Role cannot be null")
        RoleDto role,

        PermissionDto permission
) {
}
