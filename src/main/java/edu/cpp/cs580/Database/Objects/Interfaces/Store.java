package edu.cpp.cs580.Database.Objects.Interfaces;

public interface Store {
	public int getStoreID();
	public String getStoreName();
	public String getURL();
	public void setStoreID(int id);
	public void setStoreName(String name);
	public void setURL(String url);
}
