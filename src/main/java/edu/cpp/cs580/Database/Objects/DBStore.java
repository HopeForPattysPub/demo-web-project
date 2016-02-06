package edu.cpp.cs580.Database.Objects;

import edu.cpp.cs580.Database.Objects.Interfaces.Store;

public class DBStore implements Store {
	/******************Data Members********************/
	private int storeID;
	private String storeName;
	private String webPage;
	
	/******************Constructors*******************/
	/**
	 * Creates a default user with nothing initialized.
	 */
	public DBStore() {
		storeID = 0;
		storeName = null;
		webPage = null;
	}
	
	/**
	 * Constructor which initializes data members with the given parameters
	 * @param id	Store ID
	 * @param name	Store name
	 * @param url	Store URL
	 */
	public DBStore(int id, String name, String url) {
		storeID = id;
		storeName = name;
		webPage = url;
	}
	/******************Methods************************/
	@Override
	public int getStoreID() {
		return storeID;
	}

	@Override
	public String getStoreName() {
		return storeName;
	}

	@Override
	public String getURL() {
		return webPage;
	}

	@Override
	public void setStoreID(int id) {
		storeID = id;
	}

	@Override
	public void setStoreName(String name) {
		storeName = name;
	}

	@Override
	public void setURL(String url) {
		webPage = url;
	}

}
