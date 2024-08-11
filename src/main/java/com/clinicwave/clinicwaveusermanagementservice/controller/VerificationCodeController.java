package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.exception.ResourceNotFoundException;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  private static final String ERROR = "error";

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
   * Checks the verification status for the specified email address.
   *
   * @param email the email address to check the verification status for
   * @return the response entity containing the verification status
   */
  @GetMapping("/verify")
  public ResponseEntity<Map<String, Object>> checkVerificationStatus(@RequestParam String email) {
    try {
      Boolean isVerified = verificationCodeService.checkVerificationStatus(email);
      return ResponseEntity.ok(Map.of("isVerified", isVerified));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(ERROR, e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(ERROR, e.getMessage()));
    }
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
      return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }
  }
}
