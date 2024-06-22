package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when a resource is not found in the system.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.NOT_FOUND when thrown.
 * The exception takes in resourceName, fieldName, and fieldValue as parameters to construct a detailed error message.
 *
 * @author aamir on 6/16/24
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
public class ResourceNotFoundException extends RuntimeException {
  /**
   * Constructs a new ResourceNotFoundException with the specified detail message.
   *
   * @param resourceName the name of the resource that was not found
   * @param fieldName    the name of the field that was searched for
   * @param fieldValue   the value of the field that was searched for
   */
  public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("%s with %s: %s not found", resourceName, fieldName, fieldValue));
  }
}