package com.nnk.springboot.config;

import com.nnk.springboot.security.MySimpleUrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    String[] staticRessources = {"/css/**", "/img/**"};

    http
        .authorizeRequests()
        // permit all
          .antMatchers("/error",
              "/user/add")
          .permitAll()
        .antMatchers(staticRessources)
        .permitAll()
        // user
          .antMatchers(
              "/bidList/**",
              "/curvePoint/**",
              "/ruleName/**",
              "/rating/**",
              "/trade/**",
              "/secure/article-details")
          .hasAnyRole("USER", "ADMIN")
        //admin
          .antMatchers("/user/**")
          .hasRole("ADMIN")
        .and()
          .exceptionHandling()
          .accessDeniedPage("/403")
//        .and()
//        .exceptionHandling()
//        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
          .formLogin()
          .loginPage("/loginPage")
          .loginProcessingUrl("/login")
          .successHandler(myAuthenticationSuccessHandler())
        .and()
          .oauth2Login().successHandler(myAuthenticationSuccessHandler())
        .and()
          .oauth2Client(Customizer.withDefaults())
    ;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
    return new MySimpleUrlAuthenticationSuccessHandler();
  }

}
