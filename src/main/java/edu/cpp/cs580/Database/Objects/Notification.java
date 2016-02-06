package edu.cpp.cs580.Database.Objects;

import java.math.BigInteger;

public interface Notification {
	public double getCurrentPrice();
	public BigInteger getItemID();
	public double getNotifyPrice();
	public String getUsername();
	public void setCurrentPrice(double price);
	public void setItemID(BigInteger id);
	public void setNotifyPrice(double price);
	public void setUsername(String name);
}
