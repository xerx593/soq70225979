package com.stackoverflow;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Soq70225979ApplicationTests {

  static MockMultipartFile mockJpeg;
  static MockMultipartFile mockGif;

  @Autowired
  MockMvc mockMvc;

  @Test
  public void testValidatedValidGif() throws Exception {
    mockMvc.perform(putMultipart("/validated/gif")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  @Test
  public void testValidatedValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/validated/jpeg")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  public void testValidatedInvalidGif() throws Exception {
    mockMvc.perform(putMultipart("/validated/gif")
        .file(mockJpeg))
        .andExpect(status().isBadRequest());//.andExpect(content().string(endsWith("image/jpeg.\"}")));
  }

  @Test
  public void testValidatedInvalidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/validated/jpeg")
        .file(mockGif))
        .andExpect(status().isBadRequest());//.andExpect(content().string(endsWith("image/gif.\"}")));
  }

  @Test
  public void testNotValidatedValidGif() throws Exception {
    mockMvc.perform(putMultipart("/gif")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  @Test
  public void testNotValidatedValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/jpeg")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  public void testNotValidatedInValidGif() throws Exception {
    mockMvc.perform(putMultipart("/gif")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  public void testNotValidatedInValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/jpeg")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  @BeforeAll
  public static void initFile() throws IOException {
    mockJpeg = new MockMultipartFile(
        "image",
        "dang.jpg",
        MediaType.IMAGE_JPEG_VALUE,
        new ClassPathResource("dang.jpg").getInputStream()
    );
    mockGif = new MockMultipartFile(
        "image",
        "dang.gif",
        MediaType.IMAGE_GIF_VALUE,
        new ClassPathResource("dang.gif").getInputStream()
    );
  }

  private static MockMultipartHttpServletRequestBuilder putMultipart(String url) {
    MockMultipartHttpServletRequestBuilder gifBuilder
        = MockMvcRequestBuilders.multipart(url);
    gifBuilder.with((MockHttpServletRequest request) -> {
      request.setMethod(HttpMethod.PUT.name());
      return request;
    });
    return gifBuilder;
  }
}
