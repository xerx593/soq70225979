package com.stackoverflow;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class Soq70225979ApplicationTests {

  MockMultipartFile mockJpeg;
  MockMultipartFile mockGif;
  MockMultipartFile mockPdf;

  @Autowired
  MockMvc mockMvc;
  @Value("classpath:test.json")
  Resource testJson;

  @Test
  void testValidatedValidGif() throws Exception {
    mockMvc.perform(putMultipart("/validated/gif")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  @Test
  void testValidatedValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/validated/jpeg")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  void testValidatedInvalidGif() throws Exception {
    mockMvc.perform(putMultipart("/validated/gif")
        .file(mockJpeg))
        .andExpectAll(status().isBadRequest(), content().string(endsWith("image/jpeg.\"}")));
  }

  @Test
  void testValidatedInvalidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/validated/jpeg")
        .file(mockGif))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(endsWith("image/gif.\"}")));
  }

  @Test
  void testPdfInvalid() throws Exception {
    mockMvc.perform(
        putMultipart("/validated/pdf/1")
            .file(mockJpeg)) // ??
        .andExpect(status().isBadRequest()) // !
        .andExpect(content().string(endsWith("image/jpeg.\"}")));
  }

  @Test
  void testPdfValid() throws Exception {
    mockMvc.perform(
        putMultipart("/validated/pdf/1")
            .file(mockPdf)) // !
        .andExpectAll(status().isOk(), // !!
            content().string("aha.pdf"));
  }

  @Test
  void testPdfJsonValid() throws Exception {
    MockPart jsonPart = new MockPart("someJson", "test.json", testJson.getInputStream().readAllBytes());
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(
        putMultipart("/validated/pdf/json")
            .file(mockPdf)
            .part(jsonPart)) // !
        .andExpectAll(status().isOk(), // !!
            content().string("aha.pdf"));
  }

  @Test
  void testPdfJsonInvalid() throws Exception {
    mockMvc.perform(
        putMultipart("/validated/pdf/json")
            .file(mockPdf)
            .part(new MockPart("someJson", "test.json", testJson.getInputStream().readAllBytes()))) // ??
        .andExpect(status().isBadRequest()); // !
  }

  @Test
  void testNotValidatedValidGif() throws Exception {
    mockMvc.perform(putMultipart("/gif")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  @Test
  void testNotValidatedValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/jpeg")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  void testNotValidatedInValidGif() throws Exception {
    mockMvc.perform(putMultipart("/gif")
        .file(mockJpeg))
        .andExpect(status().isOk());
  }

  @Test
  void testNotValidatedInValidJpeg() throws Exception {
    mockMvc.perform(putMultipart("/jpeg")
        .file(mockGif))
        .andExpect(status().isOk());
  }

  Soq70225979ApplicationTests(
      @Value("classpath:dang.gif") Resource testGif,
      @Value("classpath:dang.jpg") Resource testJpeg) throws IOException {
    mockJpeg = new MockMultipartFile(
        "someFile",
        "dang.jpg",
        MediaType.IMAGE_JPEG_VALUE,
        testJpeg.getInputStream());
    mockGif = new MockMultipartFile(
        "someFile",
        "dang.gif",
        MediaType.IMAGE_GIF_VALUE,
        testGif.getInputStream());
    mockPdf = new MockMultipartFile(
        "someFile",
        "aha.pdf",
        MediaType.APPLICATION_PDF_VALUE, // !
        (byte[]) null);
  }

  private static MockMultipartHttpServletRequestBuilder putMultipart(String url) {
    MockMultipartHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.multipart(url);
    reqBuilder.with((MockHttpServletRequest request) -> {
      request.setMethod(HttpMethod.PUT.name());
      return request;
    });
    return reqBuilder;
  }
}
