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
    , PRIMARY KEY (Username, ItemID)
    , FOREIGN KEY (Username) REFERENCES Users(Username)
		ON DELETE CASCADE
        ON UPDATE CASCADE
    , FOREIGN KEY (ItemID) REFERENCES Items(ItemID)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

DELIMITER $$
CREATE FUNCTION levenshtein( s1 VARCHAR(255), s2 VARCHAR(255) )
RETURNS INT
DETERMINISTIC
BEGIN
DECLARE s1_len, s2_len, i, j, c, c_temp, cost INT;
DECLARE s1_char CHAR;
-- max strlen=255
DECLARE cv0, cv1 VARBINARY(256);
SET s1_len = CHAR_LENGTH(s1), s2_len = CHAR_LENGTH(s2), cv1 = 0x00, j = 1, i = 1, c = 0;
IF s1 = s2 THEN
RETURN 0;
ELSEIF s1_len = 0 THEN
RETURN s2_len;
ELSEIF s2_len = 0 THEN
RETURN s1_len;
ELSE
WHILE j <= s2_len DO
SET cv1 = CONCAT(cv1, UNHEX(HEX(j))), j = j + 1;
END WHILE;
WHILE i <= s1_len DO
SET s1_char = SUBSTRING(s1, i, 1), c = i, cv0 = UNHEX(HEX(i)), j = 1;
WHILE j <= s2_len DO
SET c = c + 1;
IF s1_char = SUBSTRING(s2, j, 1) THEN
SET cost = 0; ELSE SET cost = 1;
END IF;
SET c_temp = CONV(HEX(SUBSTRING(cv1, j, 1)), 16, 10) + cost;
IF c > c_temp THEN SET c = c_temp; END IF;
SET c_temp = CONV(HEX(SUBSTRING(cv1, j+1, 1)), 16, 10) + 1;
IF c > c_temp THEN
SET c = c_temp;
END IF;
SET cv0 = CONCAT(cv0, UNHEX(HEX(c))), j = j + 1;
END WHILE;
SET cv1 = cv0, i = i + 1;
END WHILE;
END IF;
RETURN c;
END$$
DELIMITER ;