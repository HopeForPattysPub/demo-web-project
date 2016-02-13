package edu.cpp.cs580.Database.Objects.Interfaces;

import java.sql.Date;

public interface PriceHistory {
	public long getItemID();
	public double getPrice();
	public Date getPriceDate();
	public int getStoreID();
	public void setItemID(long id);
	public void setPrice(double pr);
	public void setPriceDate(Date date);
	public void setStoreID(int id);
}
