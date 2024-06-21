package com.clinicwave.clinicwaveusermanagementservice.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aamir on 6/18/24
 */
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
  private final EntityManager entityManager;
  private String fieldName;
  private Class<?> domainClass;

  @Autowired
  public UniqueFieldValidator(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void initialize(UniqueField constraintAnnotation) {
    this.fieldName = constraintAnnotation.fieldName();
    this.domainClass = constraintAnnotation.domainClass();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Query query = entityManager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + fieldName + "=:value");
    query.setParameter("value", value);
    return query.getResultList().isEmpty();
  }
}
