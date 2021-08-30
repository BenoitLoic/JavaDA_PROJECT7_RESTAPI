package com.nnk.springboot.integrationTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RatingIT {

  @Autowired
  RatingRepository ratingRepository;
  @Autowired
  MockMvc mockMvc;

  private final String homeUrl = "/rating/list";
  private final String createFormUrl = "/rating/add";
  private final String createUrl = "/rating/add";
  private final String updateFormUrl = "/rating/update/{id}";
  private final String updateUrl = "/rating/update/{id}";
  private final String deleteUrl = "/rating/delete/{id}";

  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(model().attribute("ratings", iterableWithSize(3)))
        .andExpect(view().name("rating/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addRatingForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/add"));

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

    CreateRatingDto valid = new CreateRatingDto();
    valid.setFitchRating("fitchRatingTest");
    valid.setMoodysRating("MoodysRatingTest");
    valid.setSandPRating("SandPRatingTest");
    valid.setOrderNumber(5);
    CreateRatingDto invalid = new CreateRatingDto();
    invalid.setFitchRating("");
    invalid.setMoodysRating("MoodysRatingTest");
    invalid.setSandPRating("SandPRatingTest");
    invalid.setOrderNumber(5);
    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createRatingDto", "fitchRating"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/update"));

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
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateRating() throws Exception {

    UpdateRatingDto valid = new UpdateRatingDto();
    valid.setFitchRating("fitchRatingTest");
    valid.setMoodysRating("MoodysRatingTest");
    valid.setSandPRating("SandPRatingTest");
    valid.setOrderNumber(5);
    String urlEncoded = getUrlEncoded(valid);

    UpdateRatingDto invalid = new UpdateRatingDto();
    invalid.setFitchRating("");
    invalid.setMoodysRating("MoodysRatingTest");
    invalid.setSandPRating("SandPRatingTest");
    invalid.setOrderNumber(5);

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/rating/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
        .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateRatingDto", "fitchRating"));
  }

  @Test
  @Transactional
  public void deleteRuleName() throws Exception {

    Optional<Rating> ratingWithId1 = ratingRepository.findById(1);
    assertTrue(ratingWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
        .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(ratingRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest")
                    .roles("USER"))
        .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }
}
