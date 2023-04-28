package com.stackoverflow.web;

import java.io.IOException;

import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackoverflow.validation.ValidMediaType;

@Validated
@Controller
@RequestMapping("/validated")
public class ValidatedController {
  @Autowired
  ObjectMapper objectMapper;

  @ResponseBody
  @PutMapping("/gif")
  public String gifController(
      @RequestPart @Valid @ValidMediaType(MediaType.IMAGE_GIF_VALUE) MultipartFile someFile) {
    return "validated/gif";
  }

  @ResponseBody
  @PutMapping("/jpeg")
  public String jpegController(
      @RequestPart @Valid @ValidMediaType(MediaType.IMAGE_JPEG_VALUE) MultipartFile someFile) {
    return "validated/jpeg";
  }

  @PutMapping(value = "/pdf/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> pdfController(
      @PathVariable Integer id,
      @Valid @ValidMediaType(MediaType.APPLICATION_PDF_VALUE) @RequestPart MultipartFile someFile) {
    return new ResponseEntity<>(someFile.getOriginalFilename(), HttpStatus.OK);
  }

  @SuppressWarnings("java:S106")
  @PutMapping(value = "/pdf/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> pdf1Controller(
      @Valid @ValidMediaType(MediaType.APPLICATION_JSON_VALUE) @RequestPart Part someJson,
      @Valid @ValidMediaType(MediaType.APPLICATION_PDF_VALUE) @RequestPart MultipartFile someFile) throws IOException {
    System.err.println(objectMapper.readValue(someJson.getInputStream(), SomeDto.class));
    return new ResponseEntity<>(someFile.getOriginalFilename(), HttpStatus.OK);
  }

}

@lombok.Value(staticConstructor = "of")
class SomeDto {
  String foo;
  String bar;
}
