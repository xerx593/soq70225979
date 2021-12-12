package com.stackoverflow.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

class MediaTypeValidator implements ConstraintValidator<ValidMediaType, MultipartFile> {

  private String allowed;

  @Override
  public void initialize(ValidMediaType constraintAnnotation) {
    allowed = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
    return value == null
        || allowed.equals(MediaType.ALL_VALUE)
        || allowed.equals(value.getContentType());
  }

}
