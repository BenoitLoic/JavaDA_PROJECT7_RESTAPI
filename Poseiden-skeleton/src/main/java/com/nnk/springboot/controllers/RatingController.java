package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateRatingDto;
import com.nnk.springboot.dto.GetRatingDto;
import com.nnk.springboot.dto.UpdateRatingDto;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Contains CRUD method for Rating feature.
 */
@Controller
public class RatingController {

  private final Logger log = LogManager.getLogger(getClass().getName());
  @Autowired
  RatingService ratingService;

  /**
   * Get all Rating.
   *
   * @param model the model to inject data for the view.
   * @return the list of all rating.
   */
  @RequestMapping("/rating/list")
  public String home(Model model) {

    Collection<GetRatingDto> ratings = ratingService.getAllRating();
    model.addAttribute("ratings", ratings);

    return "rating/list";
  }

  /**
   * Return the form to create a new rating.
   *
   * @return the html form
   */
  @GetMapping("/rating/add")
  public String addRatingForm() {
    return "rating/add";
  }

  /**
   * Create a new rating : validate data and save it to db.
   *
   * @param rating the rating to save
   * @param result binding to check if there are errors in parameters
   * @return the list of all rating.
   */
  @PostMapping("/rating/validate")
  public String validate(@Valid CreateRatingDto rating, BindingResult result) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rating: "
          + rating
          + " with error : "
          + result.getFieldErrors());
      return "rating/add";
    }

    ratingService.createRating(rating);

    return "redirect:/rating/list";

  }

  /**
   * Get Rating by Id, add to model then show to the form to update.
   *
   * @param id    the id of the rating to update
   * @param model the model to add data
   * @return the html form for rating update
   */
  @GetMapping("/rating/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

    log.info("Getting rating with id: " + id);

    UpdateRatingDto rating = ratingService.getRatingWithId(id);

    model.addAttribute("rating", rating);

    return "rating/update";

  }


  /**
   * Update a Rating with its id.
   * validate data, update data in DB and return all rating.
   *
   * @param id     the id of the rating to update
   * @param rating the data to update
   * @param result the field error in parameters
   * @param model  the model
   * @return the list of all rating
   */
  @PutMapping("/rating/update/{id}")
  public String updateRating(@PathVariable("id") Integer id, @Valid UpdateRatingDto rating,
                             BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rating: "
          + rating
          + " with error : "
          + result.getFieldErrors());
      return "rating/update";
    }

    ratingService.updateRating(id, rating);

    return "redirect:/rating/list";
  }

  /**
   * Delete the rating with the given id.
   *
   * @param id    the id of the rating to delete
   * @param model th model
   * @return the list of all rating
   */
  @DeleteMapping("/rating/delete/{id}")
  public String deleteRating(@PathVariable("id") Integer id, Model model) {

    ratingService.deleteRating(id);

    return "redirect:/rating/list";

  }
}
