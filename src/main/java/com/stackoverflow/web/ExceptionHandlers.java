package com.stackoverflow.web;

import javax.validation.ValidationException;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorDto(ex.getMessage()));
  }

  @Value
  private static class ErrorDto {

    final String message;
  }
}
