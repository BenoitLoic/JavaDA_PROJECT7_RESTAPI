package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @Autowired
  UserRepository userRepository;

  private final Logger log = LogManager.getLogger(getClass().getName());


  @GetMapping("/login")
  public ModelAndView login() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("login");
    return mav;
  }

  @GetMapping("/secure/article-details")
  public ModelAndView getAllUserArticles() {
    ModelAndView mav = new ModelAndView();
    mav.addObject("users", userRepository.findAll());
    mav.setViewName("user/list");
    return mav;
  }


  @GetMapping("/403")
  public String error(Model model) {

    String errorMessage = "You are not authorized for the requested data.";
    log.info(" ERROR " + errorMessage);
    model.addAttribute("errorMsg", errorMessage);

    return "error/403";
  }
}
