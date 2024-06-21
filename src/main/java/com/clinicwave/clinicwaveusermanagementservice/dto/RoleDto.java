package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.domain.Role;
import com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * A DTO (Data Transfer Object) for Role.
 * This is used to transfer data about a Role between processes or across network links.
 * It includes various fields related to a Role.
 *
 * @author aamir on 6/16/24
 */
public record RoleDto(
        @NotNull(message = "Role ID cannot be null")
        Long id,

        @NotBlank(message = "Role name cannot be blank")
        @UniqueField(fieldName = "roleName", domainClass = Role.class)
        String roleName,

        String roleDescription,

        Set<RolePermissionDto> rolePermissionSet
) {
}
