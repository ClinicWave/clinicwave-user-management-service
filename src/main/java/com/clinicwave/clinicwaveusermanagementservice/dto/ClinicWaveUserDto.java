package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.entity.ClinicWaveUser;
import com.clinicwave.clinicwaveusermanagementservice.enums.GenderEnum;
import com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO (Data Transfer Object) for ClinicWaveUser.
 * This is used to transfer data about a ClinicWaveUser between processes or across network links.
 * It includes various fields related to a ClinicWaveUser and implements Serializable for ease of transfer.
 *
 * @author aamir on 6/16/24
 */
public record ClinicWaveUserDto(
        Long id,

        @NotBlank(message = "First name cannot be blank")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        String lastName,

        @NotBlank(message = "Mobile number cannot be blank")
        @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number format")
        @UniqueField(
                message = "Mobile number is already registered. Please use a different number",
                fieldName = "mobileNumber",
                domainClass = ClinicWaveUser.class
        )
        String mobileNumber,

        @NotBlank(message = "Username cannot be blank")
        @UniqueField(
                message = "username is already taken. Please choose a different username",
                fieldName = "username",
                domainClass = ClinicWaveUser.class
        )
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @UniqueField(
                message = "Account with this email address already exists. Please use a different email",
                fieldName = "email",
                domainClass = ClinicWaveUser.class
        )
        String email,

        @NotNull(message = "Date of birth cannot be null")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotNull(message = "Gender cannot be null")
        GenderEnum gender,

        String bio
) implements Serializable {
}