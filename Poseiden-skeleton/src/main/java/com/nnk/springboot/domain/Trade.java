package com.nnk.springboot.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 * Entity for Trade Table.
 */
@Entity
@Table(name = "Trade")
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TradeId")
  private int tradeId;

  @Column(name = "account")
  private String account;

  @Column(name = "type")
  private String type;

  @Column(name = "buyQuantity")
  private double buyQuantity;

  @Column(name = "sellQuantity")
  private double sellQuantity;

  @Column(name = "buyPrice")
  private double buyPrice;

  @Column(name = "sellPrice")
  private double sellPrice;

  @Column(name = "tradeDate")
  private LocalDateTime tradeDate;

  @Column(name = "security")
  private String security;

  @Column(name = "status")
  private String status;

  @Column(name = "trader")
  private String trader;

  @Column(name = "benchmark")
  private String benchmark;

  @Column(name = "book")
  private String book;

  @Column(name = "creationName")
  private String creationName;

  @Column(name = "creationDate")
  private LocalDateTime creationDate;

  @Column(name = "revisionName")
  private String revisionName;

  @Column(name = "revisionDate")
  private LocalDateTime revisionDate;

  @Column(name = "dealName")
  private String dealName;

  @Column(name = "dealType")
  private String dealType;

  @Column(name = "sourceListId")
  private String sourceListId;

  @Column(name = "side")
  private String side;

  public Trade() {
  }


  public int getTradeId() {
    return this.tradeId;
  }

  public void setTradeId(int tradeId) {
    this.tradeId = tradeId;
  }

  public String getAccount() {
    return this.account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getBuyQuantity() {
    return this.buyQuantity;
  }

  public void setBuyQuantity(Double buyQuantity) {
    this.buyQuantity = buyQuantity;
  }

  public Double getSellQuantity() {
    return this.sellQuantity;
  }

  public void setSellQuantity(Double sellQuantity) {
    this.sellQuantity = sellQuantity;
  }

  public Double getBuyPrice() {
    return this.buyPrice;
  }

  public void setBuyPrice(Double buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Double getSellPrice() {
    return this.sellPrice;
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

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  @PrePersist
  public void setCreationDate() {
    this.creationDate = this.revisionDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
  }

  public LocalDateTime getRevisionDate() {
    return revisionDate;
  }

  @PreUpdate
  public void setRevisionDate() {
    this.revisionDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
  }

  public String getSecurity() {
    return this.security;
  }

  public void setSecurity(String security) {
    this.security = security;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTrader() {
    return this.trader;
  }

  public void setTrader(String trader) {
    this.trader = trader;
  }

  public String getBenchmark() {
    return this.benchmark;
  }

  public void setBenchmark(String benchmark) {
    this.benchmark = benchmark;
  }

  public String getBook() {
    return this.book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public String getCreationName() {
    return this.creationName;
  }

  public void setCreationName(String creationName) {
    this.creationName = creationName;
  }

  public String getRevisionName() {
    return this.revisionName;
  }

  public void setRevisionName(String revisionName) {
    this.revisionName = revisionName;
  }

  public String getDealName() {
    return this.dealName;
  }

  public void setDealName(String dealName) {
    this.dealName = dealName;
  }

  public String getDealType() {
    return this.dealType;
  }

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }

  public String getSourceListId() {
    return this.sourceListId;
  }

  public void setSourceListId(String sourceListId) {
    this.sourceListId = sourceListId;
  }

  public String getSide() {
    return this.side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trade trade = (Trade) o;
    return tradeId == trade.tradeId
        && Double.compare(trade.buyQuantity, buyQuantity) == 0
        && Double.compare(trade.sellQuantity, sellQuantity) == 0
        && Double.compare(trade.buyPrice, buyPrice) == 0
        && Double.compare(trade.sellPrice, sellPrice) == 0
        && Objects.equals(account, trade.account)
        && Objects.equals(type, trade.type)
        && Objects.equals(tradeDate, trade.tradeDate)
        && Objects.equals(security, trade.security)
        && Objects.equals(status, trade.status)
        && Objects.equals(trader, trade.trader)
        && Objects.equals(benchmark, trade.benchmark)
        && Objects.equals(book, trade.book)
        && Objects.equals(creationName, trade.creationName)
        && Objects.equals(creationDate, trade.creationDate)
        && Objects.equals(revisionName, trade.revisionName)
        && Objects.equals(revisionDate, trade.revisionDate)
        && Objects.equals(dealName, trade.dealName)
        && Objects.equals(dealType, trade.dealType)
        && Objects.equals(sourceListId, trade.sourceListId)
        && Objects.equals(side, trade.side);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tradeId,
        account,
        type,
        buyQuantity,
        sellQuantity,
        buyPrice,
        sellPrice,
        tradeDate,
        security,
        status,
        trader,
        benchmark,
        book,
        creationName,
        creationDate,
        revisionName,
        revisionDate,
        dealName,
        dealType,
        sourceListId,
        side);
  }

  @Override
  public String toString() {
    return "Trade{"
        + "tradeId=" + tradeId
        + ", account='" + account + '\''
        + ", type='" + type + '\''
        + ", buyQuantity=" + buyQuantity
        + ", sellQuantity=" + sellQuantity
        + ", buyPrice=" + buyPrice
        + ", sellPrice=" + sellPrice
        + ", tradeDate=" + tradeDate
        + ", security='" + security + '\''
        + ", status='" + status + '\''
        + ", trader='" + trader + '\''
        + ", benchmark='" + benchmark + '\''
        + ", book='" + book + '\''
        + ", creationName='" + creationName + '\''
        + ", creationDate=" + creationDate
        + ", revisionName='" + revisionName + '\''
        + ", revisionDate=" + revisionDate
        + ", dealName='" + dealName + '\''
        + ", dealType='" + dealType + '\''
        + ", sourceListId='" + sourceListId + '\''
        + ", side='" + side + '\''
        + '}';
  }
}
