package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.GetRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation fof RatingService.
 * contains method used by Rating CRUD controller.
 */
@Service
public class RatingServiceImpl implements RatingService {

  private static final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

  private final RatingRepository ratingRepository;

  @Autowired
  public RatingServiceImpl(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  /**
   * Get all rating saved in db.
   *
   * @return collection of rating dto.
   */
  @Override
  public Collection<GetRatingDto> getAllRating() {

    log.trace("Getting all ratings.");

    Collection<GetRatingDto> ratings = new ArrayList<>();
    for (Rating rating : ratingRepository.findAll()) {
      GetRatingDto temp = new GetRatingDto();
      BeanUtils.copyProperties(rating, temp);

      ratings.add(temp);
    }

    log.info("Returning " + ratings.size() + " rating.");

    return ratings;
  }

  /**
   * Get the Rating with the given id.
   *
   * @param id the id of the rating to read
   * @return the dto
   */
  @Override
  public UpdateRatingDto getRatingWithId(int id) {
    log.trace("Getting rating, id: " + id);

    Optional<Rating> optional = ratingRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("Error can't find rating with id: " + id);
      throw new DataNotFoundException("Error can't find rating.");
    }

    UpdateRatingDto updateDto = new UpdateRatingDto();
    BeanUtils.copyProperties(optional.get(), updateDto);

    return updateDto;
  }

  /**
   * Create a new Rating in db.
   *
   * @param createRatingDto the rating to create
   */
  @Transactional
  @Override
  public void createRating(CreateRatingDto createRatingDto) {

    Rating entity = new Rating();
    BeanUtils.copyProperties(createRatingDto, entity);

    log.info("Saving rating: " + entity);

    ratingRepository.save(entity);

    log.info("Success - Rating saved with id: " + entity.getId());

  }

  /**
   * Update an existing rating in db.
   *
   * @param id              the rating id
   * @param updateRatingDto the data to update
   */
  @Transactional
  @Override
  public void updateRating(int id, UpdateRatingDto updateRatingDto) {

    log.trace("Updating rating with id: "
        + id
        + " and data: "
        + updateRatingDto);

    Optional<Rating> optional = ratingRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find rating with id: "
          + id);
      throw new DataNotFoundException("Error can't find rating");
    }

    Rating entity = optional.get();
    int count = 0;
    if (!updateRatingDto.getFitchRating().equals(entity.getFitchRating())) {
      log.trace("Updating FitchRating.");
      entity.setFitchRating(updateRatingDto.getFitchRating());
      count++;
    }
    if (!updateRatingDto.getSandPRating().equals(entity.getSandPRating())) {
      log.trace("Updating SandPRating.");
      entity.setSandPRating(updateRatingDto.getSandPRating());
      count++;
    }
    if (!updateRatingDto.getMoodysRating().equals(entity.getMoodysRating())) {
      log.trace("Updating MoodysRating.");
      entity.setMoodysRating(updateRatingDto.getMoodysRating());
      count++;
    }
    if (updateRatingDto.getOrderNumber() != entity.getOrderNumber()) {
      log.trace("Updating OderNumber.");
      entity.setOrderNumber(updateRatingDto.getOrderNumber());
      count++;
    }
    log.info("Updating Rating: " + entity);

    ratingRepository.save(entity);

    log.info("Update - OK for rating id: "
        + id
        + ". "
        + count
        + " field changed.");

  }

  /**
   * Delete the rating identified by its id.
   *
   * @param id the rating id.
   */
  @Transactional
  @Override
  public void deleteRating(int id) {

    log.info("Deleting Rating with id: " + id);

    if (ratingRepository.findById(id).isEmpty()) {
      log.warn("KO - Can't find Rating with id: " + id);
      throw new DataNotFoundException("Error - Can't find Rating.");
    }

    ratingRepository.deleteById(id);

    log.info("Delete - OK");

  }
}
