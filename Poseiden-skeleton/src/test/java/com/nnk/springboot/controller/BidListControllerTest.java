package com.nnk.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.GetBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.BidListServiceImpl;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BidListController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BidListControllerTest {


  @Autowired
  MockMvc mockMvc;
  @MockBean
  BidListServiceImpl bidListServiceMock;
  @MockBean
  private UserDetailsServiceImpl userDetailsService;

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private final static String homeUrl = "/bidList/list";
  private final static String createFormUrl = "/bidList/add";
  private final static String createUrl = "/bidList/add";
  private final static String updateFormUrl = "/bidList/update/{id}";
  private final static String updateUrl = "/bidList/update/{id}";
  private final static String deleteUrl = "/bidList/delete/{id}";


  @Test
  void homeValid() throws Exception {

    // GIVEN
    GetBidListDto bid1 = new GetBidListDto(1, "account", "type", 2.3);
    GetBidListDto bid2 = new GetBidListDto(2, "account", "type", 2.3);
    List<GetBidListDto> bids = new ArrayList<>();
    bids.add(bid1);
    bids.add(bid2);
    // WHEN
    when(bidListServiceMock.findAllBids()).thenReturn(bids);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/list"))
        .andExpect(model()
            .attribute("bidList", containsInAnyOrder(bid1, bid2)));
  }

  @Test
  void homeWhenNoBid_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(bidListServiceMock.findAllBids()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/list"))
        .andExpect(model()
            .attribute("bidList", new ArrayList<>()));
  }

  @Test
  void addBidForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("bidList/add"));
  }

  @Test
  void validateValid() throws Exception {

    // GIVEN
    CreateBidListDto temp = new CreateBidListDto();
    temp.setAccount("account");
    temp.setType("type");
    temp.setBidQuantity(204.54);
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
    CreateBidListDto temp = new CreateBidListDto();
    temp.setAccount("");
    temp.setType("type");
    temp.setBidQuantity(204.54);
    String urlEncoded = getUrlEncoded(temp);

    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content(urlEncoded))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createBidListDto", "account"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateBidListDto updateBid = new UpdateBidListDto();
    updateBid.setBidListId(5);
    updateBid.setAccount("account");
    updateBid.setType("type");
    updateBid.setBidQuantity(10d);
    // WHEN
    when(bidListServiceMock.getBidWithId(5)).thenReturn(updateBid);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("bidList/update"))
        .andExpect(model().attribute("updateBidListDto", updateBid))
        .andExpect(status().isOk());

  }

  @Test
  void showUpdateFormInvalid() throws Exception {

// GIVEN
    UpdateBidListDto updateBid = new UpdateBidListDto();
    updateBid.setBidListId(5);
    updateBid.setAccount("account");
    updateBid.setType("type");
    updateBid.setBidQuantity(10d);
    // WHEN
    when(bidListServiceMock.getBidWithId(5)).thenReturn(updateBid);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void updateBidValid() throws Exception {

    // GIVEN
    UpdateBidListDto updateBid = new UpdateBidListDto();
    updateBid.setBidListId(5);
    updateBid.setAccount("account");
    updateBid.setType("type");
    updateBid.setBidQuantity(10d);
    String urlEncoded = getUrlEncoded(updateBid);
    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateFormUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(homeUrl));
  }

  @Test
  void updateBidInvalid() throws Exception {

    // GIVEN
    UpdateBidListDto updateBid = new UpdateBidListDto();
    updateBid.setBidListId(5);
    updateBid.setAccount("");
    updateBid.setType("type");
    updateBid.setBidQuantity(10d);
    String urlEncoded = getUrlEncoded(updateBid);
    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateFormUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateBidListDto", "account"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateBidWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    UpdateBidListDto updateBid = new UpdateBidListDto();
    updateBid.setBidListId(5);
    updateBid.setAccount("account");
    updateBid.setType("type");
    updateBid.setBidQuantity(10d);
    String urlEncoded = getUrlEncoded(updateBid);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(bidListServiceMock).updateBid(Mockito.anyInt(), Mockito.any(UpdateBidListDto.class));    // THEN
    mockMvc
        .perform(
            put(updateFormUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteBid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteBidInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, "")).andExpect(status().isNotFound());

  }

  @Test
  void deleteBidWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(bidListServiceMock).deleteBid(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound());

  }


}
