package com.nnk.springboot.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "CurvePoint")
public class CurvePoint {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  private int id;

  @Column(name = "CurveId")
  private int curveId;

  @Column(name = "asOfDate")
  private Timestamp asOfDate;

  @Column(name = "term")
  private double term;

  @Column(name = "value")
  private double value;

  @Column(name = "creationDate")
  private LocalDateTime creationDate;

  public CurvePoint() {
  }

  public CurvePoint(int curveId, double term, double value) {
    this.curveId = curveId;
    this.term = term;
    this.value = value;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCurveId() {
    return this.curveId;
  }

  public void setCurveId(int curveId) {
    this.curveId = curveId;
  }

  public Timestamp getAsOfDate() {
    return this.asOfDate;
  }

  public void setAsOfDate(Timestamp asOfDate) {
    this.asOfDate = asOfDate;
  }

  public double getTerm() {
    return this.term;
  }

  public void setTerm(Double term) {
    this.term = term;
  }

  public double getValue() {
    return this.value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  @PrePersist
  public void setCreationDate() {
    creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
  }

  @Override
  public String toString() {
    return "CurvePoint{" +
        "id=" + id +
        ", curveId=" + curveId +
        ", asOfDate=" + asOfDate +
        ", term=" + term +
        ", value=" + value +
        ", creationDate=" + creationDate +
        '}';
  }
}
