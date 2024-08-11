package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.exception.InvalidVerificationCodeException;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeAlreadyUsedException;
import com.clinicwave.clinicwaveusermanagementservice.exception.VerificationCodeExpiredException;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class provides unit tests for the VerificationCodeController class.
 *
 * @author aamir on 7/22/24
 */
@SpringBootTest
@AutoConfigureMockMvc
class VerificationCodeControllerTest {
  private final MockMvc mockMvc;

  private final ObjectMapper objectMapper;

  @MockBean
  private VerificationCodeService verificationCodeService;

  private VerificationRequestDto verificationRequestDto;

  private static final String URL_TEMPLATE = "/api/verification/verify";

  /**
   * Constructs a new VerificationCodeControllerTest with the given MockMvc and ObjectMapper.
   *
   * @param mockMvc      the MockMvc instance to use for testing
   * @param objectMapper the ObjectMapper instance to use for JSON serialization and deserialization
   */
  @Autowired
  public VerificationCodeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    verificationRequestDto = new VerificationRequestDto("test@example.com", "123456");
  }

  @Test
  @DisplayName("GET /api/verification/verify - User is verified")
  void testCheckVerificationStatusWhenUserIsVerified() throws Exception {
    when(verificationCodeService.checkVerificationStatus("testuser@example.com")).thenReturn(true);

    mockMvc.perform(get(URL_TEMPLATE)
                    .param("email", "testuser@example.com")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isVerified").value(true));
  }

  @Test
  @DisplayName("GET /api/verification/verify - User is not verified")
  void testCheckVerificationStatusWhenUserIsNotVerified() throws Exception {
    when(verificationCodeService.checkVerificationStatus("testuser@example.com")).thenReturn(false);

    mockMvc.perform(get(URL_TEMPLATE)
                    .param("email", "testuser@example.com")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isVerified").value(false));
  }

  @Test
  @DisplayName("GET /api/verification/verify - User not found")
  void testCheckVerificationStatusWhenUserIsNotFound() throws Exception {
    when(verificationCodeService.checkVerificationStatus("testuser@example.com"))
            .thenThrow(new ResourceNotFoundException("ClinicWaveUser", "email", "testuser@example.com"));

    mockMvc.perform(get(URL_TEMPLATE)
                    .param("email", "testuser@example.com")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("ClinicWaveUser with email: testuser@example.com not found"));
  }

  @Test
  @DisplayName("GET /api/verification/verify - Unexpected exception")
  void testCheckVerificationStatusWhenUnexpectedException() throws Exception {
    when(verificationCodeService.checkVerificationStatus("testuser@example.com"))
            .thenThrow(new RuntimeException("Unexpected error"));

    mockMvc.perform(get(URL_TEMPLATE)
                    .param("email", "testuser@example.com")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Unexpected error"));
  }

  @Test
  @DisplayName("POST /api/verification/verify")
  void shouldVerifyAccount() throws Exception {
    // Arrange
    doNothing().when(verificationCodeService).verifyAccount(verificationRequestDto);

    // Act & Assert
    mockMvc.perform(post(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(verificationRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Account verified successfully!"));
  }

  @Test
  @DisplayName("verify account with invalid verification code")
  void verifyAccount_InvalidVerificationCode() throws Exception {
    // Arrange
    doThrow(new InvalidVerificationCodeException("VerificationCode", "code", verificationRequestDto.code()))
            .when(verificationCodeService).verifyAccount(verificationRequestDto);

    // Act & Assert
    mockMvc.perform(post(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(verificationRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(String.format("VerificationCode with code %s is invalid", verificationRequestDto.code())));
  }

  @Test
  @DisplayName("verify account with verification code already used")
  void verifyAccount_VerificationCodeAlreadyUsed() throws Exception {
    // Arrange
    doThrow(new VerificationCodeAlreadyUsedException("VerificationCode", "code", verificationRequestDto.code()))
            .when(verificationCodeService).verifyAccount(verificationRequestDto);

    // Act & Assert
    mockMvc.perform(post(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(verificationRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(String.format("VerificationCode with code %s has already been used", verificationRequestDto.code())));
  }

  @Test
  @DisplayName("verify account with verification code expired")
  void verifyAccount_VerificationCodeExpired() throws Exception {
    // Arrange
    doThrow(new VerificationCodeExpiredException("VerificationCode", "code", verificationRequestDto.code()))
            .when(verificationCodeService).verifyAccount(verificationRequestDto);

    // Act & Assert
    mockMvc.perform(post(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(verificationRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(String.format("VerificationCode with code %s has expired", verificationRequestDto.code())));
  }

  @Test
  @DisplayName("verify account with user not found")
  void verifyAccount_UserNotFound() throws Exception {
    // Arrange
    doThrow(new ResourceNotFoundException("ClinicWaveUser", "email", verificationRequestDto.email()))
            .when(verificationCodeService).verifyAccount(verificationRequestDto);

    // Act & Assert
    mockMvc.perform(post(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(verificationRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value(String.format("ClinicWaveUser with email: %s not found", verificationRequestDto.email())));
  }
}