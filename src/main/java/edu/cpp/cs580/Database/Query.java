package edu.cpp.cs580.Database;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.*;
import edu.cpp.cs580.Database.Objects.Interfaces.System;

public interface Query {
	/**************Item Methods***************/
	/**
	 * Add an item to the Database
	 * @param systemID	System of item
	 * @param Title		Title of item
	 * @return			True if item was successfully added, false otherwise
	 */
	public boolean addItem(String systemID, String Title);
	/**
	 * Get an item.
	 * @param itemID	ItemID of item
	 * @return			An item object which holds result. NULL if not found
	 */
	public Item getItem(String itemID);
	/**
	 * Get multiple items. May be zero or more depending on query results. The systemID
	 * or title can be used.
	 * @param option	Select multiple items by a given systemID or a Title
	 * @return			A map object storing the results. The key is the itemID.
	 */
	public Map<String, Item> getItems(String option);
	/**
	 * Remove an item from the Database
	 * @param itemID	ItemID of the item
	 * @return			True if removal is successful. False otherwise.
	 */
	public boolean removeItem(String itemID);
	/**
	 * Update an item. Only the systemID and Title can be updated.
	 * @param itemID	ItemID of object to update.
	 * @param systemID	SystemID of item (new or old).
	 * @param Title		Title of item (new or old).
	 * @return			True if successful, false otherwise.
	 */
	public boolean updateItem(String itemID, String systemID, String Title);
	
	/**************Notification Methods***************/
	/**
	 * Adds a notification for the user on the given itemID. Requires the current price of the item, and at
	 * which price the user wants the notification.
	 * @param notice	Notification which will be added
	 * @return			True if addition is successful, false otherwise
	 */
	public boolean addNotification(Notification notice);
	/**
	 * Retrieves a notification for a given user and item. This is used to retrieve a single notification.
	 * @param username	Username of user
	 * @param itemID	ItemID of item
	 * @return			Notification object or NULL if none is found
	 */
	public Notification getNotice(String username, BigInteger itemID);
	/**
	 * Get multiple notifications for a given user. May be zero or more depending on query results.
	 * @param username	Username of user
	 * @return			A map with all notifications for a single user. Key will be itemID
	 */
	public Map<BigInteger, Notification> getNotifications(String username);
	/**
	 * Get multiple notifications for a given item. May be zero or more depending on query results.
	 * @param itemID	ItemID of item
	 * @return			A map with all notifications for a single item. Key will be username.
	 */
	public Map<String, Notification> getNotifications(BigInteger itemID);
	/**
	 * Gets a list of all notifications which are ready to be sent out. These notifications are at or
	 * below their notify price.
	 * @return			List of all notifications ready to be sent out.
	 */
	public List<Notification> getFulfilledNotifications();
	/**
	 * Removes a single notification for a given username and itemID.
	 * @param username	Username of user
	 * @param itemID	ItemID of item
	 * @return			True if removal is successful, false otherwise.
	 */
	public boolean removeNotification(String username, BigInteger itemID);
	/**
	 * Updates a given notification. Only the current price and notification price may be updated.
	 * @param notice	Notification which will be updated
	 * @return			True if update is successful, false otherwise.
	 */
	public boolean updateNotification(Notification notice);
	
	/**************Price History Methods***************/
	/**
	 * Adds a price history for the given storeID and itemID
	 * @param newHistory	PriceHistory object which will be added.
	 * @return				True if addition is successful, false otherwise.
	 */
	public boolean addHistory(PriceHistory newHistory);
	/**
	 * Gets the history of an item at the given storeID. The resulting list will be in order from
	 * most recent to oldest.
	 * @param storeID	StoreID of item
	 * @param itemID	ItemID of item
	 * @return			List of prices from most recent to oldest.
	 */
	public List<PriceHistory> getHistory(int storeID, BigInteger itemID);
	
