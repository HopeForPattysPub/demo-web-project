package edu.cpp.cs580.Database.Objects.Interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Item {
	@JsonProperty("ItemID")
	public long getItemID();
	@JsonProperty("System")
	public String getSystem();
	@JsonProperty("Title")
	public String getTitle();
	@JsonProperty("ItemID")
	public void setItemID(long id);
	@JsonProperty("System")
	public void setSystem(String sys);
	@JsonProperty("Title")
	public void setTitle(String tle);
}
