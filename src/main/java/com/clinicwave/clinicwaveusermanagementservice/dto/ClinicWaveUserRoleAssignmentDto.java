package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;

import java.time.LocalDateTime;

/**
 * A DTO (Data Transfer Object) for UserRoleAssignment.
 * This is used to transfer data about a UserRoleAssignment between processes or across network links.
 *
 * @author aamir on 7/6/24
 */
public record ClinicWaveUserRoleAssignmentDto(
        Long userId,
        String username,
        RoleNameEnum roleName,
        LocalDateTime assignmentTimestamp
) {
}
