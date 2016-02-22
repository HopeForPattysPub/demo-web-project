package edu.cpp.cs580.Database.Objects.Interfaces;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StoreProduct {
	@JsonProperty("ItemID")
	public long getItemID();
	@JsonProperty("Price")
	public double getPrice();
	@JsonProperty("PriceDate")
	public Timestamp getPriceDate();
	@JsonProperty("StoreID")
	public int getStoreID();
	@JsonProperty("StoreProductID")
	public String getStoreProductID();
	@JsonProperty("URL")
	public String getURL();
	@JsonProperty("ItemID")
	public void setItemID(long id);
	@JsonProperty("Price")
	public void setPrice(double pr);
	@JsonProperty("PriceDate")
	public void setPriceDate(Timestamp date);
	@JsonProperty("StoreID")
	public void setStoreID(int id);
	@JsonProperty("StoreProductID")
	public void setStoreProductID(String id);
	@JsonProperty("URL")
	public void setURL(String ur);
}