	/**************Store Methods***************/
	/**
	 * Add a new store to the database. The name must be unique.
	 * @param name	Name of store
	 * @param url	URL for store
	 * @return		True if store is added successfully, false otherwise
	 */
	public boolean addStore(String name, String url);
	/**
	 * Get the store from the given storeID
	 * @param storeID	StoreID for store
	 * @return			Store object or NULL if none exists
	 */
	public Store getStore(int storeID);
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
	public boolean updateStore(int storeID);
	
	/**************Store Product Methods***************/
	/**
	 * Add a new product with the given storeID and itemID.
	 * @param newProduct	New product to add
	 * @return				True if addition is successful, false otherwise
	 */
	public boolean addStoreProduct(StoreProduct newProduct);
	/**
	 * Retrieves a single product for a given store.
	 * @param storeID	StoreID for store
	 * @param itemID	ItemID for store product
	 * @return			StoreProduct object of result or NULL if none exists
	 */
	public StoreProduct getSingleProduct(int storeID, BigInteger itemID);
	/**
	 * Get multiple products for the given storeID. May be zero or more depending on result
	 * @param storeID	StoreID of store
	 * @return			Map of store products. Key is itemID
	 */
	public Map<BigInteger, StoreProduct> getStoreProducts(int storeID);
	/**
	 * Given an itemID, this will retrieve all StoreProducts associated with this item. May be
	 * zero or more depending on results.
	 * @param itemID	ItemID of item
	 * @return			Map of store products. Key is storeID
	 */
	public Map<Integer, StoreProduct> getStoreProducts(BigInteger itemID);
	/**
	 * Remove the store product associated with the storeID and itemID
	 * @param storeID	StoreID of store
	 * @param itemID	ItemID of item
	 * @return			True if removal is successful, false otherwise
	 */
	public boolean removeStoreProduct(int storeID, BigInteger itemID);
	/**
	 * Update the given store product. The only fields not able to be updated are StoreID and
	 * itemID
	 * @param product	Product to update
	 * @return			True if update is successful, false otherwise.
	 */
	public boolean updateStoreProduct(StoreProduct product);
	
	/**************System Methods***************/
	/**
	 * Add a System to the database. SystemID must be unique.
	 * @param system	System to add
	 * @return			True if addition is successful, false otherwise
	 */
	public boolean addSystem(System system);
	/**
	 * Get system from the given systemID.
	 * @param systemID	SystemID of system
	 * @return			System object or NULL if not found
	 */
	public System getSystem(String systemID);
	/**
	 * Remove the system from the given systemID
	 * @param systemID	SystemID of system
	 * @return			True if removal is successful, false otherwise
	 */
	public boolean removeSystem(String systemID);
	/**
	 * Update system from the given systemID. Only updates the system name.
	 * @param system	System to update
	 * @return			True if update is successful, false otherwise
	 */
	public boolean updateSystem(System system);
	
	/**************User Methods***************/
	/**
	 * Add a user to the Database. Username and email must be unique, and the password is already
	 * hashed.
	 * @param newUser	New user which will be added
	 * @return			True if new user is added, false otherwise.
	 */
	public boolean addUser(User newUser);
	/**
	 * Get the user given a username or email. Should use email whenever possible.
	 * @param option	Username or email of user to retrieve.
	 * @return			User object with result. NULL if no user found.
	 */
	public User getUser(String option);
	/**
	 * Verify and validate a user.
	 * @param user		Username of user
	 * @param password	Hashed password to be used for comparison
	 * @return			True if username and password match, false otherwise.
	 */
	public boolean loginUser(String user, String password);
	/**
	 * Remove a user. To ensure some security, only the username can be used.
	 * @param user	Username of user to remove
	 * @return		True if removal is successful, false otherwise.
	 */
	public boolean removeUser(String user);
	/**
	 * Update a user. Only the email and/or password can be updated. If the new email must still
	 * be unique, and the password must be hashed.
	 * @param user	User object which will be updated
	 * @return		True if update is successful, false otherwise.
	 */
	public boolean updateUser(User user);
}
