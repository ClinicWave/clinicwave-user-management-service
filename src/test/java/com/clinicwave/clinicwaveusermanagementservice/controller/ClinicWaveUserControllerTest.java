package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.ClinicWaveUserDto;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.service.ClinicWaveUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class provides unit tests for the ClinicWaveUserController class.
 * It uses the Spring Boot testing framework to test the controller methods.
 * The tests are written using the JUnit 5 testing framework.
 * The tests use the Mockito library to mock the ClinicWaveUserService dependency.
 * The tests use the Spring MockMvc class to simulate HTTP requests to the controller.
 * The tests use the Jackson ObjectMapper class to serialize and deserialize JSON objects.
 *
 * @author aamir on 6/13/24
 */
@SpringBootTest
@AutoConfigureMockMvc
class ClinicWaveUserControllerTest {
  private final MockMvc mockMvc;

  private final ObjectMapper objectMapper;

  @MockBean
  private ClinicWaveUserService clinicWaveUserService;

  private ClinicWaveUserDto createdClinicWaveUserDto;

  /**
   * Constructs a new ClinicWaveUserControllerTest with the given MockMvc and ObjectMapper.
   *
   * @param mockMvc      the MockMvc instance to use for testing
   * @param objectMapper the ObjectMapper instance to use for JSON serialization and deserialization
   */
  @Autowired
  public ClinicWaveUserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  /**
   * Sets up the test environment by creating a new ClinicWaveUserDto instance.
   */
  @BeforeEach
  void setUp() {
    createdClinicWaveUserDto = new ClinicWaveUserDto(1L, "Test", "User",
            "1234567890", "testuser", "testuser@example.com",
            LocalDate.of(1990, 1, 1), GenderEnum.MALE, "Test bio");
  }

  @Test
  @DisplayName("GET /api/users/{userId}")
  void shouldGetUser() throws Exception {
    when(clinicWaveUserService.getUser(1L)).thenReturn(createdClinicWaveUserDto);

    mockMvc.perform(get("/api/users/{userId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(createdClinicWaveUserDto.id()))
            .andExpect(jsonPath("$.firstName").value(createdClinicWaveUserDto.firstName()))
            .andExpect(jsonPath("$.lastName").value(createdClinicWaveUserDto.lastName()))
            .andExpect(jsonPath("$.mobileNumber").value(createdClinicWaveUserDto.mobileNumber()))
            .andExpect(jsonPath("$.username").value(createdClinicWaveUserDto.username()))
            .andExpect(jsonPath("$.email").value(createdClinicWaveUserDto.email()))
            .andExpect(jsonPath("$.dateOfBirth").value(createdClinicWaveUserDto.dateOfBirth().toString()))
            .andExpect(jsonPath("$.gender").value(createdClinicWaveUserDto.gender().toString()))
            .andExpect(jsonPath("$.bio").value(createdClinicWaveUserDto.bio()));
  }

  @Test
  @DisplayName("POST /api/users")
  void shouldCreateUser() throws Exception {
    ClinicWaveUserDto clinicWaveUserDto = new ClinicWaveUserDto(null, "Test", "User",
            "1234567890", "testuser", "testuser@example.com",
            LocalDate.of(1990, 1, 1), GenderEnum.MALE, "Test bio");

    when(clinicWaveUserService.createUser(any(ClinicWaveUserDto.class))).thenReturn(createdClinicWaveUserDto);

    mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clinicWaveUserDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(createdClinicWaveUserDto.id()))
            .andExpect(jsonPath("$.firstName").value(createdClinicWaveUserDto.firstName()))
            .andExpect(jsonPath("$.lastName").value(createdClinicWaveUserDto.lastName()))
            .andExpect(jsonPath("$.mobileNumber").value(createdClinicWaveUserDto.mobileNumber()))
            .andExpect(jsonPath("$.username").value(createdClinicWaveUserDto.username()))
            .andExpect(jsonPath("$.email").value(createdClinicWaveUserDto.email()))
            .andExpect(jsonPath("$.dateOfBirth").value(createdClinicWaveUserDto.dateOfBirth().toString()))
            .andExpect(jsonPath("$.gender").value(createdClinicWaveUserDto.gender().toString()))
            .andExpect(jsonPath("$.bio").value(createdClinicWaveUserDto.bio()));
  }

  @Test
  @DisplayName("PUT /api/users/{userId}")
  void shouldUpdateUser() throws Exception {
    when(clinicWaveUserService.updateUser(eq(1L), any(ClinicWaveUserDto.class))).thenReturn(createdClinicWaveUserDto);

    mockMvc.perform(put("/api/users/{userId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createdClinicWaveUserDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(createdClinicWaveUserDto.id()))
            .andExpect(jsonPath("$.firstName").value(createdClinicWaveUserDto.firstName()))
            .andExpect(jsonPath("$.lastName").value(createdClinicWaveUserDto.lastName()))
            .andExpect(jsonPath("$.mobileNumber").value(createdClinicWaveUserDto.mobileNumber()))
            .andExpect(jsonPath("$.username").value(createdClinicWaveUserDto.username()))
            .andExpect(jsonPath("$.email").value(createdClinicWaveUserDto.email()))
            .andExpect(jsonPath("$.dateOfBirth").value(createdClinicWaveUserDto.dateOfBirth().toString()))
            .andExpect(jsonPath("$.gender").value(createdClinicWaveUserDto.gender().toString()))
            .andExpect(jsonPath("$.bio").value(createdClinicWaveUserDto.bio()));
  }

  @Test
  @DisplayName("DELETE /api/users/{userId}")
  void shouldDeleteUser() throws Exception {
    doNothing().when(clinicWaveUserService).deleteUser(1L);

    mockMvc.perform(delete("/api/users/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("GET /api/users")
  void shouldGetAllUsers() throws Exception {
    when(clinicWaveUserService.getAllUsers()).thenReturn(Collections.singletonList(createdClinicWaveUserDto));

    mockMvc.perform(get("/api/users")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(createdClinicWaveUserDto.id()))
            .andExpect(jsonPath("$[0].firstName").value(createdClinicWaveUserDto.firstName()))
            .andExpect(jsonPath("$[0].lastName").value(createdClinicWaveUserDto.lastName()))
            .andExpect(jsonPath("$[0].mobileNumber").value(createdClinicWaveUserDto.mobileNumber()))
            .andExpect(jsonPath("$[0].username").value(createdClinicWaveUserDto.username()))
            .andExpect(jsonPath("$[0].email").value(createdClinicWaveUserDto.email()))
            .andExpect(jsonPath("$[0].dateOfBirth").value(createdClinicWaveUserDto.dateOfBirth().toString()))
            .andExpect(jsonPath("$[0].gender").value(createdClinicWaveUserDto.gender().toString()))
            .andExpect(jsonPath("$[0].bio").value(createdClinicWaveUserDto.bio()));
  }
}