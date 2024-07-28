package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents an exception that is thrown when a verification code is invalid.
 *
 * @author aamir on 7/21/24
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid verification code")
public class InvalidVerificationCodeException extends RuntimeException {
  /**
   * Constructs a new InvalidVerificationCodeException with the given resource name, field name, and field value.
   *
   * @param resourceName the name of the resource that has the invalid verification code
   * @param fieldName    the name of the field that has the invalid verification code
   * @param fieldValue   the value of the field that has the invalid verification code
   */
  public InvalidVerificationCodeException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s with %s %s is invalid", resourceName, fieldName, fieldValue));
  }
}
