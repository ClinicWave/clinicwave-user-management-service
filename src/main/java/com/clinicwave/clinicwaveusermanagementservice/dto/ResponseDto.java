package com.clinicwave.clinicwaveusermanagementservice.dto;

/**
 * A DTO (Data Transfer Object) for general responses.
 * This is used to standardize the structure of generic responses sent by the API.
 *
 * @author aamir on 6/16/24
 */
public record ResponseDto(
        // The HTTP status code of the response
        Integer httpStatusCode,

        // The message of the response
        String message
) {
}