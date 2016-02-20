package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.Interfaces.Item;
import edu.cpp.cs580.Database.Queries.DBItemQuery;
import edu.cpp.cs580.Database.Queries.Interface.ItemQuery;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

public class DBItemQueryTestDisabled {
	@Test
	public void addItemTest() {
		//Attempt to add a new item
		ItemQuery it = new DBItemQuery();
		boolean result = it.addItem("XBOX360", "Test");
		
		Assert.assertEquals(true, result);
		
		//Attempt to add item with non-existing SystemID
		result = it.addItem("TEST", "Test");
		Assert.assertEquals(false, result);
		
		//Get single item
		//Item item = it.getItem(1);
		//Assert.assertEquals("Test", item.getTitle());
		//Assert.assertEquals("XBOX360", item.getSystem());
		
		//Get multiple items
		Map<Long, Item> map = it.getItems("Title", "Test");
		//Assert.assertEquals(1, map.size());
		
		//Search
		List<Item> list = it.searchTitle("test");
		System.err.println(list.size());
		
		//Remove item
		result = it.removeItem(1);
		Assert.assertEquals(true, result);
	}
	
/*	@Test
	public void updateItemTest() {
		ItemQuery it = new DBItemQuery();
		
		//Update item using updateItem
		boolean result = it.updateItem(1, "XBOX360", "Test Title");
		Assert.assertEquals(true, result);
	}*/
}
