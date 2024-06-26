package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.enums.PermissionNameEnum;
import jakarta.validation.constraints.NotBlank;

/**
 * A DTO (Data Transfer Object) for Permission.
 * This is used to transfer data about a Permission between processes or across network links.
 * It includes various fields related to a Permission.
 *
 * @author aamir on 6/16/24
 */
public record PermissionDto(
        Long id,

        @NotBlank(message = "Permission name cannot be blank")
        PermissionNameEnum permissionName
) {
}
