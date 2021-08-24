package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {

  @Mock
  RuleNameRepository ruleNameRepositoryMock;
  @InjectMocks
  RuleServiceImpl ruleService;

  @Test
  void findAllRuleName() {

    // GIVEN
    List<RuleName> ruleNames = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      RuleName temp = new RuleName();
      temp.setId(i);
      temp.setName("name" + i);
      ruleNames.add(temp);
    }
    // WHEN
    when(ruleNameRepositoryMock.findAll()).thenReturn(ruleNames);
    // THEN
    assertEquals(3, ruleService.findAllRules().size());

  }

  @Test
  void findAllRuleNameEmptyList() {

    // GIVEN

    // WHEN
    when(ruleNameRepositoryMock.findAll()).thenReturn(new ArrayList<>());
    // THEN
    assertEquals(0, ruleService.findAllRules().size());

  }

  @Test
  void createRuleName() {
    // GIVEN
    CreateRuleNameDto createRuleNameDto = new CreateRuleNameDto();
    createRuleNameDto.setTemplate("template");
    createRuleNameDto.setJson("json");
    RuleName expected = new RuleName();
    expected.setTemplate("template");
    expected.setJson("json");
    // WHEN
    ruleService.createRule(createRuleNameDto);
    // THEN
    verify(ruleNameRepositoryMock, times(1)).save(expected);

  }


  @Test
  void getRuleNameWithIdValid() {
    // GIVEN
    RuleName ruleName = new RuleName();
    ruleName.setId(5);
    ruleName.setName("nameTest");
    // WHEN
    when(ruleNameRepositoryMock.findById(5)).thenReturn(Optional.of(ruleName));
    UpdateRuleNameDto actual = ruleService.getRuleWithId(5);
    // THEN
    assertEquals(5, actual.getId());
    assertEquals("nameTest", actual.getName());

  }

  @Test
  void getRuleNameWithIdWhenDataNotFind_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ruleNameRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

    // THEN
    assertThrows(DataNotFoundException.class, () -> ruleService.getRuleWithId(1));

  }

  @Test
  void updateRuleNameValid() {
    // GIVEN

    RuleName ruleName = new RuleName();
    ruleName.setId(5);
    ruleName.setName("nameTest");
    ruleName.setTemplate("TemplateTest");
    ruleName.setDescription("descriptionTest");
    ruleName.setJson("jsonTest");
    ruleName.setSqlPart("sqlPartTest");
    ruleName.setSqlStr("sqlStrTest");


    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setName("update");
    updateRuleNameDto.setTemplate("TemplateTest");
    updateRuleNameDto.setDescription("descriptionTest");
    updateRuleNameDto.setJson("jsonTest");
    updateRuleNameDto.setSqlPart("sqlPartTest");
    updateRuleNameDto.setSqlStr("sqlStrTest");

    RuleName expected = new RuleName();
    expected.setId(5);
    expected.setName("update");
    expected.setTemplate("TemplateTest");
    expected.setDescription("descriptionTest");
    expected.setJson("jsonTest");
    expected.setSqlPart("sqlPartTest");
    expected.setSqlStr("sqlStrTest");
    // WHEN
    Mockito.when(ruleNameRepositoryMock.findById(anyInt())).thenReturn(Optional.of(ruleName));
    ruleService.updateRule(1, updateRuleNameDto);

    // THEN

    verify(ruleNameRepositoryMock).save(expected);


  }

  @Test
  void updateRuleNameWithNoChange() {
    // GIVEN

    RuleName ruleName = new RuleName();
    ruleName.setId(5);
    ruleName.setName("nameTest");
    ruleName.setTemplate("TemplateTest");
    ruleName.setDescription("descriptionTest");
    ruleName.setJson("jsonTest");
    ruleName.setSqlPart("sqlPartTest");
    ruleName.setSqlStr("sqlStrTest");


    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setName("nameTest");
    updateRuleNameDto.setTemplate("TemplateTest");
    updateRuleNameDto.setDescription("descriptionTest");
    updateRuleNameDto.setJson("jsonTest");
    updateRuleNameDto.setSqlPart("sqlPartTest");
    updateRuleNameDto.setSqlStr("sqlStrTest");
    // WHEN
    when(ruleNameRepositoryMock.findById(anyInt())).thenReturn(Optional.of(ruleName));
    ruleService.updateRule(5, updateRuleNameDto);
    // THEN
    verify(ruleNameRepositoryMock, times(1)).save(ruleName);

  }

  @Test
  void updateRuleNameWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ruleNameRepositoryMock.findById(2)).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> ruleService.updateRule(2, any()));
  }

  @Test
  void deleteRuleNameValid() {
    // GIVEN

    // WHEN
    when(ruleNameRepositoryMock.findById(2)).thenReturn(Optional.of(new RuleName()));
    ruleService.deleteRule(2);
    // THEN
    verify(ruleNameRepositoryMock, times(1)).deleteById(2);

  }

  @Test
  void deleteRuleNameWhenDataNotFound_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(ruleNameRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> ruleService.deleteRule(1));

  }


}