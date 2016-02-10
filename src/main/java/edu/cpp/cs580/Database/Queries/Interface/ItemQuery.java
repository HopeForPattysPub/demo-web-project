package edu.cpp.cs580.Database.Queries.Interface;

import java.math.BigInteger;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.Item;

public interface ItemQuery {
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
	public Item getItem(long itemID);
	/**
	 * Get multiple items. May be zero or more depending on query results. The systemID
	 * or title can be used.
	 * @param option	Select multiple items by a given systemID or a Title
	 * @return			A map object storing the results. The key is the itemID.
	 */
	public Map<Long, Item> getItems(String option);
	/**
	 * Remove an item from the Database
	 * @param itemID	ItemID of the item
	 * @return			True if removal is successful. False otherwise.
	 */
	public boolean removeItem(long itemID);
	/**
	 * Update an item. Only the systemID and Title can be updated.
	 * @param itemID	ItemID of object to update.
	 * @param systemID	SystemID of item (new or old).
	 * @param Title		Title of item (new or old).
	 * @return			True if successful, false otherwise.
	 */
	public boolean updateItem(long itemID, int systemID, String Title);
}
