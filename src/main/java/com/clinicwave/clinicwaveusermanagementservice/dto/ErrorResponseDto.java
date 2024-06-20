package com.clinicwave.clinicwaveusermanagementservice.dto;

import java.time.LocalDateTime;

/**
 * A DTO (Data Transfer Object) for error responses.
 * This is used to standardize the structure of error responses sent by the API.
 *
 * @author aamir on 6/16/24
 */
public record ErrorResponseDto(
        // The API path where the error occurred
        String apiPath,

        // The HTTP status code of the error
        Integer httpStatusCode,

        // The error message
        String errorMessage,

        // The timestamp when the error occurred
        LocalDateTime errorTimestamp
) {
}