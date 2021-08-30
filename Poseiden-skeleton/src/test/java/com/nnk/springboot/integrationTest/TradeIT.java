package com.nnk.springboot.integrationTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
public class TradeIT {

  @Autowired
  TradeRepository tradeRepository;
  @Autowired
  MockMvc mockMvc;


  private final String homeUrl = "/trade/list";
  private final String createFormUrl = "/trade/add";
  private final String createUrl = "/trade/add";
  private final String updateFormUrl = "/trade/update/{id}";
  private final String updateUrl = "/trade/update/{id}";
  private final String deleteUrl = "/trade/delete/{id}";

  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(model().attribute("trades", iterableWithSize(3)))
        .andExpect(view().name("trade/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addTradeForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/add"));

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

    CreateTradeDto valid = new CreateTradeDto();
    valid.setAccount("account");
    valid.setType("type");
    CreateTradeDto invalid = new CreateTradeDto();
    invalid.setAccount("account");
    invalid.setType("");

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createTradeDto", "type"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/update"));

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
                    .roles("USER")))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateTrade() throws Exception {

    UpdateTradeDto valid = new UpdateTradeDto();
    valid.setAccount("account");
    valid.setType("type");
    String urlEncoded = getUrlEncoded(valid);
    urlEncoded = urlEncoded.replace("&tradeDate=null", "");
    UpdateTradeDto invalid = new UpdateTradeDto();
    invalid.setAccount("account");
    invalid.setType("");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .content(urlEncoded)
                .param("tradeDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/trade/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateTradeDto", "type"));
  }

  @Test
  @Transactional
  public void deleteTrade() throws Exception {

    Optional<Trade> tradeWithId1 = tradeRepository.findById(1);
    assertTrue(tradeWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(tradeRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }
}
