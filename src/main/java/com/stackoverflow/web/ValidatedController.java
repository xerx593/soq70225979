package com.stackoverflow.web;

import javax.validation.Valid;

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

import com.stackoverflow.validation.ValidMediaType;

@Validated
@Controller
@RequestMapping("/validated")
public class ValidatedController {

  @ResponseBody
  @PutMapping("/gif")
  public String gifController(
      @RequestPart @Valid @ValidMediaType(MediaType.IMAGE_GIF_VALUE) MultipartFile image) {
    return "validated/gif";
  }

  @ResponseBody
  @PutMapping("/jpeg")
  public String jpegController(
      @RequestPart @Valid @ValidMediaType(MediaType.IMAGE_JPEG_VALUE) MultipartFile image) {
    return "validated/jpeg";
  }

  @PutMapping(value = "/pdf/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> pdfController(
      @PathVariable Integer id,
      @Valid @ValidMediaType(MediaType.APPLICATION_PDF_VALUE) @RequestPart("custom_file") MultipartFile file) {
    return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
  }
}
