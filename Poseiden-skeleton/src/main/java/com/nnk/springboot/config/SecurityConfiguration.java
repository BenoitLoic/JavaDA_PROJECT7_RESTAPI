package com.nnk.springboot.config;

import com.nnk.springboot.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .formLogin()
          .failureUrl("/error")
          .and()
        .exceptionHandling()
          .accessDeniedPage("/error")
          .and()
        .authorizeRequests()
        // permit all
          .antMatchers("/", "/app/**")
          .permitAll()
        // user
          .antMatchers(
              "/bidList/**",
              "/curvePoint/**",
              "/ruleName/**",
              "/rating/**",
              "/trade/**")
          .hasAnyAuthority("USER")
        //admin
          .antMatchers("/user/**")
          .hasAuthority("ADMIN")
    ;
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
