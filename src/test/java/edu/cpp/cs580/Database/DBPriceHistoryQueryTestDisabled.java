package edu.cpp.cs580.Database;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBPriceHistory;
import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;
import edu.cpp.cs580.Database.Queries.DBPriceHistoryQuery;
import edu.cpp.cs580.Database.Queries.Interface.PriceHistoryQuery;
import org.junit.Assert;

public class DBPriceHistoryQueryTestDisabled {
	@Test
	public void priceHistoryTest() {
		PriceHistoryQuery phq = new DBPriceHistoryQuery();
		
		//Add a price history row for a store and item
		PriceHistory history = new DBPriceHistory(1, 50.00, new Date(System.currentTimeMillis()), 1);
		boolean result = phq.addHistory(history);
		Assert.assertEquals(true, result);
		
		//List all history for an itemID in storeId
		List<PriceHistory> list = phq.getHistory(1, 1);
		Assert.assertEquals(1, list.get(0).getStoreID());
	}
}
