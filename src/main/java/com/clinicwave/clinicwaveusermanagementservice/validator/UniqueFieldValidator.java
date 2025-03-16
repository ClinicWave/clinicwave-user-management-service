package com.clinicwave.clinicwaveusermanagementservice.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

/**
 * This class implements the ConstraintValidator interface and defines the logic to validate a constraint of type UniqueField.
 * It uses the EntityManager to query the database and check if a given value is unique for a specified field in a specified domain class.
 *
 * @author aamir on 6/18/24
 */
@AllArgsConstructor
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
  private final EntityManager entityManager;
  private String fieldName;
  private Class<?> domainClass;

  /**
   * Initializes the validator with the constraint annotation instance.
   * It extracts the fieldName and domainClass from the annotation instance.
   *
   * @param constraintAnnotation the annotation instance
   */
  @Override
  public void initialize(UniqueField constraintAnnotation) {
    this.fieldName = constraintAnnotation.fieldName();
    this.domainClass = constraintAnnotation.domainClass();
  }

  /**
   * Validates the value by querying the database to check if it is unique for the specified field in the specified domain class.
   * It returns true if the value is unique, and false otherwise.
   *
   * @param value   the value to be validated
   * @param context the context in which the constraint is evaluated
   * @return true if the value is unique, false otherwise
   */
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Query query = entityManager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + fieldName + "=:value");
    query.setParameter("value", value);
    return query.getResultList().isEmpty();
  }
}