package com.nnk.springboot.integrationTest;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
public class BidListIT {

  @Autowired
  BidListRepository bidListRepository;
  @Autowired
  MockMvc           mockMvc;


  private final static String homeUrl       = "/bidList/list";
  private final static String createFormUrl = "/bidList/add";
  private final static String createUrl     = "/bidList/add";
  private final static String updateFormUrl = "/bidList/update/{id}";
  private final static String updateUrl     = "/bidList/update/{id}";
  private final static String deleteUrl     = "/bidList/delete/{id}";

  @Test
  public void home() throws Exception {

    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(model().attribute("bidList", iterableWithSize(3)))
        .andExpect(view().name("bidList/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addBidForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/add"));

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

    CreateBidListDto valid = new CreateBidListDto();
    valid.setAccount("account");
    valid.setType("type");
    valid.setBidQuantity(22.4);
    CreateBidListDto invalid = new CreateBidListDto();
    invalid.setAccount("account");
    invalid.setType("");

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest").roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest").roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createBidListDto", "type"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("USER")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/update"));

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
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateBid() throws Exception {

    UpdateBidListDto valid = new UpdateBidListDto();
    valid.setBidListId(0);
    valid.setAccount("account");
    valid.setType("type");
    valid.setBidQuantity(22.4);
    UpdateBidListDto invalid = new UpdateBidListDto();
    invalid.setAccount("account");
    invalid.setType("");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/bidList/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateBidListDto", "type"));
  }

  @Test
  @Transactional
  public void deleteBid() throws Exception {

    Optional<BidList> bidWithId1 = bidListRepository.findById(1);
    assertTrue(bidWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(bidListRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest")
                    .roles("USER"))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }


}
