package edu.cpp.cs580.Database.Objects;

import java.sql.Timestamp;

import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;

public class DBStoreProduct implements StoreProduct {
	/******************Data Members********************/
	private long itemID;
	private double price;
	private Timestamp priceDate;
	private int storeID;
	private String storeProductID;
	private String url;
	
	/******************Constructors*******************/
	/**
	 * Creates a default user with nothing initialized.
	 */
	public DBStoreProduct() {
		itemID = 0;
		price = 0;
		priceDate = null;
		storeID = 0;
		storeProductID = null;
		url = null;
	}
	
	/**
	 * Constructor which initializes data members with given parameters
	 * @param itID	Item ID
	 * @param pr	Price for item at this store
	 * @param pd	Date price was retrieved
	 * @param stID	Store Id
	 * @param stPrID	Product ID used by store for item
	 * @param ur	Item URL in store
	 */
	public DBStoreProduct(long itID, double pr, Timestamp pd, int stID, String stPrID, String ur) {
		itemID = itID;
		price = pr;
		priceDate = pd;
		storeID = stID;
		storeProductID = stPrID;
		url = ur;
	}
	/******************Methods************************/
	@Override
	public long getItemID() {
		return itemID;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public Timestamp getPriceDate() {
		return priceDate;
	}

	@Override
	public int getStoreID() {
		return storeID;
	}

	@Override
	public String getStoreProductID() {
		return storeProductID;
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public void setItemID(long id) {
		itemID = id;
	}

	@Override
	public void setPrice(double pr) {
		price = pr;
	}

	@Override
	public void setPriceDate(Timestamp date) {
		priceDate = date;
	}

	@Override
	public void setStoreID(int id) {
		storeID = id;
	}

	@Override
	public void setStoreProductID(String id) {
		storeProductID = id;
	}

	@Override
	public void setURL(String ur) {
		url = ur;
	}

}
