package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.domain.UserType;
import com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
        @UniqueField(fieldName = "type", domainClass = UserType.class)
        String type
) implements Serializable {
}