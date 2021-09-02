package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller for Home page.
 */
@Controller
public class HomeController {

  @GetMapping("/")
  public String home(Model model) {
    return "home";
  }

}
