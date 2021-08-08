package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CreateUserDto {


    @NotBlank(message = "Username is mandatory")
    private String username;


    @NotBlank(message = "Password is mandatory")
    private String password;


    @NotBlank(message = "Fullname is mandatory")
    private String fullname;


    @NotBlank(message = "Role is mandatory")
    private String role;

    public CreateUserDto() {
    }

    public CreateUserDto(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
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
        return "CreateUserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateUserDto that = (CreateUserDto) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(fullname, that.fullname) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, fullname, role);
    }
}
