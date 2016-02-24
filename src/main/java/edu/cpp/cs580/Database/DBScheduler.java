package edu.cpp.cs580.Database;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import edu.cpp.cs580.Database.Objects.DBPriceHistory;
import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Queries.DBPriceHistoryQuery;
import edu.cpp.cs580.Database.Queries.DBStoreProductQuery;
import edu.cpp.cs580.Database.Queries.DBStoreQuery;
import edu.cpp.cs580.Database.Queries.Interface.PriceHistoryQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreProductQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreQuery;
import edu.cpp.cs580.webdata.parser.GameDataPage;

public class DBScheduler implements Runnable {
	/******************Data Members*******************/
	private static DBScheduler instance = null;
	private Thread thread;
	private boolean keepAlive = true;
	
	/******************Constructor********************/
	/**
	 * Default constructor which creates a new thread to run this singleton. Only one scheduler
	 * will exist.
	 */
	private DBScheduler() {
		thread = new Thread(this);
		thread.setName("Database Scheduler");
		thread.start();
	}
	
	/******************Methods************************/
	public static DBScheduler getScheduler() {
		if (instance == null)
			synchronized(instance) {
				if (instance == null)
					instance = new DBScheduler();
			}
		
		return instance;
	}
	
	public void killDBScheduler() {
		keepAlive = false;
	}
	
	@Override
	public void run() {
		while (keepAlive) {
			//Get all storeIDs
			StoreQuery sq = new DBStoreQuery();
			Map<Integer, Store> stores = sq.getAllStores();
			
			//Parse products by store
			Set<Integer> keys = stores.keySet();
			for (Integer k : keys) {
				
			}
		}
	}
	
	/******************Private Classes*****************/
	/**
	 * This class will be used to parallelize the querying of store pages and update of the
	 * Database.
	 *
	 */
	private class DBUpdate implements Runnable {
		/******************Data Members*****************/
		private int storeID;
		private GameDataPage parser;
		
		/******************Constructors*****************/
		public DBUpdate(int id, GameDataPage par) {
			storeID = id;
			parser = par;
		}
		
		/******************Methods**********************/
		@Override
		public void run() {
			//Get all store products for this store
			StoreProductQuery spq = new DBStoreProductQuery();
			Map<Long, StoreProduct> products = spq.getStoreProducts(storeID);
			
			//Parse each web page in the map
			Set<Long> keys = products.keySet();
			for (Long k : keys) {
				StoreProduct prod = products.get(k);
				parser.parseWebPage(prod.getURL());
				
				//Update the Store Product
				prod.setPrice(parser.getWebPageInfo().getCurrentPrice());
				prod.setPriceDate(new Timestamp(System.currentTimeMillis()));
				boolean result = spq.updateStoreProduct(prod);
				
				//Only continue if the update to the DB was successful
				if (result)
					addHistory(prod);
			}
		}
		
		private void addHistory(StoreProduct prod) {
			//Get the most recent priceHistory
			PriceHistoryQuery phq = new DBPriceHistoryQuery();
			Date dbDate = phq.getRecentDate(storeID, prod.getItemID());
			//If the most recent history does not exist or is older than the day this thread is executed,
			//add a new row. This keeps price history to once per day.
			if (dbDate == null || dbDate.before(prod.getPriceDate()))
				phq.addHistory(new DBPriceHistory(prod.getItemID(), prod.getPrice(), dbDate, storeID));
		}
	}
}
