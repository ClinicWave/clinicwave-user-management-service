package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when a user is inactive.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.BAD_REQUEST when thrown.
 * The exception takes in resourceName, fieldName, and fieldValue as parameters to construct a detailed error message.
 * The exception is thrown when a user is inactive.
 *
 * @author aamir on 7/7/24
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Inactive user")
public class InactiveUserException extends RuntimeException {
  /**
   * Constructs a new InactiveUserException with the specified detail message.
   *
   * @param resourceName the name of the resource that is inactive
   * @param fieldName    the name of the field that caused the conflict
   * @param fieldValue   the value of the field that caused the conflict
   */
  public InactiveUserException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("%s with %s %d is inactive", resourceName, fieldName, fieldValue));
  }
}
