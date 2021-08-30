package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
public class LoginController {

  private final UserRepository userRepository;
  private final OAuth2AuthorizedClientService authorizedClientService;
  private final Logger log = LogManager.getLogger(getClass().getName());

  @Autowired
  public LoginController(UserRepository userRepository, OAuth2AuthorizedClientService authorizedClientService) {
    this.userRepository = userRepository;
    this.authorizedClientService = authorizedClientService;
  }

//  @GetMapping("/login")
//  public ModelAndView login() {
//    ModelAndView mav = new ModelAndView();
//    mav.setViewName("login");
//    return mav;
//  }

  @GetMapping("/secure/article-details")
  public ModelAndView getAllUserArticles() {
    ModelAndView mav = new ModelAndView();
    mav.addObject("users", userRepository.findAll());
    mav.setViewName("user/list");
    return mav;
  }

  @ResponseBody
  @GetMapping("/home")
  public String getUserInfo(Principal user) {
    StringBuffer userInfo = new StringBuffer();

    if (user instanceof UsernamePasswordAuthenticationToken) {
      userInfo.append(getUsernamePasswordLoginInfo(user));
    } else if (user instanceof OAuth2AuthenticationToken) {
      userInfo.append(getOAuth2LoginInfo(user));
    }
    return userInfo.toString();
  }

  private StringBuffer getOAuth2LoginInfo(Principal user) {
    StringBuffer protectedInfo = new StringBuffer();
    OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
    OAuth2AuthorizedClient authClient =
        this.authorizedClientService.loadAuthorizedClient(
            authToken.getAuthorizedClientRegistrationId(),
            authToken.getName());
    if (authToken.isAuthenticated()) {
      Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

      String userToken = authClient.getAccessToken().getTokenValue();
      protectedInfo.append("Welcome, " + userAttributes.get("login") + "<br><br>");
      protectedInfo.append("email: " + userAttributes.get("email") + "<br><br>");
      protectedInfo.append("Access Token: " + userToken + "<br><br>");
      protectedInfo.append(userAttributes.toString());
      OidcIdToken idToken = getIdToken(principal);

      if (idToken!=null){
        protectedInfo.append("idToken value: "+idToken.getTokenValue()+"<br><br>");
        protectedInfo.append("Token mapped values <br><br>");
        Map<String,Object>claims = idToken.getClaims();
        for (String key : claims.keySet()){
          protectedInfo.append("     "+key+":   "+claims.get(key)+"<br>");
        }
      }
    } else {
      protectedInfo.append("NA");
    }

    return protectedInfo;

  }

  private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
    StringBuffer usernameInfo = new StringBuffer();
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
    if (token.isAuthenticated()) {
      User u = (User) token.getPrincipal();
      usernameInfo.append("Welcome, " + u.getUsername() + "<br>");
      usernameInfo.append("Role: " + u.getAuthorities());
    } else {
      usernameInfo.append("NA");
    }
    return usernameInfo;
  }


  private OidcIdToken getIdToken(OAuth2User principal) {
    if (principal instanceof DefaultOidcUser) {
      DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
      return oidcUser.getIdToken();
    }
    return null;
  }

  @GetMapping("/error")
  public String error(Model model) {

    String errorMessage = "You are not authorized for the requested data.";
    log.info(" ERROR " + errorMessage);
    model.addAttribute("errorMsg", errorMessage);

    return "403";
  }
}
