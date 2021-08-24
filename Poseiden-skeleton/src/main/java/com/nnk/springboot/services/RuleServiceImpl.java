package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.GetRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementation for RuleService.
 * contains method used by RuleName CRUD controller.
 */
@Service
public class RuleServiceImpl implements RuleService {

  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  RuleNameRepository ruleNameRepository;

  /**
   * Get all rules saved in DB and return their Dto.
   *
   * @return the collection of rule dto
   */
  @Override
  public Collection<GetRuleNameDto> findAllRules() {

    log.trace("Getting all rules.");

    Collection<GetRuleNameDto> rules = new ArrayList<>();
    for (RuleName ruleName : ruleNameRepository.findAll()) {
      GetRuleNameDto temp = new GetRuleNameDto();
      BeanUtils.copyProperties(ruleName, temp);
      rules.add(temp);
    }

    log.info("Returning " + rules.size() + " rule.");

    return rules;

  }

  /**
   * Create a new rule in DB.
   *
   * @param createRuleNameDto the rule to save in DB.
   */
  @Transactional
  @Override
  public void createRule(CreateRuleNameDto createRuleNameDto) {

    RuleName entity = new RuleName();
    BeanUtils.copyProperties(createRuleNameDto, entity);

    log.info("Saving rule: " + entity);

    ruleNameRepository.save(entity);

    log.info("Success - RuleName saved with id: " + entity.getId());

  }

  /**
   * Get RuleName identified by the given id.
   *
   * @param id the id of the rule to read
   * @return the dto of the rule
   */
  @Override
  public UpdateRuleNameDto getRuleWithId(int id) {

    log.trace("Getting rule, id: " + id);

    Optional<RuleName> optional = ruleNameRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("Error can't find rule with id: " + id);
      throw new DataNotFoundException("Error can't find rule.");
    }

    UpdateRuleNameDto updateDto = new UpdateRuleNameDto();
    BeanUtils.copyProperties(optional.get(), updateDto);

    return updateDto;

  }

  /**
   * Update an existing RuleName in DB.
   *
   * @param id                the rule id
   * @param updateRuleNameDto the rule to update in DB.
   */
  @Transactional
  @Override
  public void updateRule(int id, UpdateRuleNameDto updateRuleNameDto) {

    log.trace("Updating rule with id: "
        + id
        + " and data: "
        + updateRuleNameDto);

    Optional<RuleName> optional = ruleNameRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find rule with id: "
          + id);
      throw new DataNotFoundException("Error can't find rule.");
    }

    RuleName entity = optional.get();
    int count = 0;
    if (!updateRuleNameDto.getName().equals(entity.getName())) {
      log.trace("Updating name.");
      entity.setName(updateRuleNameDto.getName());
      count++;
    }
    if (!updateRuleNameDto.getDescription().equals(entity.getDescription())) {
      log.trace("Updating description.");
      entity.setDescription(updateRuleNameDto.getDescription());
      count++;
    }
    if (!updateRuleNameDto.getJson().equals(entity.getJson())) {
      log.trace("Updating json.");
      entity.setJson(updateRuleNameDto.getJson());
      count++;
    }
    if (!updateRuleNameDto.getTemplate().equals(entity.getTemplate())) {
      log.trace("Updating template.");
      entity.setTemplate(updateRuleNameDto.getTemplate());
      count++;
    }
    if (!updateRuleNameDto.getSqlStr().equals(entity.getSqlStr())) {
      log.trace("Updating sqlStr.");
      entity.setSqlStr(updateRuleNameDto.getSqlStr());
      count++;
    }
    if (!updateRuleNameDto.getSqlPart().equals(entity.getSqlPart())) {
      log.trace("Updating sqlPart.");
      entity.setSqlPart(updateRuleNameDto.getSqlPart());
      count++;
    }

    log.info("Updating RuleName: " + entity);

    ruleNameRepository.save(entity);

    log.info("Update - OK for trade id: "
        +entity.getId()
        +". "
        + count
        +" fields changed.");

  }

  /**
   * Delete an existing rule in DB with its id.
   *
   * @param id the id of the rule to delete
   */
  @Transactional
  @Override
  public void deleteRule(int id) {

    log.info("Deleting RuleName with id: " + id);

    if (ruleNameRepository.findById(id).isEmpty()) {
      log.warn("KO - Can't find RuleName with id: " + id);
      throw new DataNotFoundException("Error - Can't find RuleName.");
    }

    ruleNameRepository.deleteById(id);

    log.info("Delete - OK");

  }
}
