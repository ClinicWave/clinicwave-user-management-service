package com.clinicwave.clinicwaveusermanagementservice.controller;

import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationRequestDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.VerificationStatusDto;
import com.clinicwave.clinicwaveusermanagementservice.service.VerificationCodeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
   * Checks the verification status for the specified token.
   *
   * @param token the token to check the verification status for
   * @return the response entity containing the VerificationStatusDto object
   * Throws exception which is handled by the GlobalExceptionHandler:
   * - ResourceNotFoundException if the user is not found
   */
  @GetMapping("/verify")
  public ResponseEntity<VerificationStatusDto> checkVerificationStatus(@RequestParam String token) {
    VerificationStatusDto verificationStatusDto = verificationCodeService.checkVerificationStatus(token);
    return ResponseEntity.ok(verificationStatusDto);
  }

  /**
   * Verifies the account for the specified email address.
   *
   * @param verificationRequestDto the verification request data transfer object
   * @return the response entity containing the result of the verification
   * Throws Various exceptions which are handled by the GlobalExceptionHandler:
   * - ResourceNotFoundException if the user is not found
   * - VerificationCodeExpiredException if the code has expired
   * - VerificationCodeAlreadyUsedException if the code was already used
   * - InvalidVerificationCodeException if the code is incorrect
   */
  @PostMapping("/verify")
  public ResponseEntity<Map<String, String>> verifyAccount(@Valid @RequestBody VerificationRequestDto verificationRequestDto) {
    log.info("Verifying account for email: {}", verificationRequestDto.email());
    verificationCodeService.verifyAccount(verificationRequestDto);
    log.info("Account verified successfully!");
    return ResponseEntity.ok(Map.of("message", "Account verified successfully!"));
  }
}
