package com.nnk.springboot.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CreateCurvePointDto {


  private int curveId;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime asOfDate;
  private double term;
  private double value;

  public CreateCurvePointDto() {
  }

  public CreateCurvePointDto(int curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
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
    return "CreateCurvePointDto{" +
        "curveId=" + curveId +
        ", term=" + term +
        ", value=" + value +
        '}';
  }
}
