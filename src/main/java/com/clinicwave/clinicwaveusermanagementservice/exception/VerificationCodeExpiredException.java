package com.clinicwave.clinicwaveusermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents an exception that is thrown when a verification code has expired.
 *
 * @author aamir on 7/21/24
 */
@ResponseStatus(value = HttpStatus.GONE, reason = "Verification code expired")
public class VerificationCodeExpiredException extends RuntimeException {
  /**
   * Constructs a new VerificationCodeExpiredException with the given resource name, field name, and field value.
   *
   * @param resourceName the name of the resource that has the expired verification code
   * @param fieldName    the name of the field that has the expired verification code
   * @param fieldValue   the value of the field that has the expired verification code
   */
  public VerificationCodeExpiredException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s with %s %s has expired", resourceName, fieldName, fieldValue));
  }
}
