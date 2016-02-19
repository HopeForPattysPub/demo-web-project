package edu.cpp.cs580.Database.Objects.Interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Store {
	@JsonProperty("StoreID")
	public int getStoreID();
	@JsonProperty("StoreName")
	public String getStoreName();
	@JsonProperty("URL")
	public String getURL();
	@JsonProperty("StoreID")
	public void setStoreID(int id);
	@JsonProperty("StoreName")
	public void setStoreName(String name);
	@JsonProperty("URL")
	public void setURL(String url);
}
