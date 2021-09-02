package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.GetRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import java.util.Collection;

/**
 * Interface for RatingService.
 * Contains method used by Rating CRUD controller.
 */
public interface RatingService {

  /**
   * Get all rating saved in db.
   *
   * @return collection of rating dto.
   */
  Collection<GetRatingDto> getAllRating();

  /**
   * Get the Rating with the given id.
   *
   * @param id the id of the rating to read
   * @return the dto
   */
  UpdateRatingDto getRatingWithId(int id);

  /**
   * Create a new Rating in db.
   *
   * @param createRatingDto the rating to create
   */
  void createRating(CreateRatingDto createRatingDto);

  /**
   * Update an existing rating in db.
   *
   * @param id              the rating id
   * @param updateRatingDto the data to update
   */
  void updateRating(int id, UpdateRatingDto updateRatingDto);

  /**
   * Delete the rating identified by its id.
   *
   * @param id the rating id.
   */
  void deleteRating(int id);
}
