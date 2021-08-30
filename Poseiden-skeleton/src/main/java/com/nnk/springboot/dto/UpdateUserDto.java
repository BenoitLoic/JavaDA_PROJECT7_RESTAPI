package com.nnk.springboot.dto;

import com.nnk.springboot.validation.PasswordValidation;

import javax.validation.constraints.NotBlank;

public class UpdateUserDto {

  private int id;
  @NotBlank(message = " is mandatory.")
  private String username;
  @PasswordValidation
  private String password;
  @NotBlank(message = " is mandatory.")
  private String fullname;
  @NotBlank(message = " is mandatory.")
  private String role;

  public UpdateUserDto() {
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    return "UpdateUserDto{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", fullname='" + fullname + '\'' +
        ", role='" + role + '\'' +
        '}';
  }

}
