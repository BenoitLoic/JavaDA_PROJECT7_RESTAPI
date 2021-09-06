package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Simple controller for Home page.
 */
@Controller
public class HomeController {

  @GetMapping("/")
  public String home(Model model) {
    return "home";
  }

  @GetMapping("/admin/home")
  public String homeAdmin(Authentication auth) {
    for (GrantedAuthority temp :
        auth.getAuthorities()) {
      if (temp.getAuthority().equals("ROLE_ADMIN")) {
        return "redirect:/user/list";
      }
    }
    return "redirect:/bidList/list";
  }
}
