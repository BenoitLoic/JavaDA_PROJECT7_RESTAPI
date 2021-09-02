package com.nnk.springboot.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Entity for User Table.
 */
@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  private int id;

  @Column(name = "username")
  @NotBlank(message = "Username is mandatory")
  private String username;

  @Column(name = "password")
  @NotBlank(message = "Password is mandatory")
  private String password;

  @Column(name = "fullname")
  @NotBlank(message = "FullName is mandatory")
  private String fullname;

  @Column(name = "role")
  @NotBlank(message = "Role is mandatory")
  private String role;

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id
        && Objects.equals(username, user.username)
        && Objects.equals(fullname, user.fullname)
        && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, fullname, role);
  }

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", username='" + username + '\''
        + ", password='" + password + '\''
        + ", fullname='" + fullname + '\''
        + ", role='" + role + '\''
        + '}';
  }


}
