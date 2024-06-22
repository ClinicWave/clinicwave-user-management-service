package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when a resource already exists in the system.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.CONFLICT when thrown.
 * The exception takes in resourceName, fieldName, and fieldValue as parameters to construct a detailed error message.
 *
 * @author aamir on 6/9/24
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Resource already exists")
public class ResourceAlreadyExistsException extends RuntimeException {
  /**
   * Constructs a new ResourceAlreadyExistsException with the specified detail message.
   *
   * @param resourceName the name of the resource that already exists
   * @param fieldName the name of the field that caused the conflict
   * @param fieldValue the value of the field that caused the conflict
   */
  public ResourceAlreadyExistsException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("%s with %s: %s already exists", resourceName, fieldName, fieldValue));
  }
}
