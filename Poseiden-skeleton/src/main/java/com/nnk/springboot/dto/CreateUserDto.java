package com.nnk.springboot.dto;

import com.nnk.springboot.validation.PasswordValidation;
import javax.validation.constraints.NotBlank;

/**
 * Dto for User creation.
 */
public class CreateUserDto {


  @NotBlank(message = "Username is mandatory")
  private String username;

  @PasswordValidation
  private String password;

  @NotBlank(message = "Fullname is mandatory")
  private String fullname;

  @NotBlank(message = "Role is mandatory")
  private String role;

  public CreateUserDto() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "CreateUserDto{"
        + "username='" + username + '\''
        + ", password='" + password + '\''
        + ", fullname='" + fullname + '\''
        + ", role='" + role + '\''
        + '}';
  }
}
