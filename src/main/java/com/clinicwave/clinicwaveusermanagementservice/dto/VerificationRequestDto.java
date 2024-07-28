package com.clinicwave.clinicwaveusermanagementservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * This class defines the data transfer object for a verification request.
 * It contains the email address and verification code to be used for verification.
 *
 * @author aamir on 7/21/24
 */
public record VerificationRequestDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Verification code cannot be blank")
        @Pattern(regexp = "^\\d{6}$", message = "Verification code must be exactly 6 digits")
        String code
) {
}
