package com.nnk.springboot.dto;

public class GetRatingDto {

  private int id;
  private String moodysRating;
  private String sandPRating;
  private String fitchRating;
  private int orderNumber;

  public GetRatingDto() {
  }

  public GetRatingDto(int id, String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
    this.id = id;
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
  public String toString() {
    return "GetRatingDto{" +
        "id=" + id +
        ", moodysRating='" + moodysRating + '\'' +
        ", sandPRating='" + sandPRating + '\'' +
        ", fitchRating='" + fitchRating + '\'' +
        ", orderNumber=" + orderNumber +
        '}';
  }
}