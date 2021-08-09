package com.nnk.springboot.dto;

public class UpdateCurvePointDto {

  private int curveId;
  private double term;
  private double value;

  public UpdateCurvePointDto() {
  }

  public UpdateCurvePointDto(int curveId, double term, double value) {
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
    return "UpdateCurvePointDto{" +
        "curveId=" + curveId +
        ", term=" + term +
        ", value=" + value +
        '}';
  }
}
