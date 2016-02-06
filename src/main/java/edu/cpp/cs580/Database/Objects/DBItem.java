package edu.cpp.cs580.Database.Objects;

import java.math.BigInteger;

import edu.cpp.cs580.Database.Objects.Interfaces.Item;

public class DBItem implements Item {
	/******************Data Members*******************/
	private BigInteger itemID;
	private String system;
	private String title;
	
	/******************Constructors*******************/
	/**
	 * Default constructor. Will initialize all data members to NULL
	 */
	public DBItem() {
		itemID = null;
		system = null;
		title = null;
	}
	
	/**
	 * Constructor which initializes all data members to the given parameters.
	 * @param id	Item id
	 * @param sys	Item system
	 * @param tle	Item title
	 */
	public DBItem(BigInteger id, String sys, String tle) {
		itemID = id;
		system = sys;
		title = tle;
	}
	
	/******************Methods************************/
	@Override
	public BigInteger getItemID() {
		return itemID;
	}

	@Override
	public String getSystem() {
		return system;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setItemID(BigInteger id) {
		itemID = id;
	}

	@Override
	public void setSystem(String sys) {
		system = sys;
	}

	@Override
	public void setTitle(String tle) {
		title = tle;
	}

}
