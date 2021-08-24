package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class UpdateRuleNameDto {

  private int id;
  @NotBlank(message = " is mandatory.")
  private String name;
  @NotBlank(message = " is mandatory.")
  private String description;
  @NotBlank(message = " is mandatory.")
  private String json;
  @NotBlank(message = " is mandatory.")
  private String template;
  @NotBlank(message = " is mandatory.")
  private String sqlStr;
  @NotBlank(message = " is mandatory.")
  private String sqlPart;

  public UpdateRuleNameDto() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getSqlStr() {
    return sqlStr;
  }

  public void setSqlStr(String sqlStr) {
    this.sqlStr = sqlStr;
  }

  public String getSqlPart() {
    return sqlPart;
  }

  public void setSqlPart(String sqlPart) {
    this.sqlPart = sqlPart;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateRuleNameDto that = (UpdateRuleNameDto) o;
    return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(json, that.json) && Objects.equals(template, that.template) && Objects.equals(sqlStr, that.sqlStr) && Objects.equals(sqlPart, that.sqlPart);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, json, template, sqlStr, sqlPart);
  }

  @Override
  public String toString() {
    return "UpdateRuleNameDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", json='" + json + '\'' +
        ", template='" + template + '\'' +
        ", sqlStr='" + sqlStr + '\'' +
        ", sqlPart='" + sqlPart + '\'' +
        '}';
  }
}