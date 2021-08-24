package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

  @Mock
  RatingRepository ratingRepositoryMock;
  @InjectMocks
  RatingServiceImpl ratingService;

  @Test
  void findAllRating() {

    // GIVEN
    List<Rating> ratings = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Rating temp = new Rating();
      temp.setId(i);
      temp.setFitchRating("fitch" + i);
      ratings.add(temp);
    }
    // WHEN
    when(ratingRepositoryMock.findAll()).thenReturn(ratings);
    // THEN
    assertEquals(3, ratingService.getAllRating().size());

  }

  @Test
  void findAllRatingEmptyList() {

    // GIVEN

    // WHEN
    when(ratingRepositoryMock.findAll()).thenReturn(new ArrayList<>());
    // THEN
    assertEquals(0, ratingService.getAllRating().size());

  }

  @Test
  void createRating() {
    // GIVEN
    CreateRatingDto createRatingDto = new CreateRatingDto();
    createRatingDto.setFitchRating("fitchRatingTest");
    createRatingDto.setMoodysRating("MoodysRatingTest");
    createRatingDto.setSandPRating("SandPRatingTest");
    createRatingDto.setOrderNumber(5);
    Rating expected = new Rating();
    expected.setFitchRating("fitchRatingTest");
    expected.setMoodysRating("MoodysRatingTest");
    expected.setSandPRating("SandPRatingTest");
    expected.setOrderNumber(5);
    // WHEN
    ratingService.createRating(createRatingDto);
    // THEN
    verify(ratingRepositoryMock, times(1)).save(expected);

  }


  @Test
  void getRatingWithIdValid() {
    // GIVEN
    Rating rating = new Rating();
    rating.setId(5);
    rating.setOrderNumber(2);
    // WHEN
    when(ratingRepositoryMock.findById(5)).thenReturn(Optional.of(rating));
    UpdateRatingDto actual = ratingService.getRatingWithId(5);
    // THEN
    assertEquals(5, actual.getId());
    assertEquals(2, actual.getOrderNumber());

  }

  @Test
  void getRatingWithIdWhenDataNotFind_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ratingRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    assertThrows(DataNotFoundException.class, () -> ratingService.getRatingWithId(1));

  }

  @Test
  void updateRatingValid() {
    // GIVEN

    Rating rating = new Rating();
    rating.setFitchRating("fitchRatingTest");
    rating.setMoodysRating("stringToUpdate");
    rating.setSandPRating("SandPRatingTest");
    rating.setOrderNumber(5);
    rating.setId(2);


    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("SandPRatingTest");
    updateRatingDto.setOrderNumber(5);
    updateRatingDto.setId(2);


    Rating expected = new Rating();
    expected.setFitchRating("fitchRatingTest");
    expected.setMoodysRating("MoodysRatingTest");
    expected.setSandPRating("SandPRatingTest");
    expected.setOrderNumber(5);
    expected.setId(2);
    // WHEN
    Mockito.when(ratingRepositoryMock.findById(anyInt())).thenReturn(Optional.of(rating));
    ratingService.updateRating(2, updateRatingDto);

    // THEN

    verify(ratingRepositoryMock).save(expected);


  }

  @Test
  void updateRatingWithNoChange() {
    // GIVEN

    Rating rating = new Rating();
    rating.setFitchRating("fitchRatingTest");
    rating.setMoodysRating("MoodysRatingTest");
    rating.setSandPRating("SandPRatingTest");
    rating.setOrderNumber(5);
    rating.setId(2);


    UpdateRatingDto updateRatingDto = new UpdateRatingDto();
    updateRatingDto.setFitchRating("fitchRatingTest");
    updateRatingDto.setMoodysRating("MoodysRatingTest");
    updateRatingDto.setSandPRating("SandPRatingTest");
    updateRatingDto.setOrderNumber(5);
    updateRatingDto.setId(2);
    // WHEN
    when(ratingRepositoryMock.findById(anyInt())).thenReturn(Optional.of(rating));
    ratingService.updateRating(5, updateRatingDto);
    // THEN
    verify(ratingRepositoryMock, times(1)).save(rating);

  }

  @Test
  void updateRatingWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ratingRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> ratingService.updateRating(2, any()));
  }

  @Test
  void deleteRuleNameValid() {
    // GIVEN

    // WHEN
    when(ratingRepositoryMock.findById(2)).thenReturn(Optional.of(new Rating()));
    ratingService.deleteRating(2);
    // THEN
    verify(ratingRepositoryMock, times(1)).deleteById(2);

  }

  @Test
  void deleteRuleNameWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ratingRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> ratingService.deleteRating(1));

  }


}