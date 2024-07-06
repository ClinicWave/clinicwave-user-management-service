package com.clinicwave.clinicwaveusermanagementservice.service;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserRoleAssignmentDto;

/**
 * This interface provides methods to assign and de-assign roles to ClinicWaveUser entities.
 *
 * @author aamir on 6/30/24
 */
public interface ClinicWaveUserRoleAssignment {
  ClinicWaveUserRoleAssignmentDto provisionUser(Long userId, Long roleId);

  ClinicWaveUserRoleAssignmentDto deProvisionUser(Long userId, Long roleId);
}
