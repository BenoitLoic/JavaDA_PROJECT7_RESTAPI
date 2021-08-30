package com.nnk.springboot.integrationTest;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserIT {

  @Autowired
  UserRepository userRepository;
  @Autowired
  MockMvc mockMvc;

  private final String homeUrl = "/user/list";
  private final String createFormUrl = "/user/add";
  private final String createUrl = "/user/add";
  private final String updateFormUrl = "/user/update/{id}";
  private final String updateUrl = "/user/update/{id}";
  private final String deleteUrl = "/user/delete/{id}";

  @Test
  public void home() throws Exception {


    mockMvc
        .perform(
            get(homeUrl)
                .with(user("testAdmin")
                    .password("test")
                    .roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(model().attribute("users", iterableWithSize(3)))
        .andExpect(view().name("user/list"));

    mockMvc.perform(get(homeUrl).with(anonymous()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/login"));

  }

  @Test
  public void addUserForm() throws Exception {
    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("user/add"));

    mockMvc
        .perform(
            get(createFormUrl)
                .with(user("userTest")
                    .roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("user/add"));

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

    CreateUserDto valid = new CreateUserDto();
    valid.setUsername("UserNameTest");
    valid.setRole("ADMIN");
    valid.setFullname("fullNameTest");
    valid.setPassword("Pa5sTe$t");

    CreateUserDto invalid = new CreateUserDto();
    invalid.setUsername("UserNameTest");
    invalid.setRole("ADMIN");
    invalid.setFullname("fullNameTest");
    invalid.setPassword("PassTe$t");

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(valid)))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

    mockMvc
        .perform(
            post(createUrl)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("createUserDto", "password"));

  }

  @Test
  public void showUpdateForm() throws Exception {
    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("ADMIN")))
                .andExpect(status().isOk())
        .andExpect(view().name("user/update"));

    mockMvc
        .perform(
            get(updateFormUrl, 1)
                .with(user("userTest")
                    .roles("ADMIN")))
        .andExpect(status().isOk())
        .andExpect(view().name("user/update"));

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
                    .roles("ADMIN")))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));


  }

  @Test
  @Transactional
  public void updateUser() throws Exception {

    UpdateUserDto valid = new UpdateUserDto();
    valid.setUsername("UserNameTest");
    valid.setRole("ADMIN");
    valid.setFullname("fullNameTest");
    valid.setPassword("Pa5sTe$t");
    String urlEncoded = getUrlEncoded(valid);

    UpdateUserDto invalid = new UpdateUserDto();
    invalid.setUsername("UserNameTest");
    invalid.setRole("ADMIN");
    invalid.setFullname("fullNameTest");
    invalid.setPassword("PassTe$t");

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf())
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/user/list"));

    mockMvc
        .perform(
            put(updateUrl, 1)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(getUrlEncoded(invalid)))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateUserDto", "password"));
  }

  @Test
  @Transactional
  public void deleteUser() throws Exception {

    Optional<User> userWithId1 = userRepository.findById(1);
    assertTrue(userWithId1.isPresent());

    mockMvc
        .perform(
            delete(deleteUrl, 1)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf()))
        .andExpect(redirectedUrl(homeUrl))
        .andExpect(status().isFound());

    assertTrue(userRepository.findById(1).isEmpty());

    mockMvc
        .perform(
            delete(deleteUrl, 99)
                .with(user("userTest")
                    .roles("ADMIN"))
                .with(csrf()))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }
}
