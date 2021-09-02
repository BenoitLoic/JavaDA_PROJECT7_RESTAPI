package com.nnk.springboot.dto;

/**
 * Dto for RuleName Read functionality.
 */
public class GetRuleNameDto {

  private int id;
  private String name;
  private String description;
  private String json;
  private String template;
  private String sqlStr;
  private String sqlPart;

  public GetRuleNameDto() {
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
  public String toString() {
    return "GetRuleNameDto{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", description='" + description + '\''
        + ", json='" + json + '\''
        + ", template='" + template + '\''
        + ", sqlStr='" + sqlStr + '\''
        + ", sqlPart='" + sqlPart + '\''
        + '}';
  }
}
