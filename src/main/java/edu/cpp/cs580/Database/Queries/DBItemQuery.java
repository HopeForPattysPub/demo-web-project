package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		
		PreparedStatement stmt = null;
		try {
			long itemID = calculateItemID();
			
			stmt = connect.prepareStatement(insertQuery);
			stmt.setString(1, title);
			stmt.setString(2, systemID);
			stmt.setLong(3, itemID);
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			//This means there was an error with executing the query, so return false.
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Item getItem(long itemID) {
		
		System.err.println("if error");
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.items WHERE ItemID = ?";
		Item result = null;
		
		try {
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setLong(1, itemID);
			
			ResultSet queryResult = stmt.executeQuery();
			
			//ItemID Exists so create Item. This assumes there is only one result since
			//ItemID is Primary Key so there will only be one. Like Highlander.
			
			if (queryResult.next()) {
				String resTitle = queryResult.getString("Title"),
					   resSystemID = queryResult.getString("System");
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
	public Map<Long, Item> getItems(String option, String value) {
		Connection connect = pool.getConnection();
		String query;
		Map<Long, Item> result = new HashMap<Long, Item>();
		
		if (option.equalsIgnoreCase("Title"))
			query = "SELECT * FROM awsdb.Items WHERE Title = ?";
		else
			query = "SELECT * FROM awsdb.Items WHERE System = ?";
		
		try {
			PreparedStatement stmt = connect.prepareStatement(query);
			stmt.setString(1, value);
			ResultSet queryResult = stmt.executeQuery();
			
			//There are results, so populate the map with items
			while (queryResult.next()) {
				String resTitle = queryResult.getString("Title"),
					   resSystemID = queryResult.getString("System");
				long resItemID = queryResult.getLong("ItemID");
				result.put(resItemID, new DBItem(resItemID, resSystemID, resTitle));
			}
			queryResult.close();
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
			if (verifyResult.next() && verifyResult.getInt(1) == 1) {
				stmt.close();
				
				stmt = connect.prepareStatement(removeQuery);
				stmt.setLong(1, itemID);
				stmt.executeUpdate();
			}
			else {
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
	public boolean updateItem(long itemID, String systemID, String title) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.Items SET System = ?, Title = ? WHERE ItemID = ?";
		boolean result = true;
		PreparedStatement stmt = null;
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, systemID);
			stmt.setString(2, title);
			stmt.setLong(3, itemID);
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (SQLException e) {
			//This means there was an error with executing the update, so return false.
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	@Override
	public List<Item> searchTitle(String title) {
		Connection connect = pool.getConnection();
		String searchQuery = "SELECT Title, ItemID, LEVENSHTEIN(Title, ?) AS distance FROM awsdb.Items WHERE Title Like ? ORDER BY distance ASC",
			   retrieveQuery = "SELECT * FROM awsdb.Items WHERE ItemID = ?";
		List<Item> result = new ArrayList<Item>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(searchQuery);
			stmt.setString(1, title);
			stmt.setString(2, "%" + title + "%");
			ResultSet searchRS = stmt.executeQuery();
			
			while (searchRS.next()) {
				PreparedStatement resultStmt = connect.prepareStatement(retrieveQuery);
				resultStmt.setLong(1, searchRS.getLong("ItemID"));
				ResultSet rs = resultStmt.executeQuery();
				rs.next();
				String tle = rs.getString("Title"),
					   system = rs.getString("System");
				long id = rs.getLong("ItemID");
				result.add(new DBItem(id, system, tle));
				
				//Clean up resources
				rs.close();
				resultStmt.close();
			}
			
			//Clean up resources
			searchRS.close();
			stmt.close();			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}
}
