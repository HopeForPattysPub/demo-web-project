package edu.cpp.cs580.Database.Objects.Interfaces;

import java.math.BigInteger;

public interface Notification {
	public double getCurrentPrice();
	public long getItemID();
	public double getNotifyPrice();
	public String getUsername();
	public void setCurrentPrice(double price);
	public void setItemID(long id);
	public void setNotifyPrice(double price);
	public void setUsername(String name);
}
