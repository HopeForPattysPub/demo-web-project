package edu.cpp.cs580.Database.Objects.Interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Notification {
	@JsonProperty("CurrentPrice")
	public double getCurrentPrice();
	@JsonProperty("ItemID")
	public long getItemID();
	@JsonProperty("NotifyPrice")
	public double getNotifyPrice();
	@JsonProperty("Username")
	public String getUsername();
	@JsonProperty("CurrentPrice")
	public void setCurrentPrice(double price);
	@JsonProperty("ItemID")
	public void setItemID(long id);
	@JsonProperty("NotifyPrice")
	public void setNotifyPrice(double price);
	@JsonProperty("Username")
	public void setUsername(String name);
}
