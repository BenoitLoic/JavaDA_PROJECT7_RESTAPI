package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateRuleDto;
import com.nnk.springboot.dto.GetRuleDto;
import com.nnk.springboot.dto.UpdateRuleDto;
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
   * Get all Rule.
   *
   * @param model the model to inject data for the view.
   * @return the list of all rules.
   */
  @GetMapping("/list")
  public String home(Model model) {
    Collection<GetRuleDto> rules = ruleService.findAllRules();
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
   * @param rule   the rating to save
   * @param result binding to check if there are errors in parameters
   * @return the list of all rules.
   */
  @PostMapping("/validate")
  public String validate(@Valid CreateRuleDto rule, BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rule: "
          + rule
          + " with error : "
          + result.getFieldErrors());
      return "ruleName/add";
    }

    ruleService.createRule(rule);

    return "redirect:/ruleName/list";
  }

  /**
   * Get Rule by Id, add to model then show to the form to update.
   *
   * @param id    the id of the rule to update
   * @param model the model to add data
   * @return the html form for rule update
   */
  @GetMapping("/update/{id}")
  public String updateForm(@PathVariable("id") int id, Model model) {

    log.info("Getting rule with id: " + id);

    UpdateRuleDto rule = ruleService.getRuleWithId(id);

    model.addAttribute("rule", rule);

    return "ruleName/update";
  }

  /**
   * Update a Rule with its id.
   * validate data, update data in DB and return all rating.
   *
   * @param id     the id of the rule to update
   * @param rule   the data to update
   * @param result the field error in parameters
   * @param model  the model
   * @return the list of all rules
   */
  @PutMapping("/update/{id}")
  public String updateRule(@PathVariable("id") int id, @Valid UpdateRuleDto rule,
                           BindingResult result, Model model) {

    if (result.hasErrors()) {
      log.warn("KO - Error in validation for rule: "
          + rule
          + " with error : "
          + result.getFieldErrors());
      return "ruleName/update";
    }

    ruleService.updateRule(id, rule);

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
