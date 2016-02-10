#Used to delete the DB
DROP DATABASE IF EXISTS AWSDB;

#Create the DB
CREATE DATABASE IF NOT EXISTS AWSDB;


#Begin AWS DB creation
USE AWSDB;

#Create the Table which holds the different types of systems (e.g PC, XBOX, etc)
CREATE TABLE Systems
(	SystemName VARCHAR(50) NOT NULL
	, SystemID VARCHAR(15) NOT NULL
    , PRIMARY KEY (SystemID)
);

#Create the table which holds the different items
CREATE TABLE Items
(	Title VARCHAR(50) NOT NULL
    , System VARCHAR(25) NOT NULL
    , ItemID BIGINT NOT NULL
    , PRIMARY KEY (ItemID)
    , FOREIGN KEY (System) REFERENCES Systems(SystemID)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

#Create the table which holds the different stores
CREATE TABLE Stores
(	StoreName VARCHAR(50) UNIQUE NOT NULL
	, StoreID INTEGER UNSIGNED NOT NULL
    , WebPage VARCHAR(255) NOT NULL
	, PRIMARY KEY (StoreID)
);

#Create the table which maps items with stores
CREATE TABLE StoreProducts
(	StoreID INTEGER UNSIGNED
    , ItemID BIGINT
    , StoreProductID VARCHAR(255) NOT NULL
    , Price	DECIMAL(10, 2) NOT NULL
    , PriceDate TIMESTAMP NOT NULL
    , URL VARCHAR(500) NOT NULL
    , PRIMARY KEY (StoreID, ItemID)
    , FOREIGN KEY (StoreID) REFERENCES Stores(StoreID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
    , FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

#Create the table for Price history
CREATE TABLE PriceHistory
(	ItemID BIGINT NOT NULL
	, StoreID INTEGER UNSIGNED NOT NULL
    , PriceDate TIMESTAMP NOT NULL
    , Price DECIMAL(10, 2) NOT NULL
    , PRIMARY KEY (ItemID, PriceDate, StoreID)
    , FOREIGN KEY (StoreID) REFERENCES Stores(StoreID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
    , FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

#Create the table for user accounts
CREATE TABLE Users
(	Username VARCHAR(20) NOT NULL
	, Email VARCHAR(100) UNIQUE NOT NULL
    , UserPassword CHAR(128) NOT NULL
    , PRIMARY KEY (Username)
);

#Table which will store user requested notifications
CREATE TABLE Notifications
(
	Username VARCHAR(20) NOT NULL
    , ItemID BIGINT NOT NULL
    , CurrentPrice DECIMAL(10, 2) NOT NULL
    , NotifyPrice DECIMAL(10, 2)
    , FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);