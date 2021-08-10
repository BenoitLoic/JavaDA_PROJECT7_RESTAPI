package com.nnk.springboot.dto;

public class UpdateRuleDto {

  private String name;
  private String description;
  private String json;
  private String template;
  private String sqlStr;
  private String sqlPart;

  public UpdateRuleDto() {
  }

  public UpdateRuleDto(String name, String description, String json, String template, String sqlStr, String sqlPart) {
    this.name = name;
    this.description = description;
    this.json = json;
    this.template = template;
    this.sqlStr = sqlStr;
    this.sqlPart = sqlPart;
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
  public String toString() {
    return "UpdateRuleDto{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", json='" + json + '\'' +
        ", template='" + template + '\'' +
        ", sqlStr='" + sqlStr + '\'' +
        ", sqlPart='" + sqlPart + '\'' +
        '}';
  }
}
