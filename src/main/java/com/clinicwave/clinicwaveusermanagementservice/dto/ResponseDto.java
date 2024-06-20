package com.clinicwave.clinicwaveusermanagementservice.dto;

/**
 * @author aamir on 6/16/24
 */
public record ResponseDto(
        Integer httpStatusCode,

        String message
) {
}
