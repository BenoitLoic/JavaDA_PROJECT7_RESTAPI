package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CreateRatingDto {
  @NotBlank(message = " is mandatory.")
  private String moodysRating;
  @NotBlank(message = " is mandatory.")
  private String sandPRating;
  @NotBlank(message = " is mandatory.")
  private String fitchRating;
  private int orderNumber;

  public CreateRatingDto() {
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
    CreateRatingDto that = (CreateRatingDto) o;
    return orderNumber == that.orderNumber && Objects.equals(moodysRating, that.moodysRating) && Objects.equals(sandPRating, that.sandPRating) && Objects.equals(fitchRating, that.fitchRating);
  }

  @Override
  public int hashCode() {
    return Objects.hash(moodysRating, sandPRating, fitchRating, orderNumber);
  }

  @Override
  public String toString() {
    return "CreateRatingDto{" +
        "moodysRating='" + moodysRating + '\'' +
        ", sandPRating='" + sandPRating + '\'' +
        ", fitchRating='" + fitchRating + '\'' +
        ", orderNumber=" + orderNumber +
        '}';
  }
}
