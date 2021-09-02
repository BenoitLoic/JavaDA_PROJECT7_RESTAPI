package com.nnk.springboot.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for RuleNAme Table.
 */
@Entity
@Table(name = "RuleName")
public class RuleName {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "json")
  private String json;

  @Column(name = "template")
  private String template;

  @Column(name = "sqlStr")
  private String sqlStr;

  @Column(name = "sqlPart")
  private String sqlPart;

  public RuleName() {
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
    RuleName ruleName = (RuleName) o;
    return id == ruleName.id
        && Objects.equals(name, ruleName.name)
        && Objects.equals(description, ruleName.description)
        && Objects.equals(json, ruleName.json)
        && Objects.equals(template, ruleName.template)
        && Objects.equals(sqlStr, ruleName.sqlStr)
        && Objects.equals(sqlPart, ruleName.sqlPart);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, json, template, sqlStr, sqlPart);
  }

  @Override
  public String toString() {
    return "RuleName{"
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
