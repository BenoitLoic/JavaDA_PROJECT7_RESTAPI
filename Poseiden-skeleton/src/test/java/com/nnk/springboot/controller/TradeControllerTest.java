package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.dto.CreateTradeDto;
import com.nnk.springboot.dto.GetTradeDto;
import com.nnk.springboot.dto.UpdateTradeDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.TradeServiceImpl;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
class TradeControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  TradeServiceImpl tradeServiceMock;
  @MockBean
  private UserDetailsServiceImpl userDetailsService;


  private final static String homeUrl = "/trade/list";
  private final static String createFormUrl = "/trade/add";
  private final static String createUrl = "/trade/add";
  private final static String updateFormUrl = "/trade/update/{id}";
  private final static String updateUrl = "/trade/update/{id}";
  private final static String deleteUrl = "/trade/delete/{id}";

  @Test
  void homeValid() throws Exception {

    // GIVEN
    // GetTradeDto(int tradeId, String account, String type, Double buyQuantity)
    GetTradeDto trade1 = new GetTradeDto();
    trade1.setTradeId(1);
    trade1.setAccount("account");
    trade1.setType("type");
    trade1.setBuyQuantity(1.5);
    GetTradeDto trade2 = new GetTradeDto();
    trade2.setTradeId(2);
    trade2.setAccount("account2");
    trade2.setType("type2");
    trade2.setBuyQuantity(2.5);
    List<GetTradeDto> trades = new ArrayList<>();
    trades.add(trade1);
    trades.add(trade2);
    // WHEN
    when(tradeServiceMock.findAllTrades()).thenReturn(trades);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/list"))
        .andExpect(model()
            .attribute("trades", containsInAnyOrder(trade1, trade2)));
  }

  @Test
  void homeWhenNoTrade_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(tradeServiceMock.findAllTrades()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/list"))
        .andExpect(model()
            .attribute("trades", new ArrayList<>()));
  }

  @Test
  void addTradeForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("trade/add"));
  }

  @Test
  void validateValid() throws Exception {

    // GIVEN
    CreateTradeDto temp = new CreateTradeDto();
    temp.setAccount("account");
    temp.setType("type");
    temp.setBuyQuantity(204.54);
    String urlEncoded = getUrlEncoded(temp);

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
    CreateTradeDto temp = new CreateTradeDto();
    temp.setAccount("account");
    temp.setType("");
    String urlEncoded = getUrlEncoded(temp);

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createTradeDto", "type"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateTradeDto updateTradeDto = new UpdateTradeDto();
    updateTradeDto.setAccount("account");
    updateTradeDto.setType("type");
    updateTradeDto.setTradeId(5);
    // WHEN
    when(tradeServiceMock.getTradeWithId(5)).thenReturn(updateTradeDto);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("trade/update"))
        .andExpect(model().attribute("updateTradeDto", updateTradeDto))
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
  void updateTradeValid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .param("account", "account")
                .param("type", "type")
                .param("tradeDate", "2021-08-22T14:42")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(homeUrl));
  }

  @Test
  void updateTradeInvalid() throws Exception {

    // GIVEN
    UpdateTradeDto updateTradeDto = new UpdateTradeDto();
    updateTradeDto.setAccount("account");
    updateTradeDto.setType("");
    updateTradeDto.setBuyQuantity(0d);


    String urlEncoded = getUrlEncoded(updateTradeDto);

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded.replace("&tradeDate=null,", ""))
                .param("tradeDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateTradeDto", "type"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateTradeWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    UpdateTradeDto updateTradeDto = new UpdateTradeDto();
    updateTradeDto.setAccount("account");
    updateTradeDto.setType("type");
    updateTradeDto.setBuyQuantity(0d);
    String urlEncoded = getUrlEncoded(updateTradeDto);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(tradeServiceMock).updateTrade(Mockito.anyInt(), Mockito.any(UpdateTradeDto.class));
    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded.replace("&tradeDate=null,", ""))
                .param("tradeDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void deleteTrade() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteTradeInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteTradeWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(tradeServiceMock).deleteTrade(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }

}