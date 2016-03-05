package edu.cpp.cs580.Database.Queries.Interface;

import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;

public interface StoreProductQuery {
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
	public StoreProduct getSingleProduct(int storeID, long itemID);
	/**
	 * Retrieves a store product from a given store, given the store product ID
	 * @param storeID			Store ID
	 * @param storeProductID	Store product ID for item
	 * @return					Single store product or NULL if none exists.
	 */
	public StoreProduct getSingleProduct(int storeID, String storeProductID);
	/**
	 * Get multiple products for the given storeID. May be zero or more depending on result
	 * @param storeID	StoreID of store
	 * @return			Map of store products. Key is itemID
	 */
	public Map<Long, StoreProduct> getStoreProducts(int storeID);
	/**
	 * Given an itemID, this will retrieve all StoreProducts associated with this item. May be
	 * zero or more depending on results.
	 * @param itemID	ItemID of item
	 * @return			Map of store products. Key is storeID
	 */
	public Map<Integer, StoreProduct> getItemIDProducts(long itemID);
	/**
	 * Remove the store product associated with the storeID and itemID
	 * @param storeID	StoreID of store
	 * @param itemID	ItemID of item
	 * @return			True if removal is successful, false otherwise
	 */
	public boolean removeStoreProduct(int storeID, long itemID);
	/**
	 * Update the given store product. The only fields not able to be updated are StoreID and
	 * itemID
	 * @param product	Product to update
	 * @return			True if update is successful, false otherwise.
	 */
	public boolean updateStoreProduct(StoreProduct product);
}
