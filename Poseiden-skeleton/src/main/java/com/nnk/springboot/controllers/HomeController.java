package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple controller for Home page.
 */
@Controller
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/admin/home")
  public String adminHome(Authentication auth) {

    for (GrantedAuthority role : auth.getAuthorities()) {
      if (role.getAuthority().equals("ROLE_ADMIN")) {
        return "redirect:/user/list";
      }
    }
    return "redirect:/bidList/list";
  }
}
