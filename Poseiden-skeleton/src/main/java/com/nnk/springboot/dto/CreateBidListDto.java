package com.nnk.springboot.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;

/**
 * Dto for BidList creation.
 */
public class CreateBidListDto {

  @NotBlank(message = " is mandatory")
  private String account;

  @NotBlank(message = " is mandatory")
  private String type;

  private double bidQuantity;

  public CreateBidListDto() {
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
    return "CreateBidListDto{"
        + "account='" + account + '\''
        + ", type='" + type + '\''
        + ", bidQuantity=" + bidQuantity
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateBidListDto that = (CreateBidListDto) o;
    return Double.compare(that.bidQuantity, bidQuantity) == 0
        && Objects.equals(account, that.account)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, type, bidQuantity);
  }
}
