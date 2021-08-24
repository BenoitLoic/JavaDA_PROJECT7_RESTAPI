package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.GetRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;
import com.nnk.springboot.services.RuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  RuleService ruleService;

  /**
   * Get all RuleName.
   *
   * @param model the model to inject data for the view.
   * @return the list of all rules.
   */
  @GetMapping("/list")
  public String home(Model model) {
    Collection<GetRuleNameDto> rules = ruleService.findAllRules();
    model.addAttribute("rules", rules);

    return "ruleName/list";
  }

  /**
   * Return the form to create a new rule.
   *
   * @return the html form
   */
  @GetMapping("/add")
  public String addForm() {
    return "ruleName/add";
  }

  /**
   * Create a new rule : validate data and save it to db.
   *
   * @param createRuleNameDto   the rule to save
   * @param result binding to check if there are errors in parameters
   * @return the list of all rules.
   */
  @PostMapping("/add")
  public String validate(@Valid CreateRuleNameDto createRuleNameDto, BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rule: "
          + createRuleNameDto
          + " with error : "
          + result.getFieldErrors());
      return "ruleName/add";
    }

    ruleService.createRule(createRuleNameDto);

    return "redirect:/ruleName/list";
  }

  /**
   * Get RuleName by Id, add to model then show to the form to update.
   *
   * @param id    the id of the rule to update
   * @param model the model to add data
   * @return the html form for rule update
   */
  @GetMapping("/update/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {

    log.info("Getting rule with id: " + id);

    UpdateRuleNameDto rule = ruleService.getRuleWithId(id);

    model.addAttribute("updateRuleNameDto", rule);

    return "ruleName/update";
  }

  /**
   * Update a RuleName with its id.
   * validate data, update data in DB and return all rating.
   *
   * @param id     the id of the rule to update
   * @param updateRuleNameDto   the data to update
   * @param result the field error in parameters
   * @param model  the model
   * @return the list of all rules
   */
  @PutMapping("/update/{id}")
  public String updateRule(@PathVariable("id") int id, @Valid UpdateRuleNameDto updateRuleNameDto,
                           BindingResult result, Model model) {

    log.info("Updating RuleName Name: "+id);

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rule: "
          + updateRuleNameDto
          + " with error : "
          + result.getFieldErrors());
      model.addAttribute("updateRuleNameDto", updateRuleNameDto);
      return "/ruleName/update";
    }

    ruleService.updateRule(id, updateRuleNameDto);

    return "redirect:/ruleName/list";
  }

  /**
   * Delete the rule with the given id.
   *
   * @param id    the id of the rule to delete
   * @param model th model
   * @return the list of all rules
   */
  @DeleteMapping("/delete/{id}")
  public String deleteRule(@PathVariable("id") int id, Model model) {

    ruleService.deleteRule(id);

    return "redirect:/ruleName/list";
  }
}
