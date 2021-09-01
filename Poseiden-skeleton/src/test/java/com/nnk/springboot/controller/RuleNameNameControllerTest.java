package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.GetRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.RuleServiceImpl;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
class RuleNameNameControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  RuleServiceImpl ruleServiceMock;
  @MockBean private UserDetailsServiceImpl userDetailsService;


  private final String homeUrl = "/ruleName/list";
  private final String createFormUrl = "/ruleName/add";
  private final String createUrl = "/ruleName/add";
  private final String updateFormUrl = "/ruleName/update/{id}";
  private final String updateUrl = "/ruleName/update/{id}";
  private final String deleteUrl = "/ruleName/delete/{id}";

  @Test
  void homeValid() throws Exception {

    // GIVEN
    // GetTradeDto(int tradeId, String account, String type, Double buyQuantity)
    GetRuleNameDto rule1 = new GetRuleNameDto();
    rule1.setName("nameTest");
    rule1.setDescription("DescriptionTest");
    rule1.setJson("JsonTest");
    rule1.setTemplate("templateTest");
    rule1.setSqlPart("SqlPartTest");
    rule1.setSqlStr("SqlStringTest");

    GetRuleNameDto rule2 = new GetRuleNameDto();
    rule2.setName("nameTest2");
    rule2.setDescription("DescriptionTest2");
    rule2.setJson("JsonTest2");
    rule2.setTemplate("templateTest2");
    rule2.setSqlPart("SqlPartTest2");
    rule2.setSqlStr("SqlStringTest2");

    List<GetRuleNameDto> rules = new ArrayList<>();
    rules.add(rule1);
    rules.add(rule2);
    // WHEN
    when(ruleServiceMock.findAllRules()).thenReturn(rules);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/list"))
        .andExpect(model()
            .attribute("rules", containsInAnyOrder(rule1, rule2)));
  }

  @Test
  void homeWhenNoRuleName_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(ruleServiceMock.findAllRules()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/list"))
        .andExpect(model()
            .attribute("rules", new ArrayList<>()));
  }

  @Test
  void addRuleNameForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("ruleName/add"));
  }

  @Test
  void validateValid() throws Exception {

    // GIVEN
    CreateRuleNameDto createRuleNameDto = new CreateRuleNameDto();
    createRuleNameDto.setName("nameTest");
    createRuleNameDto.setDescription("DescriptionTest");
    createRuleNameDto.setJson("JsonTest");
    createRuleNameDto.setTemplate("templateTest");
    createRuleNameDto.setSqlPart("SqlPartTest");
    createRuleNameDto.setSqlStr("SqlStringTest");
    String urlEncoded = getUrlEncoded(createRuleNameDto);

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));
  }


  @Test
  void validateInvalid() throws Exception {

    // GIVEN
    CreateRuleNameDto createRuleNameDto = new CreateRuleNameDto();
    createRuleNameDto.setName("nameTest");
    createRuleNameDto.setDescription("DescriptionTest");
    createRuleNameDto.setJson("");
    createRuleNameDto.setTemplate("templateTest");
    createRuleNameDto.setSqlPart("SqlPartTest");
    createRuleNameDto.setSqlStr("SqlStringTest");

    String urlEncoded = getUrlEncoded(createRuleNameDto);

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createRuleNameDto", "json"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setId(5);
    updateRuleNameDto.setName("Name test");
    updateRuleNameDto.setDescription("Descrioptyop test");
    updateRuleNameDto.setTemplate("Template test");
    updateRuleNameDto.setJson("Json test");
    updateRuleNameDto.setSqlPart("Sql Part test");
    updateRuleNameDto.setSqlStr("Sql String Test");

    // WHEN
    when(ruleServiceMock.getRuleWithId(5)).thenReturn(updateRuleNameDto);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("ruleName/update"))
        .andExpect(model().attribute("updateRuleNameDto", updateRuleNameDto))
        .andExpect(status().isOk());

  }


  @Test
  void showUpdateFormInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, ""))
        .andExpect(status().isNotFound());

  }


  @Test
  void updateRuleNameValid() throws Exception {

    // GIVEN
    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setId(5);
    updateRuleNameDto.setName("Name test");
    updateRuleNameDto.setDescription("Descrioptyop test");
    updateRuleNameDto.setTemplate("Template test");
    updateRuleNameDto.setJson("Json test");
    updateRuleNameDto.setSqlPart("Sql Part test");
    updateRuleNameDto.setSqlStr("Sql String Test");
    String urlEncoded = getUrlEncoded(updateRuleNameDto);

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(homeUrl));
  }

  @Test
  void updateRuleNameInvalid() throws Exception {

    // GIVEN
    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setId(1);
    updateRuleNameDto.setName("Name test");
    updateRuleNameDto.setDescription("Descrioptyop test");
    updateRuleNameDto.setTemplate("");
    updateRuleNameDto.setJson("Json test");
    updateRuleNameDto.setSqlPart("Sql Part test");
    updateRuleNameDto.setSqlStr("Sql String Test");

    String urlEncoded = getUrlEncoded(updateRuleNameDto);

    System.out.println(urlEncoded);

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateRuleNameDto", "template"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateRuleNameWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    UpdateRuleNameDto updateRuleNameDto = new UpdateRuleNameDto();
    updateRuleNameDto.setId(5);
    updateRuleNameDto.setName("Name test");
    updateRuleNameDto.setDescription("Descrioptyop test");
    updateRuleNameDto.setTemplate("Template test");
    updateRuleNameDto.setJson("Json test");
    updateRuleNameDto.setSqlPart("Sql Part test");
    updateRuleNameDto.setSqlStr("Sql String Test");

    String urlEncoded = getUrlEncoded(updateRuleNameDto);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(ruleServiceMock).updateRule(Mockito.anyInt(), Mockito.any(UpdateRuleNameDto.class));
    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void deleteRuleName() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteRuleNameInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteRuleNameWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(ruleServiceMock).deleteRule(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }

}