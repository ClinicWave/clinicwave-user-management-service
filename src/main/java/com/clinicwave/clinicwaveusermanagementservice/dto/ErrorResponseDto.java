package com.clinicwave.clinicwaveusermanagementservice.dto;

import java.time.LocalDateTime;

/**
 * @author aamir on 6/16/24
 */
public record ErrorResponseDto(
        String apiPath,

        Integer httpStatusCode,

        String errorMessage,

        LocalDateTime errorTimestamp
) {
}
