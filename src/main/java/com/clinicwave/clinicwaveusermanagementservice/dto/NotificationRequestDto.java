package com.clinicwave.clinicwaveusermanagementservice.dto;

import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationCategoryEnum;
import com.clinicwave.clinicwaveusermanagementservice.enums.NotificationTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents a data transfer object for a notification request.
 *
 * @author aamir on 7/11/24
 */
public record NotificationRequestDto(
        @Email(message = "Recipient email address is invalid")
        @NotBlank(message = "Recipient email address is required")
        String recipient,

        @NotBlank(message = "Subject is required")
        String subject,

        @NotBlank(message = "Template name is required")
        String templateName,

        Map<String, Object> templateVariables,

        @NotNull(message = "Notification type is required")
        NotificationTypeEnum type,

        @NotNull(message = "Notification category is required")
        NotificationCategoryEnum category
) implements Serializable {
}
