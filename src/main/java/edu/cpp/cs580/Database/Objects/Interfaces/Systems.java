package edu.cpp.cs580.Database.Objects.Interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Systems {
	@JsonProperty("SystemID")
	public String getSystemID();
	@JsonProperty("SystemName")
	public String getSystemName();
	@JsonProperty("SystemID")
	public void setSystemID(String id);
	@JsonProperty("SystemName")
	public void setSystemName(String name);
}
