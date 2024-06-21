package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.domain.Permission;
import com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A DTO (Data Transfer Object) for Permission.
 * This is used to transfer data about a Permission between processes or across network links.
 * It includes various fields related to a Permission.
 *
 * @author aamir on 6/16/24
 */
public record PermissionDto(
        @NotNull(message = "Permission ID cannot be null")
        Long id,

        @NotBlank(message = "Permission name cannot be blank")
        @UniqueField(fieldName = "permissionName", domainClass = Permission.class)
        String permissionName
) {
}
