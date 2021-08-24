package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.GetRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;

import java.util.Collection;

/**
 * Interface for RuleService.
 * Contains method used by CRUD controller.
 */
public interface RuleService {

  /**
   * Get all rules saved in DB and return their Dto.
   * @return the collection of rule dto
   */
  Collection<GetRuleNameDto> findAllRules();

  /**
   * Create a new rule in DB.
   * @param createRuleNameDto the rule to save in DB.
   */
  void createRule(CreateRuleNameDto createRuleNameDto);

  /**
   * Get RuleName identified by the given id.
   * @param id the id of the rule to read
   * @return the dto of the rule
   */
  UpdateRuleNameDto getRuleWithId(int id);

  /**
   * Update an existing RuleName in DB.
   * @param id the id of the rule to update
   * @param updateRuleNameDto the rule to update in DB.
   */
  void updateRule(int id, UpdateRuleNameDto updateRuleNameDto);

  /**
   * Delete an existing rule in DB with its id.
   * @param id the id of the rule to delete
   */
  void deleteRule(int id);
}
