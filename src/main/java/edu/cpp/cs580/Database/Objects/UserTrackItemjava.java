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
	public UserTrackItemjava(double price, Timestamp priceDate,	String url, String storeName, String title, String system, double notifyPrice)
	{
		this.priceDate = priceDate;
		this.url = url;
		this.storeName = storeName;
		this.price = price;
		this.title = title;
		this.system = system;
		this.priceDate = priceDate;
		this.notifyPrice = notifyPrice;
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
}
