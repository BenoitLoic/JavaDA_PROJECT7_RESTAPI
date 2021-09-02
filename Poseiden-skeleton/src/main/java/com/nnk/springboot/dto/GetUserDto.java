package com.nnk.springboot.dto;

/**
 * Dto for User Read functionality.
 */
public class GetUserDto {

  private int id;

  private String username;

  private String fullname;

  private String role;

  public GetUserDto() {
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
    return "GetUserDto{"
        + "id=" + id
        + ", username='" + username + '\''
        + ", fullname='" + fullname + '\''
        + ", role='" + role + '\''
        + '}';
  }
}
