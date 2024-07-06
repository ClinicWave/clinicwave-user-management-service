package com.clinicwave.clinicwaveusermanagementservice.exception;

import com.clinicwave.clinicwaveusermanagementservice.enums.RoleNameEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception that is thrown when a role is attempted to be assigned to a resource that already has the role assigned.
 * It is annotated with @ResponseStatus to automatically return a HttpStatus.CONFLICT when thrown.
 * The exception takes in resourceName, fieldName, fieldValue, and roleName as parameters to construct a detailed error message.
 * The exception is thrown when a role is attempted to be assigned to a resource that already has the role assigned.
 *
 * @author aamir on 7/5/24
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate role assignment")
public class DuplicateRoleAssignmentException extends RuntimeException {
  /**
   * Constructs a new DuplicateRoleAssignmentException with the specified detail message.
   *
   * @param resourceName the name of the resource that already exists
   * @param fieldName    the name of the field that caused the conflict
   * @param fieldValue   the value of the field that caused the conflict
   * @param roleName     the name of the role that was attempted to be assigned
   */
  public DuplicateRoleAssignmentException(String resourceName, String fieldName, Long fieldValue, RoleNameEnum roleName) {
    super(String.format("%s with %s : '%s' already has the role '%s' assigned", resourceName, fieldName, fieldValue, roleName));
  }
}
