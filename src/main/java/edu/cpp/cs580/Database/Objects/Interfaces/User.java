package edu.cpp.cs580.Database.Objects.Interfaces;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface User {
	@JsonProperty("Username")
	public String getUsername();
	@JsonProperty("Email")
	public String getEmail();
	@JsonProperty("Password")
	public String getPassword();
	@JsonProperty("Email")
	public void setEmail(String em);
	@JsonProperty("Password")
	public void setPassword(String ps);
	@JsonProperty("Username")
	public void setUsername(String un);
}
