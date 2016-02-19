package edu.cpp.cs580.Database.Objects.Interfaces;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PriceHistory {
	@JsonProperty("ItemID")
	public long getItemID();
	@JsonProperty("Price")
	public double getPrice();
	@JsonProperty("ItemID")
	public Date getPriceDate();
	@JsonProperty("StoreID")
	public int getStoreID();
	@JsonProperty("ItemID")
	public void setItemID(long id);
	@JsonProperty("Price")
	public void setPrice(double pr);
	@JsonProperty("PriceDate")
	public void setPriceDate(Date date);
	@JsonProperty("StoreID")
	public void setStoreID(int id);
}
