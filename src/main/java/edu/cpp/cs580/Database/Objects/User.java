package edu.cpp.cs580.Database.Objects;

public interface User {
	public String getUsername();
	public String getEmail();
	public String getPassword();
	public void setEmail(String em);
	public void setPassword(String ps);
	public void setUsername(String un);
}
