DROP DATABASE IF EXISTS AuctionWebApp ;    -- Delete database
DROP USER IF EXISTS 'app_user'@'localhost';       -- Delete custom user

CREATE DATABASE  IF NOT EXISTS AuctionWebApp;
USE AuctionWebApp;

CREATE USER 'app_user'@'localhost' IDENTIFIED BY 'yz@utz98JxZpa';
GRANT ALL PRIVILEGES ON `AuctionWebApp`.* TO 'app_user'@'localhost';

CREATE TABLE locations (
  id int(10) NOT NULL AUTO_INCREMENT,
  location varchar(30) NOT NULL,
  latitude float(12,10) DEFAULT NULL,
  longitude float(13,10) DEFAULT NULL,
  country varchar(30) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE categories (
  id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(30) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO categories VALUES (1, 'Clothes');
INSERT INTO categories VALUES (2, 'Computer');
INSERT INTO categories VALUES (3, 'Technology');
INSERT INTO categories VALUES (4, 'Hobby');
INSERT INTO categories VALUES (5, 'Music');
INSERT INTO categories VALUES (6, 'Shoes');
INSERT INTO categories VALUES (7, 'Beauty');
INSERT INTO categories VALUES (8, 'Man');
INSERT INTO categories VALUES (9, 'Woman');
INSERT INTO categories VALUES (10, 'Cooking');
INSERT INTO categories VALUES (11, 'Kids');
INSERT INTO categories VALUES (12, 'Video Games');
INSERT INTO categories VALUES (13, 'Toys');
INSERT INTO categories VALUES (14, 'Pet');
INSERT INTO categories VALUES (15, 'Sport');

CREATE TABLE users (
  id int(10) NOT NULL AUTO_INCREMENT,
  public_id varchar(30) NOT NULL,
  role varchar(9) NOT NULL,
  username varchar(15) NOT NULL,
  password char(60) NOT NULL,
  fname varchar(15) NOT NULL,
  lname varchar(15) NOT NULL,
  location_id int(10) DEFAULT NULL,
  email varchar(25) NOT NULL,
  phone char(10) NOT NULL,
  afm char(11) NOT NULL,
  verified tinyint(1) NOT NULL DEFAULT '0',
  buyer_rating smallint(10) DEFAULT '0',
  seller_rating smallint(10) DEFAULT '0',
  PRIMARY KEY (id,public_id),
  UNIQUE KEY username (username),
  UNIQUE KEY public_id (public_id),
  FOREIGN KEY (location_id) REFERENCES locations (id)
);

INSERT INTO users VALUES (1,'1LWlyyBUSubA0QnxPpbTpIQk3XTlyn','ADMIN','admin','$2a$10$gowLqce06XjzsoXfyeCwu.XcUPtlB8RarJBCRIW.yznutz98JxZpa','admin','admin',NULL,'admin@auction.gr','0','0',1,0,0);


CREATE TABLE items (
  id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  description varchar(500) NOT NULL,
  first_bid float(10,2) NOT NULL,
  current_bid float(10,2) DEFAULT NULL,
  auction_start datetime NOT NULL,
  auction_end datetime NOT NULL,
  seller_id varchar(30) NOT NULL,
  buyer_id varchar(30) DEFAULT NULL,
  buy_price float(10,2) DEFAULT NULL,
  buy_offer float(10,2) DEFAULT NULL,
  no_bids int(10) DEFAULT '0',
  location_id int(10) DEFAULT NULL,
  ended tinyint(1) DEFAULT NULL,
  active tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (buyer_id) REFERENCES users (public_id),
  FOREIGN KEY (seller_id) REFERENCES users (public_id),
  FOREIGN KEY (location_id) REFERENCES locations (id)
);

CREATE TABLE item_category (
  item_id int(10) NOT NULL,
  category_id int(10) NOT NULL,
  PRIMARY KEY (item_id,category_id),
  FOREIGN KEY (item_id) REFERENCES items (id),
  FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE photos (
  id int(10) NOT NULL AUTO_INCREMENT,
  item_id int(10) NOT NULL,
  name varchar(100) NOT NULL,
  content longblob NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (item_id) REFERENCES items (id)
);

CREATE TABLE bids (
  id int(10) NOT NULL AUTO_INCREMENT,
  item_id int(10) NOT NULL,
  bidder_id varchar(30) NOT NULL,
  amount float(10,2) NOT NULL,
  bid_time datetime NOT NULL,
  win_status varchar(3) NOT NULL DEFAULT 'NO',
  PRIMARY KEY (id),
  FOREIGN KEY (item_id) REFERENCES items (id),
  FOREIGN KEY (bidder_id) REFERENCES users (public_id)
);

CREATE TABLE ratings (
  id int(10) NOT NULL AUTO_INCREMENT,
  rated varchar(30) DEFAULT NULL,
  was_rated varchar(30) DEFAULT NULL,
  rating smallint(10) DEFAULT '0',
  PRIMARY KEY (id),
  FOREIGN KEY (was_rated) REFERENCES users (public_id),
  FOREIGN KEY (rated) REFERENCES users (public_id)
);

CREATE TABLE messages (
  id int(10) NOT NULL AUTO_INCREMENT,
  from_user varchar(30) NOT NULL,
  from_role char(6) NOT NULL,
  to_user varchar(30) NOT NULL,
  date_time datetime NOT NULL,
  message varchar(200) DEFAULT NULL,
  seen tinyint(1) NOT NULL,
  deleted_from tinyint(1) DEFAULT NULL,
  deleted_to tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (from_user) REFERENCES users (public_id),
  FOREIGN KEY (to_user) REFERENCES users (public_id)
);

-- Search puproses
ALTER TABLE items ADD FULLTEXT (name);
ALTER TABLE items ADD FULLTEXT (description);
ALTER TABLE categories ADD FULLTEXT (name);
Alter table locations add fulltext (country);
Alter table locations add fulltext (location);
