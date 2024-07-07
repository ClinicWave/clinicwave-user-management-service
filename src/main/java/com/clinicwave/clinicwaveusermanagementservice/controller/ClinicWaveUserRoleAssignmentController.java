package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserRoleAssignmentDto;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserRoleAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a REST controller that handles requests related to user role assignments.
 * It defines several endpoints for provisioning and de-provisioning roles for users.
 *
 * @author aamir on 6/30/24
 */
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class ClinicWaveUserRoleAssignmentController {
  private final ClinicWaveUserRoleAssignment clinicWaveUserRoleAssignment;

  /**
   * Constructor for the ClinicWaveUserRoleAssignmentController class.
   *
   * @param clinicWaveUserRoleAssignment the ClinicWaveUserRoleAssignment to be used for handling business logic
   */
  @Autowired
  public ClinicWaveUserRoleAssignmentController(ClinicWaveUserRoleAssignment clinicWaveUserRoleAssignment) {
    this.clinicWaveUserRoleAssignment = clinicWaveUserRoleAssignment;
  }

  /**
   * Provisions a role for a user.
   *
   * @param userId the ID of the user to be provisioned
   * @param roleId the ID of the role to be provisioned
   * @return the response entity containing the ClinicWaveUserRoleAssignmentDto data transfer object
   */
  @PostMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<ClinicWaveUserRoleAssignmentDto> provisionUser(@PathVariable Long userId, @PathVariable Long roleId) {
    return ResponseEntity.ok(clinicWaveUserRoleAssignment.provisionUser(userId, roleId));
  }

  /**
   * De-provisions a role for a user.
   *
   * @param userId the ID of the user to be de-provisioned
   * @param roleId the ID of the role to be de-provisioned
   * @return the response entity containing the ClinicWaveUserRoleAssignmentDto data transfer object
   */
  @DeleteMapping("/{userId}/roles/{roleId}")
  public ResponseEntity<ClinicWaveUserRoleAssignmentDto> deProvisionUser(@PathVariable Long userId, @PathVariable Long roleId) {
    return ResponseEntity.ok(clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId));
  }
}
