package edu.cpp.cs580.Database.Objects;

import edu.cpp.cs580.Database.Objects.Interfaces.Notification;

public class DBNotification implements Notification {
	/******************Data Members********************/
	private long itemID;
	private double notifyPrice;
	private String username;
	
	/******************Constructors*******************/
	/**
	 * Default constructor which sets data members to 0 and NULL.
	 */
	public DBNotification() {
		itemID = 0;
		notifyPrice = 0;
		username = null;
	}
	
	/**
	 * Constructor which initializes data members to given parameters
	 * @param id	Item ID
	 * @param np	Item price before sending notification
	 * @param name	Username of user which created notification
	 */
	public DBNotification(long id, double np, String name) {
		itemID = id;
		notifyPrice = np;
		username = name;
	}
	/******************Methods************************/
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
