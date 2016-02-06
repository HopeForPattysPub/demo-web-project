package edu.cpp.cs580.Database.Objects.Interfaces;

import java.math.BigInteger;
import java.util.Date;

public interface StoreProduct {
	public BigInteger getItemID();
	public double getPrice();
	public Date getPriceDate();
	public int getStoreID();
	public String getStoreProductID();
	public String getURL();
	public void setItemID(BigInteger id);
	public void setPrice(double pr);
	public void setPriceDate(Date date);
	public void setStoreID(int id);
	public void setStoreProductID(String id);
	public void setURL(String ur);
}
