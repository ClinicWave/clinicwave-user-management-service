package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents an exception that is thrown when a verification code has already been used.
 *
 * @author aamir on 7/21/24
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Verification code already used")
public class VerificationCodeAlreadyUsedException extends RuntimeException {
  /**
   * Constructs a new VerificationCodeAlreadyUsedException with the given resource name, field name, and field value.
   *
   * @param resourceName the name of the resource that has the verification code that has already been used
   * @param fieldName    the name of the field that has the verification code that has already been used
   * @param fieldValue   the value of the field that has the verification code that has already been used
   */
  public VerificationCodeAlreadyUsedException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s with %s %s has already been used", resourceName, fieldName, fieldValue));
  }
}
