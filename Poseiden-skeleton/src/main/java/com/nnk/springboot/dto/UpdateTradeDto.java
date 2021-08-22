package com.nnk.springboot.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class UpdateTradeDto {

  private int tradeId;
  @NotBlank
  private String account;
  @NotBlank
  private String type;

  private double buyQuantity;
  private double sellQuantity;
  private double buyPrice;
  private double sellPrice;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime tradeDate ;

  private String security;
  private String status;
  private String trader;
  private String benchmark;
  private String book;

  private String creationName;

  private String revisionName;

  private String dealName;
  private String dealType;
  private String sourceListId;
  private String side;

  public UpdateTradeDto() {
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

  public Double getSellQuantity() {
    return sellQuantity;
  }

  public void setSellQuantity(Double sellQuantity) {
    this.sellQuantity = sellQuantity;
  }

  public Double getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(Double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Double getSellPrice() {
    return sellPrice;
  }

  public void setSellPrice(Double sellPrice) {
    this.sellPrice = sellPrice;
  }

  public LocalDateTime getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(LocalDateTime tradeDate) {
    this.tradeDate = tradeDate;
  }

  public String getSecurity() {
    return security;
  }

  public void setSecurity(String security) {
    this.security = security;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTrader() {
    return trader;
  }

  public void setTrader(String trader) {
    this.trader = trader;
  }

  public String getBenchmark() {
    return benchmark;
  }

  public void setBenchmark(String benchmark) {
    this.benchmark = benchmark;
  }

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public String getCreationName() {
    return creationName;
  }

  public void setCreationName(String creationName) {
    this.creationName = creationName;
  }

  public String getRevisionName() {
    return revisionName;
  }

  public void setRevisionName(String revisionName) {
    this.revisionName = revisionName;
  }

  public String getDealName() {
    return dealName;
  }

  public void setDealName(String dealName) {
    this.dealName = dealName;
  }

  public String getDealType() {
    return dealType;
  }

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }

  public String getSourceListId() {
    return sourceListId;
  }

  public void setSourceListId(String sourceListId) {
    this.sourceListId = sourceListId;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }



  @Override
  public String toString() {
    return "UpdateTradeDto{" +
        "account='" + account + '\'' +
        ", type='" + type + '\'' +
        ", buyQuantity=" + buyQuantity +
        ", sellQuantity=" + sellQuantity +
        ", buyPrice=" + buyPrice +
        ", sellPrice=" + sellPrice +
        ", tradeDate=" + tradeDate +
        ", security='" + security + '\'' +
        ", status='" + status + '\'' +
        ", trader='" + trader + '\'' +
        ", benchmark='" + benchmark + '\'' +
        ", book='" + book + '\'' +
        ", creationName='" + creationName + '\'' +
        ", revisionName='" + revisionName + '\'' +
        ", dealName='" + dealName + '\'' +
        ", dealType='" + dealType + '\'' +
        ", sourceListId='" + sourceListId + '\'' +
        ", side='" + side + '\'' +
        '}';
  }
}
