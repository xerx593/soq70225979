package com.stackoverflow.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.springframework.http.MediaType;

@Documented
@Constraint(validatedBy = MediaTypeValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMediaType {

  String value() default MediaType.ALL_VALUE;

  String message() default "{javax.validation.constraints.ValidMediaType.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
