package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login Controller for Poseidon Api.
 * Contains endpoints for login and Spring security errors.
 */
@Controller
public class LoginController {

  private final UserRepository userRepository;

  private static final Logger log = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  public LoginController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @GetMapping("/loginPage")
  public String loginPage() {
    return "loginPage";
  }

  @GetMapping("/403")
  public String error(Model model) {

    String errorMessage = "You are not authorized for the requested data.";
    log.info(" ERROR " + errorMessage);
    model.addAttribute("errorMsg", errorMessage);

    return "error/403";
  }
}
