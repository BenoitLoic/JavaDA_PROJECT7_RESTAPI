package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CreateUserDto;
import com.nnk.springboot.dto.GetUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
import com.nnk.springboot.services.UserService;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contains CRUD method for User model/entity.
 */
@RequestMapping("/user")
@Controller
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * This method will get all user in database.
   *
   * @param model the model that serve to inject user list for the view
   * @return the list of all user.
   */
  @GetMapping("/list")
  public String home(Model model) {
    Collection<GetUserDto> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "user/list";
  }

  /**
   * This method will return the form to add a new user.
   *
   * @return the html form.
   */
  @GetMapping("/add")
  public String addUserForm(CreateUserDto createUserDto) {
    return "user/add";
  }

  /**
   * This method will create a new user.
   *
   * @param createUserDto the user to create
   * @param result        validation check for user.
   * @return redirection to /user/list (get all user)
   */
  @PostMapping("/add")
  public String validate(@Valid CreateUserDto createUserDto, BindingResult result) {

    if (result.hasErrors()) {
      log.warn("Error in validation for user: "
          + createUserDto
          + " with error : "
          + result.getFieldErrors());
      return "user/add";
    }

    userService.createUser(createUserDto);

    return "redirect:/user/list";


  }

  /**
   * This method will return the update form with user's data with the given id.
   *
   * @param id    the id of the user to get data.
   * @param model the model where we inject the user data.
   * @return the form with user's data inside.
   */
  @GetMapping("/update/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {
    UpdateUserDto updateUserDto = userService.getUserWithId(id);
    model.addAttribute("updateUserDto", updateUserDto);
    return "user/update";
  }

  /**
   * This method will update the user with the given id.
   *
   * @param id            the id of the user to update
   * @param updateUserDto the user to update
   * @param result        validation check
   * @param model         the model that serve to inject user list for the view
   * @return redirection to /user/list (get all user)
   */
  @PutMapping("/update/{id}")
  public String updateUser(@PathVariable("id") int id, @Valid UpdateUserDto updateUserDto,
                           BindingResult result, Model model) {


    if (result.hasErrors()) {
      log.error("KO - error with user params : "
          + result.getFieldErrors());
      model.addAttribute("updateUserDto", updateUserDto);
      return "user/update";
    }

    userService.updateUser(id, updateUserDto);

    return "redirect:/user/list";
  }

  /**
   * This method delete the user with the given id.
   *
   * @param id    the id of the user to delete
   * @param model the model that serve to inject user list for the view
   * @return redirection to /user/list (get all user)
   */
  @DeleteMapping("/delete/{id}")
  public String deleteUser(@PathVariable("id") Integer id, Model model) {

    userService.deleteUser(id);

    return "redirect:/user/list";
  }
}
