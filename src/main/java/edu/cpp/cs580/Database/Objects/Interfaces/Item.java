package edu.cpp.cs580.Database.Objects.Interfaces;

public interface Item {
	public long getItemID();
	public String getSystem();
	public String getTitle();
	public void setItemID(long id);
	public void setSystem(String sys);
	public void setTitle(String tle);
}
