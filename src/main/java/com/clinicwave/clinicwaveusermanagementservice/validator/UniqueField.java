package com.clinicwave.clinicwaveusermanagementservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author aamir on 6/18/24
 */
@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
  String message() default "{com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String fieldName();

  Class<?> domainClass();
}
