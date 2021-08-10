package com.nnk.springboot.dto;


public class GetTradeDto {

  private int tradeId;
  private String account;
  private String type;
  private Double buyQuantity;

  public GetTradeDto() {
  }

  public GetTradeDto(int tradeId, String account, String type, Double buyQuantity) {
    this.tradeId = tradeId;
    this.account = account;
    this.type = type;
    this.buyQuantity = buyQuantity;
  }

  public int getTradeId() {
    return tradeId;
  }

  public void setTradeId(int tradeId) {
    this.tradeId = tradeId;
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

  public Double getBuyQuantity() {
    return buyQuantity;
  }

  public void setBuyQuantity(Double buyQuantity) {
    this.buyQuantity = buyQuantity;
  }

  @Override
  public String toString() {
    return "GetTradeDto{" +
        "tradeId=" + tradeId +
        ", account='" + account + '\'' +
        ", type='" + type + '\'' +
        ", buyQuantity=" + buyQuantity +
        '}';
  }
}
