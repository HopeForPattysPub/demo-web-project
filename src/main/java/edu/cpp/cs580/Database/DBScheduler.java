package edu.cpp.cs580.Database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cpp.cs580.Database.Objects.DBPriceHistory;
import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Queries.DBNotificationQuery;
import edu.cpp.cs580.Database.Queries.DBPriceHistoryQuery;
import edu.cpp.cs580.Database.Queries.DBStoreProductQuery;
import edu.cpp.cs580.Database.Queries.DBStoreQuery;
import edu.cpp.cs580.Database.Queries.Interface.NotificationQuery;
import edu.cpp.cs580.Database.Queries.Interface.PriceHistoryQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreProductQuery;
import edu.cpp.cs580.Database.Queries.Interface.StoreQuery;
import edu.cpp.cs580.webdata.parser.GameDataPage;

public class DBScheduler implements Runnable {
	/******************Data Members*******************/
	private static DBScheduler instance = null;
	private Thread thread;
	private boolean keepAlive = true;
	private final long RUN_TIME = 12 * 60 * 60 * 1000;	//12 hours
	
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
			synchronized(DBScheduler.class) {
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
			//Run this thread every 12 hours
			try {
				Thread.sleep(RUN_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Get all storeIDs
			StoreQuery sq = new DBStoreQuery();
			Map<Integer, Store> stores = sq.getAllStores();
			
			//Parse products by store
			Set<Integer> keys = stores.keySet();
			List<Thread> updates = new ArrayList<Thread>();
			for (Integer k : keys) {
				//Create the DataPage from the class name column, and then create thread
				try {
					Class<GameDataPage> dataPageName = (Class<GameDataPage>)Class.forName(stores.get(k).getClassName());
					Constructor<GameDataPage> constructor = dataPageName.getConstructor(String.class);
					Thread newThread = new Thread(new DBUpdate(stores.get(k).getStoreID(), constructor));
					newThread.setName(stores.get(k).getClassName() + " Store Update");
					newThread.start();
					updates.add(newThread);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
			}
			
			boolean updateDone = false;
			while (!updateDone) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				for (Thread t : updates) {
					//If any update thread is still alive, break out of this for loop
					if (t.isAlive())
						break;
					//This will only be reached if no threads are alive
					updateDone = true;
				}
			}
			
			//Get fulfilled notifications
			NotificationQuery nq = new DBNotificationQuery();
			List<Notification> notes = nq.getFulfilledNotifications();
			//TODO: Send notifications
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
		private Constructor<GameDataPage> parser;
		
		/******************Constructors*****************/
		public DBUpdate(int id, Constructor<GameDataPage> par) {
			storeID = id;
			parser = par;
		}
		
		/******************Methods**********************/
		@Override
		public void run() {
			System.err.println("In Thread: " + storeID);
			//Get all store products for this store
			StoreProductQuery spq = new DBStoreProductQuery();
			Map<Long, StoreProduct> products = spq.getStoreProducts(storeID);
			
			//Parse each web page in the map
			Set<Long> keys = products.keySet();
			for (Long k : keys) {
				StoreProduct prod = products.get(k);
				try {
					GameDataPage dataPage = parser.newInstance(prod.getURL());
					
					//Update the Store Product
					prod.setPrice(dataPage.getWebPageInfo().getCurrentPrice());
					prod.setPriceDate(new Timestamp(System.currentTimeMillis()));
					boolean result = spq.updateStoreProduct(prod);
					
					//Only continue if the update to the DB was successful
					if (result)
						addHistory(prod);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		private void addHistory(StoreProduct prod) {
			//Get the most recent priceHistory
			PriceHistoryQuery phq = new DBPriceHistoryQuery();
			Date dbDate = phq.getRecentDate(storeID, prod.getItemID());
			//If the most recent history does not exist or is older than the day this thread is executed,
			//add a new row. This keeps price history to once per day.
			if (dbDate == null || dbDate.before(prod.getPriceDate()))
				phq.addHistory(new DBPriceHistory(prod.getItemID(), prod.getPrice(), new Date(prod.getPriceDate().getTime()), storeID));
		}
	}
	
//	public static void main(String args[]) {
//		DBScheduler sch = getScheduler();
//	}
}
