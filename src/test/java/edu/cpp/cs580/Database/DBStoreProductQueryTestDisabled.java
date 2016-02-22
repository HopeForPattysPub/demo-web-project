package edu.cpp.cs580.Database;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBStoreProduct;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Queries.DBStoreProductQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreProductQuery;
import org.junit.Assert;

public class DBStoreProductQueryTestDisabled {
	@Test
	public void storeProductQueryTest() {
		StoreProductQuery spq = new DBStoreProductQuery();
		
		//Add item
		StoreProduct product = new DBStoreProduct((long)1, 50.00, new Timestamp(System.currentTimeMillis()), 1, "1", "http://example.com/item");
		boolean result = spq.addStoreProduct(product);
		Assert.assertEquals(true, result);
		
		//Get single item
		product = spq.getSingleProduct(1, 1);
		Assert.assertEquals("http://example.com/item", product.getURL());
		
		//Get all product for a store using store id
		Map<Long, StoreProduct> map = spq.getStoreProducts(1);
		Assert.assertEquals("1", map.get((long)1).getStoreProductID());
		
		//Get all store products related to an itemId
		Map<Integer, StoreProduct> map2 = spq.getItemIDProducts(1);
		Assert.assertEquals(1, map2.get(1).getStoreID());
		
		//Update a store product
		product = new DBStoreProduct((long)1, 20.00, new Timestamp(System.currentTimeMillis()), 1, "1", "http://example.com/item");
		result = spq.updateStoreProduct(product);
		Assert.assertEquals(true, result);
		
		//Remove store product
		result = spq.removeStoreProduct(1, 1);
		Assert.assertEquals(true, result);
	}
}
