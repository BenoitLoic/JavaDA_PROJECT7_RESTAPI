package com.nnk.springboot.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Dto for CurvePoint creation.
 */
public class CreateCurvePointDto {


  private int curveId;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime asOfDate;

  private double term;
  private double value;

  public CreateCurvePointDto() {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateCurvePointDto that = (CreateCurvePointDto) o;
    return curveId == that.curveId
        && Double.compare(that.term, term) == 0
        && Double.compare(that.value, value) == 0
        && Objects.equals(asOfDate, that.asOfDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(curveId, asOfDate, term, value);
  }

  @Override
  public String toString() {
    return "CreateCurvePointDto{"
        + "curveId=" + curveId
        + ", term=" + term
        + ", value=" + value
        + '}';
  }
}
