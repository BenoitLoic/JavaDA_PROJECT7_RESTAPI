package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.CreateRuleDto;
import com.nnk.springboot.dto.GetRuleDto;
import com.nnk.springboot.dto.UpdateRuleDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation for RuleService.
 * contains method used by Rule CRUD controller.
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
  public Collection<GetRuleDto> findAllRules() {

    log.trace("Getting all rules.");

    Collection<GetRuleDto> rules = new ArrayList<>();
    for (Rule rule : ruleNameRepository.findAll()) {
      GetRuleDto temp = new GetRuleDto(
          rule.getId(),
          rule.getName(),
          rule.getDescription());
      rules.add(temp);
    }

    log.info("Returning " + rules.size() + " rule.");

    return rules;

  }

  /**
   * Create a new rule in DB.
   *
   * @param createRuleDto the rule to save in DB.
   */
  @Transactional
  @Override
  public void createRule(CreateRuleDto createRuleDto) {

    Rule entity = new Rule();
    BeanUtils.copyProperties(createRuleDto, entity);

    log.info("Saving rule: " + entity);

    ruleNameRepository.save(entity);

    log.info("Success - Rule saved with id: " + entity.getId());

  }

  /**
   * Get Rule identified by the given id.
   *
   * @param id the id of the rule to read
   * @return the dto of the rule
   */
  @Override
  public UpdateRuleDto getRuleWithId(int id) {

    log.trace("Getting rule, id: " + id);

    Optional<Rule> optional = ruleNameRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("Error can't find rule with id: " + id);
      throw new NoSuchElementException("Error can't find rule.");
    }

    UpdateRuleDto updateDto = new UpdateRuleDto();
    BeanUtils.copyProperties(optional.get(), updateDto);

    return updateDto;

  }

  /**
   * Update an existing Rule in DB.
   *
   * @param id            the rule id
   * @param updateRuleDto the rule to update in DB.
   */
  @Transactional
  @Override
  public void updateRule(int id, UpdateRuleDto updateRuleDto) {

    log.trace("Updating rule with id: "
        + id
        + " and data: "
        + updateRuleDto);

    Optional<Rule> optional = ruleNameRepository.findById(id);

    if (optional.isEmpty()) {
      log.warn("KO - Error can't find rule with id: "
          + id);
      throw new NoSuchElementException("Error can't find rule.");
    }

    Rule entity = optional.get();

    if (!updateRuleDto.getName().isBlank()) {
      log.trace("Updating name.");
      entity.setName(updateRuleDto.getName());
    }
    if (!updateRuleDto.getDescription().isBlank()) {
      log.trace("Updating description.");
      entity.setDescription(updateRuleDto.getDescription());
    }
    if (!updateRuleDto.getJson().isBlank()) {
      log.trace("Updating json.");
      entity.setJson(updateRuleDto.getJson());
    }
    if (!updateRuleDto.getTemplate().isBlank()) {
      log.trace("Updating template.");
      entity.setTemplate(updateRuleDto.getTemplate());
    }
    if (!updateRuleDto.getSqlStr().isBlank()) {
      log.trace("Updating sqlStr.");
      entity.setSqlStr(updateRuleDto.getSqlStr());
    }
    if (!updateRuleDto.getSqlPart().isBlank()) {
      log.trace("Updating sqlPart.");
      entity.setSqlPart(updateRuleDto.getSqlPart());
    }

    log.info("Updating Rule: " + entity);

    ruleNameRepository.save(entity);

    log.info("Update - OK");

  }

  /**
   * Delete an existing rule in DB with its id.
   *
   * @param id the id of the rule to delete
   */
  @Transactional
  @Override
  public void deleteRule(int id) {

    log.info("Deleting Rule with id: " + id);

    if (ruleNameRepository.findById(id).isEmpty()) {
      log.warn("KO - Can't find Rule with id: " + id);
      throw new NoSuchElementException("Error - Can't find Rule.");
    }

    ruleNameRepository.deleteById(id);

    log.info("Delete - OK");

  }
}
