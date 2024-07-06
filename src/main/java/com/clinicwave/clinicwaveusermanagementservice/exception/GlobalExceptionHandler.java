package com.clinicwave.clinicwaveusermanagementservice.exception;

import com.clinicwave.clinicwaveusermanagementservice.dto.ErrorResponseDto;
import com.clinicwave.clinicwaveusermanagementservice.dto.ValidationErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is a global exception handler for the application.
 * It extends the ResponseEntityExceptionHandler class provided by Spring, which provides centralized exception handling across all methods through @ExceptionHandler methods.
 * It is annotated with @ControllerAdvice, which makes it applicable to all controllers in the application.
 * <p>
 * The class defines several methods, each annotated with @ExceptionHandler and handling a specific type of exception.
 * When an exception to that type is thrown anywhere in the application, the corresponding method in this class will be invoked to handle it.
 * Each method constructs an ErrorResponseDto object containing details about the exception and returns it in the response entity.
 * <p>
 * The handleMethodArgumentNotValid method is overridden from ResponseEntityExceptionHandler to provide custom handling for MethodArgumentNotValidException,
 * which is thrown when validation on an argument annotated with @Valid fails.
 *
 * @author aamir on 6/9/24
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * Handles all types of exceptions.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGlobalException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles ResourceAlreadyExistsException.
   */
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.CONFLICT);
  }

  /**
   * Handles ResourceNotFoundException.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles RoleMismatchException.
   */
  @ExceptionHandler(RoleMismatchException.class)
  public ResponseEntity<ErrorResponseDto> handleRoleMismatchException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles DuplicateRoleAssignmentException.
   */
  @ExceptionHandler(DuplicateRoleAssignmentException.class)
  public ResponseEntity<ErrorResponseDto> handleDuplicateRoleAssignmentException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.CONFLICT);
  }

  /**
   * Handles DefaultRoleRemovalException.
   */
  @ExceptionHandler(DefaultRoleRemovalException.class)
  public ResponseEntity<ErrorResponseDto> handleDefaultRoleRemovalException(
          Exception exception,
          WebRequest webRequest
  ) {
    return createErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles MethodArgumentNotValidException.
   * This exception is thrown when validation on an argument annotated with @Valid fails.
   *
   * @param exception  the exception that was thrown
   * @param headers    the headers for the response
   * @param status     the status code for the response
   * @param webRequest the current web request
   * @return a response entity containing a ValidationErrorResponseDto
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException exception,
          @NonNull HttpHeaders headers,
          HttpStatusCode status,
          WebRequest webRequest
  ) {
    Map<String, String> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .filter(error -> error.getDefaultMessage() != null)
            .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing
            ));

    ValidationErrorResponseDto validationErrorResponseDto = new ValidationErrorResponseDto(
            webRequest.getDescription(false),
            status.value(),
            "Validation failed",
            LocalDateTime.now(),
            errors
    );

    return new ResponseEntity<>(validationErrorResponseDto, status);
  }

  private ResponseEntity<ErrorResponseDto> createErrorResponse(
          Exception exception,
          WebRequest webRequest,
          HttpStatus status
  ) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            status.value(),
            exception.getMessage(),
            LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDto, status);
  }
}
