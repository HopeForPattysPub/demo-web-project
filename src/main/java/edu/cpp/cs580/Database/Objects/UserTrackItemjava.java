package edu.cpp.cs580.Database.Objects;

import java.sql.Timestamp;



public class UserTrackItemjava {
	/******************Data Members********************/
	
	private double price;
	private Timestamp priceDate;
	private String url;
	private String storeName;
	private String title;
	private String system;
	private double notifyPrice;
	private long itemID;
	private String headerImageURL;
	
	/******************Constructors*******************/
	/**
	 * Creates a default user with nothing initialized.
	 */
	public UserTrackItemjava() {
		priceDate = null;
		url = null;
		storeName = null;
		price = 0;
		title = null;
		system = null;
		notifyPrice = 0;
		itemID = 0;
		headerImageURL = null;
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
	public UserTrackItemjava(long itemID, double price, Timestamp priceDate,	String url, String storeName, String title, String system, double notifyPrice)
	{
		this.itemID = itemID;
		this.priceDate = priceDate;
		this.url = url;
		this.storeName = storeName;
		this.price = price;
		this.title = title;
		this.system = system;
		this.priceDate = priceDate;
		this.notifyPrice = notifyPrice;
	}
	
	public UserTrackItemjava(long itemID, double price, Timestamp priceDate,	String url, String storeName, String title, String system, double notifyPrice, String headerImageURL)
	{
		this.itemID = itemID;
		this.priceDate = priceDate;
		this.url = url;
		this.storeName = storeName;
		this.price = price;
		this.title = title;
		this.system = system;
		this.priceDate = priceDate;
		this.notifyPrice = notifyPrice;
		this.headerImageURL = headerImageURL;
	}
	
	public long getItemID()
	{
		return this.itemID;
	}
	
	public void SetItemID(long itemID)
	{
		this.itemID = itemID;
	}
	
	public void SetPrice(double price)
	{
		this.price = price;
	}
	
	public void SetURL(String url)
	{
		this.url = url;
	}
	
	public void SetTitle(String title)
	{
		this.title = title;
	}
	public void SetSystem(String system)
	{
		this.system = system;
	}
	public void SetNotifyPrice(double notifyPrice)
	{
		this.notifyPrice = notifyPrice;
	}
	
	public double getPrice()
	{
		return this.price;
	}
	
	public void setHeaderImageURL(String url)
	{
		this.headerImageURL = url;
	}
	
	public String getHeaderImageURL()
	{
		return this.headerImageURL;
	}
}
