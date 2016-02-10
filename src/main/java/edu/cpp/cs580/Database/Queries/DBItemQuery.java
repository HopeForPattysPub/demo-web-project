package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBItem;
import edu.cpp.cs580.Database.Objects.Interfaces.Item;
import edu.cpp.cs580.Database.Queries.Interface.ItemQuery;

public class DBItemQuery implements ItemQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addItem(String systemID, String title) {
		Connection connect = pool.getConnection();
		String insertQuery = "INSERT INTO awsdb.items(Title, System, ItemID) VALUES(?,?,?)";
		boolean result = true;
		
		try {
			long itemID = calculateItemID();
			
			PreparedStatement stmt = connect.prepareStatement(insertQuery);
			stmt.setString(1, title);
			stmt.setString(2, systemID);
			stmt.setLong(3, itemID);
			int queryResult = stmt.executeUpdate();
			
			//No updates where performed. More than like system does not exist.
			//Foreign Key failure.
			if (queryResult == 0)
				result = false;
			
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Item getItem(long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Items WHERE ItemID = ?";
		Item result = null;
		
		try {
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setLong(1, itemID);
			ResultSet queryResult = stmt.executeQuery();
			
			//ItemID Exists so create Item. This assumes there is only one result since
			//ItemID is Primary Key so there will only be one. Like Highlander.
			if (queryResult.next()) {
				String resTitle = queryResult.getString("Title"),
					   resSystemID = queryResult.getString("SystemID");
				long resItemID = queryResult.getLong("ItemID");
				result = new DBItem(resItemID, resSystemID, resTitle);
			}
			
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Map<Long, Item> getItems(String option) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Items WHERE ItemID = ?";
		Map<Long, Item> result = new HashMap<Long, Item>();
		
		try {
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setString(1, option);
			ResultSet queryResult = stmt.executeQuery();
			
			//There are results, so populate the map with items
			while (queryResult.next()) {
				String resTitle = queryResult.getString("Title"),
					   resSystemID = queryResult.getString("SystemID");
				long resItemID = queryResult.getLong("ItemID");
				result.put(resItemID, new DBItem(resItemID, resSystemID, resTitle));
			}
			
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean removeItem(long itemID) {
		Connection connect = pool.getConnection();
		String removeQuery = "DELETE FROM awsdb.Items WHERE ItemID = ?";
		String verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Items WHERE ItemID = ?)";
		boolean result = true;
		
		try {
			//First verify that the item exists
			PreparedStatement stmt = connect.prepareStatement(verifyQuery);
			stmt.setLong(1, itemID);
			ResultSet verifyResult = stmt.executeQuery();
			
			//verifyResult will have 1 row if the item exists, so can continue with delete
			if (verifyResult.next()) {
				stmt.close();
				
				stmt = connect.prepareStatement(removeQuery);
				stmt.setLong(1, itemID);
				stmt.executeUpdate();
			}
			else {
				verifyResult.close();
				result = false;
			}
			
			//Close resources to prevent Leaks
			verifyResult.close();
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean updateItem(long itemID, int systemID, String Title) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.Items SET SystemID = ?, Title = ? WHERE ItemID = ?";
		boolean result = true;
		
		try {
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setLong(1, itemID);
			int queryResult = stmt.executeUpdate();
			
			//No updates where performed. More than likely ItemID does not exist.
			if (queryResult == 0)
				result = false;
			
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	/**
	 * This will calculate the next highest ItemID from the highest ItemID
	 * @return	New calculated itemID
	 */
	private long calculateItemID() {
		String query = "SELECT ItemID FROM awsdb.Items ORDER BY ItemID DESC LIMIT 1";
		
		Connection connect = pool.getConnection();
		long itemID;
		try {
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			//If the set is empty (should only happen once, ever)
			if (!result.next())
				itemID = 1;
			else {
				itemID = result.getLong("ItemID");
				//Add one to itemID
				itemID += 1;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			itemID = 1;
		}
		
		//Close connection
		pool.closeConnection(connect);
		
		return itemID;
	}
}
