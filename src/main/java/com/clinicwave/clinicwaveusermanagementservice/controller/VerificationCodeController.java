package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * This class defines the controller for handling verification code related requests.
 *
 * @author aamir on 7/22/24
 */
@RestController
@RequestMapping("/api/verification")
@Slf4j
public class VerificationCodeController {
  private final VerificationCodeService verificationCodeService;

  /**
   * Constructs a new VerificationCodeController with the given VerificationCodeService.
   *
   * @param verificationCodeService the VerificationCodeService to be used for verification code operations
   */
  @Autowired
  public VerificationCodeController(VerificationCodeService verificationCodeService) {
    this.verificationCodeService = verificationCodeService;
  }

  /**
   * Verifies the account for the specified email address.
   *
   * @param verificationRequestDto the verification request data transfer object
   * @return the response entity containing the result of the verification
   */
  @PostMapping("/verify")
  public ResponseEntity<Map<String, String>> verifyAccount(@Valid @RequestBody VerificationRequestDto verificationRequestDto) {
    log.info("Verifying account for email: {}", verificationRequestDto.email());
    try {
      verificationCodeService.verifyAccount(verificationRequestDto);
      log.info("Account verified successfully!");
      return ResponseEntity.ok(Map.of("message", "Account verified successfully!"));
    } catch (Exception e) {
      log.error("Error verifying account: {}", e.getMessage());
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }
}
