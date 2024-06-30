package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.enums.UserTypeEnum;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO (Data Transfer Object) for UserType.
 * This is used to transfer data about a UserType between processes or across network links.
 * It includes various fields related to a UserType and implements Serializable for ease of transfer.
 *
 * @author aamir on 6/16/24
 */
public record UserTypeDto(
        Long id,

        @NotBlank(message = "User type cannot be blank")
        UserTypeEnum type
) implements Serializable {
}