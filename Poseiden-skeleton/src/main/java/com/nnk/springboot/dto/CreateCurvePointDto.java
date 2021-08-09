package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateCurvePointDto {

  @NotBlank(message = " is mandatory.")
  @Size
  private int curveId;
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
