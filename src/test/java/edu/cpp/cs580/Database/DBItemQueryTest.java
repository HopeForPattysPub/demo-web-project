package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Queries.DBItemQuery;
import edu.cpp.cs580.Database.Queries.Interface.ItemQuery;
import org.junit.Assert;

public class DBItemQueryTest {
	@Test
	public void addItemTest() {
		ItemQuery it = new DBItemQuery();
		boolean result = it.addItem("XBOX360", "Test");
		
		Assert.assertEquals(true, result);
	}
}
