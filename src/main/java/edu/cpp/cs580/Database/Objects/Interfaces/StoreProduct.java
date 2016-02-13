package edu.cpp.cs580.Database.Objects.Interfaces;

import java.sql.Date;

public interface StoreProduct {
	public long getItemID();
	public double getPrice();
	public Date getPriceDate();
	public int getStoreID();
	public String getStoreProductID();
	public String getURL();
	public void setItemID(long id);
	public void setPrice(double pr);
	public void setPriceDate(Date date);
	public void setStoreID(int id);
	public void setStoreProductID(String id);
	public void setURL(String ur);
}
