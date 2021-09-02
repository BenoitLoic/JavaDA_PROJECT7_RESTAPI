package com.nnk.springboot.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Dto for CurvePoint Update functionality.
 */
public class UpdateCurvePointDto {

  private int id;

  private int curveId;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime asOfDate;

  private double term;

  private double value;

  public UpdateCurvePointDto() {
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCurveId() {
    return curveId;
  }

  public void setCurveId(int curveId) {
    this.curveId = curveId;
  }

  public LocalDateTime getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(LocalDateTime asOfDate) {
    this.asOfDate = asOfDate;
  }

  public double getTerm() {
    return term;
  }

  public void setTerm(double term) {
    this.term = term;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "UpdateCurvePointDto{"
        + "id=" + id
        + ", curveId=" + curveId
        + ", asOfDate=" + asOfDate
        + ", term=" + term
        + ", value=" + value
        + '}';
  }
}
