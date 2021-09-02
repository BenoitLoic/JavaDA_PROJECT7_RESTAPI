package com.nnk.springboot.integrationTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CurvePointIT {

  @Autowired
  MockMvc              mockMvc;
  @Autowired
  CurvePointRepository curvePointRepository;

  private final static String homeUrl       = "/curvePoint/list";
  private final static String createFormUrl = "/curvePoint/add";
  private final static String createUrl     = "/curvePoint/add";
  private final static String updateFormUrl = "/curvePoint/update/{id}";
  private final static String updateUrl     = "/curvePoint/update/{id}";
  private final static String deleteUrl     = "/curvePoint/delete/{id}";


  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(model().attribute("curvePoints", iterableWithSize(3)))
        .andExpect(view().name("curvePoint/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addCurvePointForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));


  }


  @Test
  @Transactional
  public void validate() throws Exception {


    CreateCurvePointDto valid = new CreateCurvePointDto();
    valid.setCurveId(5);

    String urlEncoded        = getUrlEncoded(valid);
    String urlEncodedValid   = urlEncoded.replace("asOfDate=null", "asOfDate=2077-06-08 11:30");
    String urlEncodedInvalid = urlEncoded.replace("asOfDate=null", "asOfDate=2077-22-08 11:30");

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(urlEncodedValid))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(urlEncodedInvalid))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createCurvePointDto", "asOfDate"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

    mockMvc
        .perform(
            get(updateFormUrl, 999)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateCurvePoint() throws Exception {

    UpdateCurvePointDto valid = new UpdateCurvePointDto();
    valid.setId(2);
    valid.setCurveId(5);
    String urlEncoded        = getUrlEncoded(valid);
    String urlEncodedValid   = urlEncoded.replace("asOfDate=null", "asOfDate=2077-06-08 11:30");
    String urlEncodedInvalid = urlEncoded.replace("asOfDate=null", "asOfDate=2077-16-22 11:30");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .content(urlEncodedValid)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/curvePoint/list"));
    Optional<CurvePoint> curve = curvePointRepository.findById(1);
    assertTrue(curve.isPresent());
    assertEquals(5, curve.get().getCurveId());

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(urlEncodedInvalid))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateCurvePointDto", "asOfDate"));
  }

  @Test
  @Transactional
  public void deleteCurvePoint() throws Exception {

    Optional<CurvePoint> cpWithId1 = curvePointRepository.findById(1);
    assertTrue(cpWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(curvePointRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }
}
