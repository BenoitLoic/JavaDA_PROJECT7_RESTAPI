# Create schema

CREATE SCHEMA poseidon_test;
USE poseidon_test;

# Create Tables.

CREATE TABLE BidList
(
    BidListId    int NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    bidQuantity  DOUBLE,
    askQuantity  DOUBLE,
    bid          DOUBLE,
    ask          DOUBLE,
    benchmark    VARCHAR(125),
    bidListDate  DATETIME NULL DEFAULT NULL ,
    commentary   VARCHAR(125),
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    book         VARCHAR(125),
    creationName VARCHAR(125),
    creationDate DATETIME NOT NULL,
    revisionName VARCHAR(125),
    revisionDate DATETIME NOT NULL,
    dealName     VARCHAR(125),
    dealType     VARCHAR(125),
    sourceListId VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (BidListId)
);

CREATE TABLE Trade
(
    TradeId      int NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    buyQuantity  DOUBLE,
    sellQuantity DOUBLE,
    buyPrice     DOUBLE,
    sellPrice    DOUBLE,
    tradeDate    DATETIME NULL default NULL,
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    benchmark    VARCHAR(125),
    book         VARCHAR(125),
    creationName VARCHAR(125),
    creationDate DATETIME    NOT NULL,
    revisionName VARCHAR(125),
    revisionDate DATETIME    NOT NULL,
    dealName     VARCHAR(125),
    dealType     VARCHAR(125),
    sourceListId VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (TradeId)
);

CREATE TABLE CurvePoint
(
    Id           int NOT NULL AUTO_INCREMENT,
    CurveId      int,
    asOfDate     DATETIME NULL default NULL,
    term         DOUBLE,
    value        DOUBLE,
    creationDate DATETIME NOT NULL,

    PRIMARY KEY (Id)
);

CREATE TABLE Rating
(
    Id           int NOT NULL AUTO_INCREMENT,
    moodysRating VARCHAR(125),
    sandPRating  VARCHAR(125),
    fitchRating  VARCHAR(125),
    orderNumber  int,

    PRIMARY KEY (Id)
);

CREATE TABLE RuleName
(
    Id          int NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(512),
    sqlStr      VARCHAR(125),
    sqlPart     VARCHAR(125),

    PRIMARY KEY (Id)
);

CREATE TABLE Users
(
    Id       int NOT NULL AUTO_INCREMENT,
    username VARCHAR(125) NOT NULL,
    password VARCHAR(125) NOT NULL,
    fullname VARCHAR(125) NOT NULL,
    role     VARCHAR(125) NOT NULL,

    PRIMARY KEY (Id)
);

