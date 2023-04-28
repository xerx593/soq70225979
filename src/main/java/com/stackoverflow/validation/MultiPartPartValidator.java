package com.stackoverflow.validation;

import javax.servlet.http.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.MediaType;

class MultiPartPartValidator implements ConstraintValidator<ValidMediaType, Part> {

  private String allowed;

  @Override
  public void initialize(ValidMediaType constraintAnnotation) {
    allowed = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(Part value, ConstraintValidatorContext context) {
    return value == null
        || allowed.equals(MediaType.ALL_VALUE)
        || allowed.equals(value.getContentType());
  }

}
