package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;

public class CreateBidListDto {

  @NotBlank(message = " is mandatory")
  private String account;
  @NotBlank(message = " is mandatory")
  private String type;

  private double bidQuantity;

  public CreateBidListDto() {
  }

  public CreateBidListDto(String account, String type, double bidQuantity) {
    this.account = account;
    this.type = type;
    this.bidQuantity = bidQuantity;
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
    return "CreateBidListDto{" +
        "account='" + account + '\'' +
        ", type='" + type + '\'' +
        ", bidQuantity=" + bidQuantity +
        '}';
  }
}
