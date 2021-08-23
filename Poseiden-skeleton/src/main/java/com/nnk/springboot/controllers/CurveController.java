package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateCurvePointDto;
import com.nnk.springboot.dto.GetCurvePointDto;
import com.nnk.springboot.dto.UpdateCurvePointDto;
import com.nnk.springboot.services.CurveService;
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
 * Contains CRUD method for Curve feature.
 */
@Controller
@RequestMapping("/curvePoint")
public class CurveController {

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  CurveService curveService;

  /**
   * Get all curvePoint in database.
   *
   * @param model the model to inject data for the view
   * @return the list of curve points
   */
  @GetMapping("/list")
  public String home(Model model) {

    Collection<GetCurvePointDto> curvePoints = curveService.getAllCurvePoint();
    model.addAttribute("curvePoints", curvePoints);

    return "curvePoint/list";
  }

  /**
   * Return the form to add a new point.
   *
   * @return html form.
   */
  @GetMapping("/add")
  public String addCurvePointForm(CreateCurvePointDto createCurvePointDto) {
    return "curvePoint/add";
  }

  /**
   * Create a new curvePoint.
   *
   * @param createCurvePointDto the point to create.
   * @param result     validation check for curvePoint
   * @param model      the model to inject data for the view
   * @return list of all points
   */
  @PostMapping("/add")
  public String createCurvePoint(@Valid CreateCurvePointDto createCurvePointDto, BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("Error in validation for CurvePoint: "
          + createCurvePointDto
          + " with error: "
          + result.getFieldError());
      return "curvePoint/add";
    }
log.info(createCurvePointDto.getAsOfDate());

    curveService.createCurvePoint(createCurvePointDto);

    return "redirect:/curvePoint/list";
  }

  /**
   * Return the form to update a curve point with curvePoint data prefilled.
   *
   * @param id    the id of the point to update
   * @param model the model to inject data for the view
   * @return the html form
   */
  @GetMapping("/update/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {
    log.info("Getting curve point with id: " + id);

    UpdateCurvePointDto point = curveService.getPointWithId(id);
    model.addAttribute("updateCurvePointDto", point);

    return "curvePoint/update";
  }

  /**
   * Update the curvePoint with its id.
   *
   * @param id         the id of the point to update
   * @param updateCurvePointDto the data to update
   * @param result     validation check
   * @param model      the model to inject data for the view
   * @return list of all points
   */
  @PutMapping("/update/{id}")
  public String updateCurvePoint(@PathVariable("id") int id, @Valid UpdateCurvePointDto updateCurvePointDto,
                                 BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for curvePoint: "
          + result.getFieldError());
      return "curvePoint/update";
    }
    System.out.println("post");
    curveService.updateCurvePoint(id, updateCurvePointDto);

    return "redirect:/curvePoint/list";
  }

  /**
   * Delete the curvePoint with the given id
   *
   * @param id    the id of the point to delete
   * @param model the model to inject data for the view
   * @return list of all points
   */
  @DeleteMapping("/delete/{id}")
  public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {

    curveService.deleteCurvePoint(id);

    return "redirect:/curvePoint/list";
  }
}
