package com.nnk.springboot.dto;

/**
 * Dto for CurvePoint Read functionality.
 */
public class GetCurvePointDto {

  private int id;
  private int curveId;
  private double term;
  private double value;

  public GetCurvePointDto() {
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
    return "GetCurvePointDto{"
        + "id=" + id
        + ", curveId=" + curveId
        + ", term=" + term
        + ", value=" + value
        + '}';
  }
}
