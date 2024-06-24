package com.clinicwave.clinicwaveusermanagementservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * A DTO (Data Transfer Object) for validation error responses.
 * This is used to standardize the structure of validation error responses sent by the API.
 *
 * @author aamir on 6/24/24
 */
public record ValidationErrorResponseDto(
        // The API path where the error occurred
        String apiPath,

        // The HTTP status code of the error
        Integer httpStatusCode,

        // The error message
        String errorMessage,

        // The timestamp when the error occurred
        LocalDateTime errorTimestamp,

        // A map of field names to error messages
        Map<String, String> errors
) {
}
