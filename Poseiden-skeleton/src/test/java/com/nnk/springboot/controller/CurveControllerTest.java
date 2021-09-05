package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.GetCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.CurveServiceImpl;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CurveController.class)
@AutoConfigureMockMvc(addFilters = false)
class CurveControllerTest {

  @Autowired
  MockMvc          mockMvc;
  @MockBean
  CurveServiceImpl curveServiceMock;
  @MockBean
  private UserDetailsServiceImpl userDetailsService;

  private final static String homeUrl       = "/curvePoint/list";
  private final static String createFormUrl = "/curvePoint/add";
  private final static String createUrl     = "/curvePoint/add";
  private final static String updateFormUrl = "/curvePoint/update/{id}";
  private final static String updateUrl     = "/curvePoint/update/{id}";
  private final static String deleteUrl     = "/curvePoint/delete/{id}";

  @Test
  void homeValid() throws Exception {

    // GIVEN
    // GetTradeDto(int tradeId, String account, String type, Double buyQuantity)
    GetCurvePointDto curvePoint1 = new GetCurvePointDto();
    curvePoint1.setId(1);
    curvePoint1.setCurveId(6);
    GetCurvePointDto curvePoint2 = new GetCurvePointDto();
    curvePoint1.setId(2);
    curvePoint1.setCurveId(5);
    List<GetCurvePointDto> points = new ArrayList<>();
    points.add(curvePoint1);
    points.add(curvePoint2);
    // WHEN
    when(curveServiceMock.getAllCurvePoint()).thenReturn(points);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/list"))
        .andExpect(model()
            .attribute("curvePoints", containsInAnyOrder(curvePoint1, curvePoint2)));
  }

  @Test
  void homeWhenNoTrade_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(curveServiceMock.getAllCurvePoint()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/list"))
        .andExpect(model()
            .attribute("curvePoints", new ArrayList<>()));
  }

  @Test
  void addCurvePointForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("curvePoint/add"));
  }

  @Test
  void createCurvePointValid() throws Exception {

    // GIVEN
    CreateCurvePointDto temp = new CreateCurvePointDto();
    temp.setCurveId(5);
    String urlEncoded = getUrlEncoded(temp);
    urlEncoded = urlEncoded.replace("asOfDate=null", "asOfDate=2021-08-23 12:16");

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));
  }


  @Test
  void createCurvePointInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("curveId", "5")
            .param("asOfDate", "2022-03-23")) // missing time 'T'HH:mm
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createCurvePointDto", "asOfDate"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateCurvePointDto updateCurvePointDto = new UpdateCurvePointDto();
    updateCurvePointDto.setCurveId(2);
    updateCurvePointDto.setId(5);

    // WHEN
    when(curveServiceMock.getPointWithId(5)).thenReturn(updateCurvePointDto);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(model().attribute("updateCurvePointDto", updateCurvePointDto))
        .andExpect(status().isOk());

  }

  @Test
  void showUpdateFormInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void updateCurvePointValid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .param("curveId", "5")
                .param("asOfDate", "2022-03-23 16:59")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(homeUrl));
  }

  @Test
  void updateCurvePointInvalid() throws Exception {

    // GIVEN


    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .param("asOfDate", "2022-31-23") // missing Time param
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateCurvePointDto", "asOfDate"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateCurvePointWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(curveServiceMock).updateCurvePoint(
        Mockito.anyInt(), Mockito.any(UpdateCurvePointDto.class));
    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .param("curveId", "2")
                .param("asOfDate",
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString().replace("T",
                        " "))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(
            result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void deleteCurvePoint() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteCurvePointInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteCurvePointWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(curveServiceMock).deleteCurvePoint(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(
            result.getResolvedException() instanceof DataNotFoundException));

  }
}