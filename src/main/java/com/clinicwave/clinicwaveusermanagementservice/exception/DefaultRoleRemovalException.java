package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when there is an attempt to remove a default role.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.BAD_REQUEST when thrown.
 * The exception takes in resourceName, fieldName, and fieldValue as parameters to construct a detailed error message.
 *
 * @author aamir on 7/1/24
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot remove default role")
public class DefaultRoleRemovalException extends RuntimeException {
  /**
   * Constructs a new DefaultRoleRemovalException with the specified detail message.
   *
   * @param resourceName the name of the resource where the default role removal was attempted
   * @param fieldName    the name of the field that caused the default role removal
   * @param fieldValue   the value of the field that caused the default role removal
   */
  public DefaultRoleRemovalException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("Cannot remove default role from %s with %s: %s", resourceName, fieldName, fieldValue));
  }
}
