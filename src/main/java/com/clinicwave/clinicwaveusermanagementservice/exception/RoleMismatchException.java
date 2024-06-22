package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when there is a mismatch between the user's role and the expected role.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.BAD_REQUEST when thrown.
 * The exception takes in `resourceName`, `fieldName`, and `fieldValue` as parameters to construct a detailed error message.
 * The error message will indicate that the role with the specified ID does not match the user's roleId.
 * For example, this may be thrown when attempting to modify a user's role with an incorrect role ID.
 *
 * @author aamir on 6/18/24
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Role mismatch")
public class RoleMismatchException extends RuntimeException {
  /**
   * Constructs a new RoleMismatchException with the specified detail message.
   *
   * @param resourceName the name of the resource where the role mismatch occurred
   * @param fieldName    the name of the field that caused the role mismatch
   * @param fieldValue   the value of the field that caused the role mismatch
   */
  public RoleMismatchException(String resourceName, String fieldName, Long fieldValue) {
    super(String.format("%s with %s %s does not match user's roleId", resourceName, fieldName, fieldValue));
  }
}