# Populate
INSERT INTO `poseidon_test`.`Users` (`Id`, `username`, `password`, `fullname`, `role`) VALUES ('1', 'testUser', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', 'Test User', 'USER');
INSERT INTO `poseidon_test`.`Users` (`Id`, `username`, `password`, `fullname`, `role`) VALUES ('2', 'testUser2', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', 'Test Second User', 'USER');
INSERT INTO `poseidon_test`.`Users` (`Id`, `username`, `password`, `fullname`, `role`) VALUES ('3', 'testAdmin', '$2a$10$qiVpUqQKa5Truu0j3QpRvuJVTpiTyWbyDGR.NvSG5hFBEH8N3hFaG', 'Test Admin', 'ADMIN');

INSERT INTO `poseidon_test`.`BidList` (`BidListId`, `account`, `type`, `bidQuantity`, `askQuantity`, `bid`, `ask`, `benchmark`, `bidListDate`, `commentary`, `security`, `status`, `trader`, `book`, `creationName`, `creationDate`, `revisionName`, `revisionDate`, `dealName`, `dealType`, `sourceListId`, `side`) VALUES ('1', 'account test 1', 'account type 1', '1.11', '1.0', '0', '0', '0', '2021-08-18 16:55:00', 'comment test', 'security test', 'statusTest', 'trader test', 'book test', 'cn test', '2021-08-18 12:55:00', 'rn test', '2021-08-18 12:55:00', 'dn test', 'dt test', 'sl test', 'side test');
INSERT INTO `poseidon_test`.`BidList` (`BidListId`, `account`, `type`, `bidQuantity`, `askQuantity`, `bid`, `ask`, `benchmark`, `bidListDate`, `commentary`, `security`, `status`, `trader`, `book`, `creationName`, `creationDate`, `revisionName`, `revisionDate`, `dealName`, `dealType`, `sourceListId`, `side`) VALUES ('2', 'account test 2', 'account type 2', '2.42', '1.0', '0', '0', '0', '2021-08-18 16:55:00', 'comment test', 'security test', 'statusTest', 'trader test', 'book test', 'cn test', '2021-08-18 12:55:00', 'rn test', '2021-08-18 12:55:00', 'dn test', 'dt test', 'sl test', 'side test');
INSERT INTO `poseidon_test`.`BidList` (`BidListId`, `account`, `type`, `bidQuantity`, `askQuantity`, `bid`, `ask`, `benchmark`, `bidListDate`, `commentary`, `security`, `status`, `trader`, `book`, `creationName`, `creationDate`, `revisionName`, `revisionDate`, `dealName`, `dealType`, `sourceListId`, `side`) VALUES ('4', 'account test 4', 'account type 4', '4.44', '1.0', '0', '0', '0', '2021-08-18 16:55:00', 'comment test', 'security test', 'statusTest', 'trader test', 'book test', 'cn test', '2021-08-18 12:55:00', 'rn test', '2021-08-18 12:55:00', 'dn test', 'dt test', 'sl test', 'side test');

INSERT INTO `poseidon_test`.`Trade` (`TradeId`, `account`, `type`, `buyQuantity`, `sellQuantity`, `buyPrice`, `sellPrice`, `tradeDate`, `security`, `status`, `creationDate`, `revisionDate`) VALUES ('1', 'account1', 'type1', '1.1', '0', '111.11', '0', '2021-08-22T17:53', 'security1', 'status1', '2021-08-22T17:53', '2021-08-22T17:53');
INSERT INTO `poseidon_test`.`Trade` (`TradeId`, `account`, `type`, `buyQuantity`, `sellQuantity`, `buyPrice`, `sellPrice`, `tradeDate`, `security`, `status`, `creationDate`, `revisionDate`) VALUES ('2', 'account2', 'type2', '2.1', '0', '222.11', '0', '2020-08-22T17:53', 'security2', 'status2', '2021-08-22T17:53', '2021-08-22T17:53');
INSERT INTO `poseidon_test`.`Trade` (`TradeId`, `account`, `type`, `buyQuantity`, `sellQuantity`, `buyPrice`, `sellPrice`, `tradeDate`, `security`, `status`, `creationDate`, `revisionDate`) VALUES ('3', 'account3', 'type3', '1.333', '0', '111.11', '0', '2021-08-22T17:53', 'security3', 'status3', '2021-08-22T17:53', '2021-08-22T17:53');

INSERT INTO `poseidon_test`.`CurvePoint` (`Id`, `CurveId`, `asOfDate`, `term`, `value`, `creationDate`) VALUES ('1', '1', '2021-06-16 10:08:56', '1.1', '1.1', '2021-08-23 10:41:00');
INSERT INTO `poseidon_test`.`CurvePoint` (`Id`, `CurveId`, `asOfDate`, `term`, `value`, `creationDate`) VALUES ('2', '2', '2021-06-17 10:08:56', '2.1', '22222.1', '2021-08-23 10:41:00');
INSERT INTO `poseidon_test`.`CurvePoint` (`Id`, `CurveId`, `asOfDate`, `term`, `value`, `creationDate`) VALUES ('3', '3', '2021-06-22 10:08:56', '1.1353', '3333.331', '2021-08-23 10:41:00');



# Create user
CREATE USER IF NOT EXISTS 'poseidon_test'@'localhost' IDENTIFIED BY 'poseidon-test';

# Grant privileges on schema

GRANT ALL PRIVILEGES ON poseidon_test.* TO 'poseidon_test'@'localhost';
FLUSH PRIVILEGES;

