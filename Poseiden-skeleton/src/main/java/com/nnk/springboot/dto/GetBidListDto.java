package com.nnk.springboot.dto;

/**
 * Dto for BidList Read.
 */
public class GetBidListDto {

  private int bidListId;
  private String account;
  private String type;
  private double bidQuantity;

  public GetBidListDto() {
  }

  /**
   * Constructor with all field.
   *
   * @param bidListId   the id
   * @param account     the account
   * @param type        the type
   * @param bidQuantity the bid quantity
   */
  public GetBidListDto(int bidListId, String account, String type, double bidQuantity) {
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
    return "GetBidListDto{"
        + "bidListId=" + bidListId
        + ", account='" + account + '\''
        + ", type='" + type + '\''
        + ", bidQuantity=" + bidQuantity + '}';
  }
}
