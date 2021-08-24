package com.nnk.springboot.integrationTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.CreateRuleNameDto;
import com.nnk.springboot.dto.UpdateRuleNameDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RuleNameNameIT {

  @Autowired
  RuleNameRepository ruleNameRepository;
  @Autowired
  MockMvc mockMvc;

  private final String homeUrl = "/ruleName/list";
  private final String createFormUrl = "/ruleName/add";
  private final String createUrl = "/ruleName/add";
  private final String updateFormUrl = "/ruleName/update/{id}";
  private final String updateUrl = "/ruleName/update/{id}";
  private final String deleteUrl = "/ruleName/delete/{id}";

  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
        .andExpect(status().isOk())
        .andExpect(model().attribute("rules", iterableWithSize(3)))
        .andExpect(view().name("ruleName/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addRuleNameForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("USER"))))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));


  }


  @Test
  @Transactional
  public void validate() throws Exception {

    CreateRuleNameDto valid = new CreateRuleNameDto();
    valid.setName("nameTest");
    valid.setDescription("DescriptionTest");
    valid.setJson("JsonTest");
    valid.setTemplate("templateTest");
    valid.setSqlPart("SqlPartTest");
    valid.setSqlStr("SqlStringTest");
    CreateRuleNameDto invalid = new CreateRuleNameDto();
    invalid.setName("nameTest");
    invalid.setDescription("DescriptionTest");
    invalid.setJson("JsonTest");
    invalid.setTemplate("templateTest");
    invalid.setSqlPart("");
    invalid.setSqlStr("SqlStringTest");
    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createRuleNameDto", "sqlPart"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("USER"))))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

    mockMvc
        .perform(
            get(updateFormUrl, 999)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("USER"))))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateRuleName() throws Exception {

    UpdateRuleNameDto valid = new UpdateRuleNameDto();
    valid.setId(5);
    valid.setName("Name test");
    valid.setDescription("Descrioptyop test");
    valid.setTemplate("Template test");
    valid.setJson("Json test");
    valid.setSqlPart("Sql Part test");
    valid.setSqlStr("Sql String Test");
    String urlEncoded = getUrlEncoded(valid);

    UpdateRuleNameDto invalid = new UpdateRuleNameDto();
    invalid.setId(5);
    invalid.setName("Name test");
    invalid.setDescription("Descrioptyop test");
    invalid.setTemplate("");
    invalid.setJson("Json test");
    invalid.setSqlPart("Sql Part test");
    invalid.setSqlStr("Sql String Test");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/ruleName/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateRuleNameDto", "template"));
  }

  @Test
  @Transactional
  public void deleteRuleName() throws Exception {

    Optional<RuleName> ruleWithId1 = ruleNameRepository.findById(1);
    assertTrue(ruleWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(ruleNameRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }
}
