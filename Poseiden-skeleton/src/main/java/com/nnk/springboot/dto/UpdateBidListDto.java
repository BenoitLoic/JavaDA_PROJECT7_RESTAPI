package com.nnk.springboot.dto;

public class UpdateBidListDto {

  private int bidListId;
  private String account;
  private String type;
  private double bidQuantity;

  public UpdateBidListDto() {
  }

  public UpdateBidListDto(int bidListId, String account, String type, double bidQuantity) {
    this.bidListId = bidListId;
    this.account = account;
    this.type = type;
    this.bidQuantity = bidQuantity;
  }

  public int getBidListId() {
    return bidListId;
  }

  public void setBidListId(int bidListId) {
    this.bidListId = bidListId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBidQuantity() {
    return bidQuantity;
  }

  public void setBidQuantity(double bidQuantity) {
    this.bidQuantity = bidQuantity;
  }

  @Override
  public String toString() {
    return "UpdateBidListDto{" +
        "bidListId=" + bidListId +
        ", account='" + account + '\'' +
        ", type='" + type + '\'' +
        ", bidQuantity=" + bidQuantity +
        '}';
  }
}
