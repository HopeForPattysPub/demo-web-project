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
    , ItemID BIGINT UNSIGNED NOT NULL
    , Price	DECIMAL(10, 2) NOT NULL
    , PRIMARY KEY (ItemID)
    , FOREIGN KEY (System) REFERENCES Systems(SystemID)
);

#Create the table which holds the different stores
CREATE TABLE Stores
(	StoreName VARCHAR(50) NOT NULL
	, StoreID INTEGER UNSIGNED NOT NULL
    , WebPage VARCHAR(255) NOT NULL
	, PRIMARY KEY (StoreID)
);

#Create the table which maps items with stores
CREATE TABLE StoreProducts
(	StoreID INTEGER UNSIGNED
    , ItemID BIGINT UNSIGNED
    , URL VARCHAR(500) NOT NULL
    , PRIMARY KEY (StoreID, ItemID)
    , FOREIGN KEY (StoreID) REFERENCES Stores(StoreID)
    , FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
);