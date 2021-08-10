package com.nnk.springboot.services;

import com.nnk.springboot.dto.CreateRuleDto;
import com.nnk.springboot.dto.GetRuleDto;
import com.nnk.springboot.dto.UpdateRuleDto;

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
  Collection<GetRuleDto> findAllRules();

  /**
   * Create a new rule in DB.
   * @param createRuleDto the rule to save in DB.
   */
  void createRule(CreateRuleDto createRuleDto);

  /**
   * Get Rule identified by the given id.
   * @param id the id of the rule to read
   * @return the dto of the rule
   */
  UpdateRuleDto getRuleWithId(int id);

  /**
   * Update an existing Rule in DB.
   * @param id the id of the rule to update
   * @param updateRuleDto the rule to update in DB.
   */
  void updateRule(int id,UpdateRuleDto updateRuleDto);

  /**
   * Delete an existing rule in DB with its id.
   * @param id the id of the rule to delete
   */
  void deleteRule(int id);
}
