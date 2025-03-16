package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserRoleAssignmentDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import com.clinicwave.clinicwaveusermanagementservice.exception.DefaultRoleRemovalException;
import com.clinicwave.clinicwaveusermanagementservice.exception.DuplicateRoleAssignmentException;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.exception.RoleMismatchException;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserRoleAssignment;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class provides unit tests for the ClinicWaveUserRoleAssignmentController class.
 * It uses the Spring Boot testing framework to test the controller methods.
 * The tests are written using the JUnit 5 testing framework.
 *
 * @author aamir on 7/3/24
 */
@SpringBootTest
@AutoConfigureMockMvc
@AllArgsConstructor
class ClinicWaveUserRoleAssignmentControllerTest {
  private final MockMvc mockMvc;

  @MockBean
  private ClinicWaveUserRoleAssignment clinicWaveUserRoleAssignment;

  @Test
  @DisplayName("POST /api/users/{userId}/roles/{roleId}")
  void provisionUser_ShouldReturnOkResponse() throws Exception {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUserRoleAssignmentDto expectedDto = new ClinicWaveUserRoleAssignmentDto(userId, "testUser", RoleNameEnum.ROLE_ADMIN, LocalDateTime.now());
    when(clinicWaveUserRoleAssignment.provisionUser(userId, roleId)).thenReturn(expectedDto);

    // Act & Assert
    mockMvc.perform(post("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.username").value("testUser"))
            .andExpect(jsonPath("$.roleName").value(RoleNameEnum.ROLE_ADMIN.toString()))
            .andExpect(jsonPath("$.assignmentTimestamp").isNotEmpty());

    verify(clinicWaveUserRoleAssignment, times(1)).provisionUser(userId, roleId);
  }

  @Test
  @DisplayName("provisionUser should handle DuplicateRoleAssignmentException")
  void provisionUser_ShouldHandleDuplicateRoleAssignmentException() throws Exception {
    Long userId = 1L;
    Long roleId = 2L;
    when(clinicWaveUserRoleAssignment.provisionUser(userId, roleId))
            .thenThrow(new DuplicateRoleAssignmentException("User", "id", userId, RoleNameEnum.ROLE_ADMIN));

    mockMvc.perform(post("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());

    verify(clinicWaveUserRoleAssignment, times(1)).provisionUser(userId, roleId);
  }

  @Test
  @DisplayName("provisionUser should handle ResourceNotFoundException")
  void provisionUser_ShouldHandleResourceNotFoundException() throws Exception {
    Long userId = 1L;
    Long roleId = 2L;
    when(clinicWaveUserRoleAssignment.provisionUser(userId, roleId))
            .thenThrow(new ResourceNotFoundException("User", "id", userId));

    mockMvc.perform(post("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    verify(clinicWaveUserRoleAssignment, times(1)).provisionUser(userId, roleId);
  }

  @Test
  @DisplayName("DELETE /api/users/{userId}/roles/{roleId}")
  void deProvisionUser_ShouldReturnOkResponse() throws Exception {
    // Arrange
    Long userId = 1L;
    Long roleId = 2L;
    ClinicWaveUserRoleAssignmentDto expectedDto = new ClinicWaveUserRoleAssignmentDto(userId, "testUser", RoleNameEnum.ROLE_DEFAULT, LocalDateTime.now());
    when(clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId)).thenReturn(expectedDto);

    // Act & Assert
    mockMvc.perform(delete("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userId").value(userId))
            .andExpect(jsonPath("$.username").value("testUser"))
            .andExpect(jsonPath("$.roleName").value(RoleNameEnum.ROLE_DEFAULT.toString()))
            .andExpect(jsonPath("$.assignmentTimestamp").isNotEmpty());

    verify(clinicWaveUserRoleAssignment, times(1)).deProvisionUser(userId, roleId);
  }

  @Test
  @DisplayName("deProvisionUser should handle RoleMismatchException")
  void deProvisionUser_ShouldHandleRoleMismatchException() throws Exception {
    Long userId = 1L;
    Long roleId = 2L;
    when(clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId))
            .thenThrow(new RoleMismatchException("Role", "roleId", roleId));

    mockMvc.perform(delete("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    verify(clinicWaveUserRoleAssignment, times(1)).deProvisionUser(userId, roleId);
  }

  @Test
  @DisplayName("deProvisionUser should handle DefaultRoleRemovalException")
  void deProvisionUser_ShouldHandleDefaultRoleRemovalException() throws Exception {
    Long userId = 1L;
    Long roleId = 2L;
    when(clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId))
            .thenThrow(new DefaultRoleRemovalException("User", "id", userId));

    mockMvc.perform(delete("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    verify(clinicWaveUserRoleAssignment, times(1)).deProvisionUser(userId, roleId);
  }

  @Test
  @DisplayName("deProvisionUser should handle ResourceNotFoundException")
  void deProvisionUser_ShouldHandleResourceNotFoundException() throws Exception {
    Long userId = 1L;
    Long roleId = 2L;
    when(clinicWaveUserRoleAssignment.deProvisionUser(userId, roleId))
            .thenThrow(new ResourceNotFoundException("User", "id", userId));

    mockMvc.perform(delete("/api/users/{userId}/roles/{roleId}", userId, roleId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    verify(clinicWaveUserRoleAssignment, times(1)).deProvisionUser(userId, roleId);
  }
}