package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Queries.DBStoreQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreQuery;
import org.junit.Assert;

public class DBStoreQueryTestDisabled {
	@Test
	public void addItemTest() {
		StoreQuery sq = new DBStoreQuery();
		
		//Add stores to the DB
		boolean result = sq.addStore("Test", "http://www.example.com");
		Assert.assertEquals(true, result);
		
		//Get the stored using the Store name
		Store store = sq.getStore("Test");
		Assert.assertEquals("Test", store.getStoreName());
		
		//Update store web page
		store.setURL("http://www.example2.com");
		result = sq.updateStore(store);
		Assert.assertEquals(true, result);
		
		//Get updated store item from store id
		store = sq.getStore(store.getStoreID());
		Assert.assertEquals("http://www.example2.com", store.getURL());
		
		//Remove store
		result = sq.removeStore(store.getStoreID());
		Assert.assertEquals(true, result);
	}
}
