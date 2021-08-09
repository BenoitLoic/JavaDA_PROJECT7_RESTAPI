package com.nnk.springboot.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BidList")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BidListId")
    private int bidListId;

    @Column(name = "account")
    private String account;

    @Column(name = "type")
    private String type;

    @Column(name = "bidQuantity")
    private double bidQuantity;

    @Column(name = "askQuantity")
    private double askQuantity;

    @Column(name = "bid")
    private double bid;

    @Column(name = "ask")
    private double ask;

    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "bidListDate")
    private Timestamp bidListDate;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "security")
    private String security;

    @Column(name = "status")
    private String status;

    @Column(name = "trader")
    private String trader;

    @Column(name = "book")
    private String book;

    @Column(name = "creationName")
    private String creationName;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @Column(name = "revisionName")
    private String revisionName;

    @Column(name = "revisionDate")
    private Timestamp revisionDate;

    @Column(name = "dealName")
    private String dealName;

    @Column(name = "dealType")
    private String dealType;

    @Column(name = "sourceListId")
    private String sourceListId;

    @Column(name = "side")
    private String side;

    public BidList() {
    }

    public BidList(String account, String type, double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public int getBidListId() {
        return this.bidListId;
    }

    public void setBidListId(int bidListId) {
        this.bidListId = bidListId;
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

    public double getBidQuantity() {
        return this.bidQuantity;
    }

    public void setBidQuantity(double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public double getAskQuantity() {
        return this.askQuantity;
    }

    public void setAskQuantity(double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public double getBid() {
        return this.bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return this.ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return this.benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return this.bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
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

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return this.revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return this.revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
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
    public String toString() {
        return "BidList{" +
                "bidListId=" + bidListId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", bidQuantity=" + bidQuantity +
                ", askQuantity=" + askQuantity +
                ", bid=" + bid +
                ", ask=" + ask +
                ", benchmark='" + benchmark + '\'' +
                ", bidListDate=" + bidListDate +
                ", commentary='" + commentary + '\'' +
                ", security='" + security + '\'' +
                ", status='" + status + '\'' +
                ", trader='" + trader + '\'' +
                ", book='" + book + '\'' +
                ", creationName='" + creationName + '\'' +
                ", creationDate=" + creationDate +
                ", revisionName='" + revisionName + '\'' +
                ", revisionDate=" + revisionDate +
                ", dealName='" + dealName + '\'' +
                ", dealType='" + dealType + '\'' +
                ", sourceListId='" + sourceListId + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
