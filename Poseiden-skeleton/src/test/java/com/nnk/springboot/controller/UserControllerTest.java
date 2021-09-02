package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.GetUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.exceptions.DataNotFoundException;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
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

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false)
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  UserServiceImpl userServiceMock;
  @MockBean private UserDetailsServiceImpl userDetailsService;



  private final static String homeUrl = "/user/list";
  private final static String createFormUrl = "/user/add";
  private final static String createUrl = "/user/add";
  private final static String updateFormUrl = "/user/update/{id}";
  private final static String updateUrl = "/user/update/{id}";
  private final static String deleteUrl = "/user/delete/{id}";

  @Test
  void homeValid() throws Exception {

    // GIVEN
    // GetTradeDto(int tradeId, String account, String type, Double buyQuantity)
    GetUserDto user1 = new GetUserDto();
    user1.setId(1);
    user1.setUsername("username1");
    user1.setFullname("fullname1");
    user1.setRole("role1");
    GetUserDto user2 = new GetUserDto();
    user2.setId(2);
    user2.setUsername("username2");
    user2.setFullname("fullname2");
    user2.setRole("role2");
    List<GetUserDto> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);
    // WHEN
    when(userServiceMock.getAllUsers()).thenReturn(users);
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("user/list"))
        .andExpect(model()
            .attribute("users", containsInAnyOrder(user1, user2)));
  }

  @Test
  void homeWhenNoUser_ShouldReturnEmptyList() throws Exception {

    // GIVEN

    // WHEN
    when(userServiceMock.getAllUsers()).thenReturn(new ArrayList<>());
    // THEN
    mockMvc
        .perform(
            get(homeUrl))
        .andExpect(status().isOk())
        .andExpect(view().name("user/list"))
        .andExpect(model()
            .attribute("users", new ArrayList<>()));
  }

  @Test
  void addUserForm() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(
            get(createFormUrl))
        .andExpect(status().isOk())
        .andExpect(
            view().name("user/add"));
  }

  @Test
  void validateValid() throws Exception {

    // GIVEN
    CreateUserDto createUserDto = new CreateUserDto();
    createUserDto.setUsername("usernameTest");
    createUserDto.setFullname("fullnameTest");
    createUserDto.setRole("USER");
    createUserDto.setPassword("PassTest100$");

    String urlEncoded = getUrlEncoded(createUserDto);
    System.out.println(urlEncoded);
    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .content(getUrlEncoded(createUserDto))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));
  }


  @Test
  void validateInvalid() throws Exception {

    // GIVEN
    CreateUserDto user = new CreateUserDto();
    user.setUsername("username1");
    user.setFullname("fullname1");
    user.setRole("role1");
    user.setPassword("passtest0$");
    String urlEncoded = getUrlEncoded(user);
    System.out.println(urlEncoded);
    // WHEN

    // THEN
    mockMvc
        .perform(post(createUrl)
            .content(urlEncoded)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
        .andExpect(status().isOk())
        .andExpect(model().errorCount(1))
        .andExpect(model().attributeHasFieldErrors("createUserDto", "password"));
  }


  @Test
  void showUpdateFormValid() throws Exception {

    // GIVEN
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");


    // WHEN
    when(userServiceMock.getUserWithId(5)).thenReturn(updateUserDto);
    // THEN
    mockMvc
        .perform(
            get(updateFormUrl, "5"))
        .andExpect(view().name("user/update"))
        .andExpect(model().attribute("updateUserDto", updateUserDto))
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
  void updateUserValid() throws Exception {

    // GIVEN
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");
    updateUserDto.setPassword("passTest100*");


    String urlEncoded = getUrlEncoded(updateUserDto);

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
  void updateUserValidPasswordNull() throws Exception {

    // GIVEN
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");
    updateUserDto.setPassword(null);


    String urlEncoded = getUrlEncoded(updateUserDto);

    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(model().attributeHasFieldErrors("updateUserDto", "password"))
        .andExpect(model().errorCount(1));
  }

  @Test
  void updateUserInvalidPasswordPattern() throws Exception {

    // GIVEN
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");
    updateUserDto.setPassword("passtest0$");


    String urlEncoded = getUrlEncoded(updateUserDto);


    // WHEN

    // THEN
    mockMvc
        .perform(
            put(updateUrl, "5")
                .content(urlEncoded)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("updateUserDto", "password"))
        .andExpect(model().errorCount(1));

  }

  @Test
  void updateUserWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    UpdateUserDto updateUserDto = new UpdateUserDto();
    updateUserDto.setId(5);
    updateUserDto.setUsername("username1");
    updateUserDto.setFullname("fullname1");
    updateUserDto.setRole("role1");
    updateUserDto.setPassword("passTest100$");

    String urlEncoded = getUrlEncoded(updateUserDto);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class).when(userServiceMock).updateUser(Mockito.anyInt(), Mockito.any(UpdateUserDto.class));
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
  void deleteUser() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, 2))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl(homeUrl));

  }

  @Test
  void deleteUserInvalid() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc.perform(delete(deleteUrl, ""))
        .andExpect(status().isNotFound());

  }

  @Test
  void deleteUserWhenDataNotFound_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(userServiceMock).deleteUser(Mockito.anyInt());
    // THEN
    mockMvc
        .perform(delete(deleteUrl, 5))
        .andExpect(status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DataNotFoundException));

  }


}