package edu.cpp.cs580.Database.Objects;

import java.math.BigInteger;
import java.util.Date;

import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;

public class DBPriceHistory implements PriceHistory {
	/******************Data Members********************/
	private BigInteger itemID;
	private double price;
	private Date priceDate;
	private int storeID;
	
	/******************Constructors*******************/
	/**
	 * Default constructor which initializes data members to 0 or NULL
	 */
	public DBPriceHistory() {
		itemID = null;
		price = 0;
		priceDate = null;
		storeID = 0;
	}
	
	/**
	 * Constructor which initializes data members to the given parameters
	 * @param itID	Item ID
	 * @param pr	Price at the given date
	 * @param pd	Date price retrieved
	 * @param strID	Store ID from which price was retrieved
	 */
	public DBPriceHistory(BigInteger itID, double pr, Date pd, int strID) {
		itemID = itID;
		price = pr;
		priceDate = pd;
		storeID = strID;
	}
	/******************Methods************************/
	@Override
	public BigInteger getItemID() {
		return itemID;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public Date getPriceDate() {
		return priceDate;
	}

	@Override
	public int getStoreID() {
		return storeID;
	}

	@Override
	public void setItemID(BigInteger id) {
		itemID = id;
	}

	@Override
	public void setPrice(double pr) {
		price = pr;
	}

	@Override
	public void setPriceDate(Date date) {
		priceDate = date;
	}

	@Override
	public void setStoreID(int id) {
		storeID = id;
	}

}
