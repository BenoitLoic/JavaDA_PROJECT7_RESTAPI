package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;

/**
 * Dto for BidList Update functionality.
 */
public class UpdateBidListDto {

  private int bidListId;

  @NotBlank(message = " is mandatory.")
  private String account;

  @NotBlank(message = " is mandatory.")
  private String type;

  private double bidQuantity;

  public UpdateBidListDto() {
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
    return "UpdateBidListDto{"
        + "bidListId=" + bidListId
        + ", account='" + account + '\''
        + ", type='" + type + '\''
        + ", bidQuantity=" + bidQuantity
        + '}';
  }
}
