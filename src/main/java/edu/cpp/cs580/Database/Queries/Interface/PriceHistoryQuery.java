package edu.cpp.cs580.Database.Queries.Interface;

import java.sql.Date;
import java.util.List;

import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;

public interface PriceHistoryQuery {
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
	public List<PriceHistory> getHistory(int storeID, long itemID);
	
	/**
	 * Get the most recent date/time for the given StoreID and ItemID
	 * @param storeID	StoreID of store
	 * @param itemID	ItemID of item
	 * @return			Most recent date for given store item
	 */
	public Date getRecentDate(int storeID, long itemID);
}
