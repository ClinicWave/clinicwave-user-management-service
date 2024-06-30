package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

/**
 * A DTO (Data Transfer Object) for Role.
 * This is used to transfer data about a Role between processes or across network links.
 * It includes various fields related to a Role.
 *
 * @author aamir on 6/16/24
 */
public record RoleDto(
        Long id,

        @NotBlank(message = "Role name cannot be blank")
        RoleNameEnum roleName,

        String roleDescription,

        @Valid
        Set<PermissionDto> permissionSet
) {
}
