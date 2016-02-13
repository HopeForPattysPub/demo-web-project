package edu.cpp.cs580.Database.Objects;

import edu.cpp.cs580.Database.Objects.Interfaces.Notification;

public class DBNotification implements Notification {
	/******************Data Members********************/
	private double currentPrice;
	private long itemID;
	private double notifyPrice;
	private String username;
	
	/******************Constructors*******************/
	/**
	 * Default constructor which sets data members to 0 and NULL.
	 */
	public DBNotification() {
		currentPrice = 0;
		itemID = 0;
		notifyPrice = 0;
		username = null;
	}
	
	/**
	 * Constructor which initializes data members to given parameters
	 * @param cp	Item current price at time of creation
	 * @param id	Item ID
	 * @param np	Item price before sending notification
	 * @param name	Username of user which created notification
	 */
	public DBNotification(double cp, long id, double np, String name) {
		currentPrice = cp;
		itemID = id;
		notifyPrice = np;
		username = name;
	}
	/******************Methods************************/
	@Override
	public double getCurrentPrice() {
		return currentPrice;
	}

	@Override
	public long getItemID() {
		return itemID;
	}

	@Override
	public double getNotifyPrice() {
		return notifyPrice;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setCurrentPrice(double price) {
		currentPrice = price;
	}

	@Override
	public void setItemID(long id) {
		itemID = id;
	}

	@Override
	public void setNotifyPrice(double price) {
		notifyPrice = price;
	}

	@Override
	public void setUsername(String name) {
		username = name;
	}

}