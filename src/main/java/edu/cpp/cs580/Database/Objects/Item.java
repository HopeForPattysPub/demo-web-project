package edu.cpp.cs580.Database.Objects;

import java.math.BigInteger;

public interface Item {
	public BigInteger getItemID();
	public String getSystem();
	public String getTitle();
	public void setItemID(BigInteger id);
	public void setSystem(String sys);
	public void setTitle(String tle);
}
