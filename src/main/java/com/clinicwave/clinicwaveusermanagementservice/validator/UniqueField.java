package com.clinicwave.clinicwaveusermanagementservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * This is a custom annotation used for validating that a field's value is unique in the database.
 * It is used in conjunction with the UniqueFieldValidator class.
 * The annotation can be applied to methods and fields.
 * It is retained at runtime.
 *
 * @author aamir on 6/18/24
 */
@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
  /**
   * Default error message when the validation fails.
   */
  String message() default "{com.clinicwave.clinicwaveusermanagementservice.validator.UniqueField.message}";

  /**
   * Groups can be used to categorize constraints.
   */
  Class<?>[] groups() default {};

  /**
   * Payload can be used to assign custom severity levels to constraint violations.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * The name of the field that is being validated for uniqueness.
   */
  String fieldName();

  /**
   * The domain class where the field resides.
   */
  Class<?> domainClass();
}