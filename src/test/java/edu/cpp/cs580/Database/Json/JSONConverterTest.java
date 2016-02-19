package edu.cpp.cs580.Database.Json;

import org.junit.Assert;
import org.junit.Test;

import edu.cpp.cs580.Database.JSONConverter;
import edu.cpp.cs580.Database.JSONConverter.JsonClassType;
import edu.cpp.cs580.Database.Objects.DBItem;
import edu.cpp.cs580.Database.Objects.Interfaces.Item;

public class JSONConverterTest {
	@Test
	public void convertItemTest() {
		//String json = "{\"1\":{\"ItemID\":1,\"System\":\"XBOX360\",\"Title\":\"test2\"},\"2\":{\"ItemID\":2,\"System\":\"XBOX360\",\"Title\":\"Test\"},\"3\":{\"ItemID\":3,\"System\":\"XBOX360\",\"Title\":\"Test\"},\"4\":{\"ItemID\":4,\"System\":\"XBOX360\",\"Title\":\"Test\"},\"5\":{\"ItemID\":5,\"System\":\"XBOX360\",\"Title\":\"Test\"},\"6\":{\"ItemID\":6,\"System\":\"XBOX360\",\"Title\":\"Test\"}}";
		String json = "{\"ItemID\":1,\"System\":\"XBOX360\",\"Title\":\"test2\"}";
		Item item = (DBItem)JSONConverter.jsonToObject(json, JsonClassType.ITEM);
		
		Assert.assertNotNull(item);
		System.out.println(item.getItemID());
		//System.err.println(json);
		//Assert.assertEquals(json, json2);
	}
}
