package com.clinicwave.clinicwaveusermanagementservice.exception;

import com.clinicwave.clinicwaveusermanagementservice.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * @author aamir on 6/9/24
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGlobalException(
          Exception exception,
          WebRequest webRequest
  ) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage(),
            LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(
          Exception exception,
          WebRequest webRequest
  ) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.CONFLICT.value(),
            exception.getMessage(),
            LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
          Exception exception,
          WebRequest webRequest
  ) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage(),
            LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RoleMismatchException.class)
  public ResponseEntity<ErrorResponseDto> handleRoleMismatchException(
          Exception exception,
          WebRequest webRequest
  ) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST.value(),
            exception.getMessage(),
            LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }
}
