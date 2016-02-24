package edu.cpp.cs580.Database.Queries.Interface;

import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.Store;

public interface StoreQuery {
	/**
	 * Add a new store to the database. The name must be unique.
	 * @param name	Name of store
	 * @param url	URL for store
	 * @param className	GameDataPage sub-class name which handles this store 
	 *                  (edu.cpp.cs580.webdata.parser.SteamJSONDataPage).
	 * @return		True if store is added successfully, false otherwise
	 */
	public boolean addStore(String name, String url, String className);
	/**
	 * Get all stores in the Database.
	 * @return	Map of all stores.
	 */
	public Map<Integer, Store> getAllStores();
	/**
	 * Get the store from the given storeID
	 * @param storeID	StoreID for store
	 * @return			Store object or NULL if none exists
	 */
	public Store getStore(int storeID);
	/**
	 * Get the store from the given store name
	 * @param storeName	Store name
	 * @return			Store object or NULL if none exists
	 */
	public Store getStore(String storeName);
	/**
	 * Remove the given store
	 * @param storeID	StoreID of store
	 * @return			True if removal is successful, false otherwise
	 */
	public boolean removeStore(int storeID);
	/**
	 * Updates the name and/or URL of a given store. 
	 * @param storeID	StoreID of store
	 * @return			True if update is successful, false otherwise
	 */
	public boolean updateStore(Store store);
}
