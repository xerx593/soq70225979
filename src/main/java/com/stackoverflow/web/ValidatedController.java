package com.stackoverflow.web;

import com.stackoverflow.validation.ValidMediaType;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Validated
@Controller
@RequestMapping("/validated")
public class ValidatedController {

  @ResponseBody
  @PutMapping("/gif")
  public String gifController(
      @RequestPart @Valid
      @ValidMediaType(MediaType.IMAGE_GIF_VALUE) MultipartFile image) {
    return "validated/gif";
  }

  @ResponseBody
  @PutMapping("/jpeg")
  public String jpegController(
      @RequestPart @Valid
      @ValidMediaType(MediaType.IMAGE_JPEG_VALUE) MultipartFile image) {
    return "validated/jpeg";
  }
}
