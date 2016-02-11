package edu.cpp.cs580.Database.Objects;

import edu.cpp.cs580.Database.Objects.Interfaces.Systems;

public class DBSystem implements Systems {
	/******************Data Members********************/
	private String systemID;
	private String systemName;
	
	/******************Constructors*******************/
	/**
	 * Creates a default user with nothing initialized.
	 */
	public DBSystem() {
		systemID = null;
		systemName = null;
	}
	
	/**
	 * Constructor which initializes data members to the given parameters
	 * @param id	System ID
	 * @param name	System Name
	 */
	public DBSystem(String id, String name) {
		systemID = id;
		systemName = name;
	}
	/******************Methods************************/
	@Override
	public String getSystemID() {
		return systemID;
	}

	@Override
	public String getSystemName() {
		return systemName;
	}

	@Override
	public void setSystemID(String id) {
		systemID = id;
	}

	@Override
	public void setSystemName(String name) {
		systemName = name;
	}

}
