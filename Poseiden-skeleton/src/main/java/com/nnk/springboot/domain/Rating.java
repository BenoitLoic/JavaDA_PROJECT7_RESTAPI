package com.nnk.springboot.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Rating")
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id")
  private int id;

  @Column(name = "moodysRating")
  private String moodysRating;


  @Column(name = "sandPRating")
  private String sandPRating;

  @Column(name = "fitchRating")
  private String fitchRating;

  @Column(name = "orderNumber")
  private int orderNumber;

  public Rating() {
  }

  public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
    this.moodysRating = moodysRating;
    this.sandPRating = sandPRating;
    this.fitchRating = fitchRating;
    this.orderNumber = orderNumber;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMoodysRating() {
    return moodysRating;
  }

  public void setMoodysRating(String moodysRating) {
    this.moodysRating = moodysRating;
  }

  public String getSandPRating() {
    return sandPRating;
  }

  public void setSandPRating(String sandPRating) {
    this.sandPRating = sandPRating;
  }

  public String getFitchRating() {
    return fitchRating;
  }

  public void setFitchRating(String fitchRating) {
    this.fitchRating = fitchRating;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rating rating = (Rating) o;
    return id == rating.id && orderNumber == rating.orderNumber && Objects.equals(moodysRating, rating.moodysRating) && Objects.equals(sandPRating, rating.sandPRating) && Objects.equals(fitchRating, rating.fitchRating);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, moodysRating, sandPRating, fitchRating, orderNumber);
  }

  @Override
  public String toString() {
    return "Rating{" +
        "id=" + id +
        ", moodysRating='" + moodysRating + '\'' +
        ", sandPRating='" + sandPRating + '\'' +
        ", fitchRating='" + fitchRating + '\'' +
        ", orderNumber=" + orderNumber +
        '}';
  }
}
