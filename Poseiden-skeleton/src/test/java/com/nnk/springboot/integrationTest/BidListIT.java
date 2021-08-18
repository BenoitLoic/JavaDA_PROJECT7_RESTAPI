package com.nnk.springboot.integrationTest;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.CreateBidListDto;
import com.nnk.springboot.dto.UpdateBidListDto;
import com.nnk.springboot.exceptions.BadArgumentException;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.nnk.springboot.utility.FormatToUrlEncoded.getUrlEncoded;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListIT {

  @Autowired
  BidListRepository bidListRepository;
  @Autowired
  MockMvc mockMvc;


  private final String homeUrl = "/bidList/list";
  private final String createFormUrl = "/bidList/add";
  private final String createUrl = "/bidList/validate";
  private final String updateFormUrl = "/bidList/update/{id}";
  private final String updateUrl = "/bidList/update/{id}";
  private final String deleteUrl = "/bidList/delete/{id}";

  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
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
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("USER"))))
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

    CreateBidListDto valid = new CreateBidListDto("account", "type", 22.4);
    CreateBidListDto invalid = new CreateBidListDto();
    invalid.setAccount("account");
    invalid.setType("");

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
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("ADMIN"))))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .authorities(new SimpleGrantedAuthority("USER"))))
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
                    .authorities(new SimpleGrantedAuthority("USER"))))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateBid() throws Exception {

    UpdateBidListDto valid = new UpdateBidListDto(0, "account", "type", 22.4);
    UpdateBidListDto invalid = new UpdateBidListDto();
    invalid.setAccount("account");
    invalid.setType("");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/bidList/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  @Transactional
  public void deleteBid() throws Exception {

    Optional<BidList> bidWithId1 = bidListRepository.findById(1);
    assertTrue(bidWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(bidListRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest").authorities(new SimpleGrantedAuthority("USER")))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }


}
