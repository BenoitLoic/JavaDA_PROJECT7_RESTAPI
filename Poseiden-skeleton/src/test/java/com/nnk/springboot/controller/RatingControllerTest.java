package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.GetRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.RatingServiceImpl;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
class RatingControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  RatingServiceImpl ratingService;
  @MockBean private UserDetailsServiceImpl userDetailsService;


  private final String homeUrl = "/rating/list";
  private final String createFormUrl = "/rating/add";
  private final String createUrl = "/rating/add";
  private final String updateFormUrl = "/rating/update/{id}";
  private final String updateUrl = "/rating/update/{id}";
  private final String deleteUrl = "/rating/delete/{id}";

  @Test
  void homeValid() throws Exception {

    // GIVEN
    // GetTradeDto(int tradeId, String account, String type, Double buyQuantity)
    GetRatingDto rating1 = new GetRatingDto();
    rating1.setFitchRating("fitchRatingTest");
    rating1.setMoodysRating("MOODYS");
    rating1.setSandPRating("sandPRRatrrf");
    rating1.setId(1);
    rating1.setOrderNumber(1);
    GetRatingDto rating2 = new GetRatingDto();
    rating2.setFitchRating("fitchRatingTest45");
    rating2.setMoodysRating("MOODYS54");
    rating2.setSandPRating("sandPRRatrrf25");
    rating2.setId(2);
    rating2.setOrderNumber(58);
    List<GetRatingDto> ratings = new ArrayList<>();
    ratings.add(rating1);
    ratings.add(rating2);
    // WHEN
    when(ratingService.getAllRating()).thenReturn(ratings);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/list"))
        .andExpect(model()
            .attribute("ratings", containsInAnyOrder(rating1, rating2)));
  }

  @Test
  void homeWhenNoRating_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(ratingService.getAllRating()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/list"))
        .andExpect(model()
            .attribute("ratings", new ArrayList<>()));
  }

  @Test
  void addRuleRatingForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("rating/add"));
  }

  @Test
  void validateValid() throws Exception {

    // GIVEN
    CreateRatingDto createRatingDto = new CreateRatingDto();
    createRatingDto.setFitchRating("fitchRatingTest");
    createRatingDto.setMoodysRating("MoodysRatingTest");
    createRatingDto.setSandPRating("SandPRatingTest");
    createRatingDto.setOrderNumber(5);

    String urlEncoded = getUrlEncoded(createRatingDto);

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
  void validateInvalid() throws Exception {

    // GIVEN
    CreateRatingDto createRatingDto = new CreateRatingDto();
    createRatingDto.setFitchRating("fitchRatingTest");
    createRatingDto.setMoodysRating("MoodysRatingTest");
    createRatingDto.setSandPRating("");
    createRatingDto.setOrderNumber(5);

    String urlEncoded = getUrlEncoded(createRatingDto);

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createRatingDto", "sandPRating"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("");
    updateRatingDto.setOrderNumber(5);




    // WHEN
    when(ratingService.getRatingWithId(5)).thenReturn(updateRatingDto);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("rating/update"))
        .andExpect(model().attribute("updateRatingDto", updateRatingDto))
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
  void updateRatingValid() throws Exception {

    // GIVEN
    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("SandPRatingTest");
    updateRatingDto.setOrderNumber(5);


    String urlEncoded = getUrlEncoded(updateRatingDto);

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(homeUrl));
  }

  @Test
  void updateRatingInvalid() throws Exception {

    // GIVEN
    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("");
    updateRatingDto.setOrderNumber(5);


    String urlEncoded = getUrlEncoded(updateRatingDto);


    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateRatingDto", "sandPRating"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateRatingWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("sandTest");
    updateRatingDto.setOrderNumber(5);


    String urlEncoded = getUrlEncoded(updateRatingDto);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(ratingService).updateRating(Mockito.anyInt(), Mockito.any(UpdateRatingDto.class));
    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void deleteRating() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteRRatingInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteRatingWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(ratingService).deleteRating(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }

}