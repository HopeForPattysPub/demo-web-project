package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBStore;
import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Queries.Interface.StoreQuery;

public class DBStoreQuery implements StoreQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addStore(String name, String url) {
		Connection connect = pool.getConnection();
		String query = "INSERT INTO awsdb.Stores(StoreName, StoreID, WebPage) VALUES(?,?,?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			//Determine the next StoreID
			int storeID = calculateStoreID();
			
			stmt.setString(1, name);
			stmt.setInt(2, storeID);
			stmt.setString(3, url);
			stmt.executeUpdate();
			stmt.close();
		} catch(SQLException e) {
			//There was an error inserting row, so change result to fail
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch(SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Store getStore(int storeID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Stores WHERE StoreID = ?";
		Store result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			ResultSet rs = stmt.executeQuery();
			
			//If there is a result, then save it into a Store item
			if (rs.next()) {
				String name = rs.getString("StoreName"),
					   page = rs.getString("WebPage");
				int id = rs.getInt("StoreID");
				result = new DBStore(id, name, page);
			}
			
			//Close to prevent resource leak
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Store getStore(String storeName) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Stores WHERE StoreName = ?";
		Store result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, storeName);
			ResultSet rs = stmt.executeQuery();
			
			//If there is a result, then save it into a Store item
			if (rs.next()) {
				String name = rs.getString("StoreName"),
					   page = rs.getString("WebPage");
				int id = rs.getInt("StoreID");
				result = new DBStore(id, name, page);
			}
			
			//Close to prevent resource leak
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}
	
	@Override
	public boolean removeStore(int storeID) {
		Connection connect = pool.getConnection();
		String removeQuery = "DELETE FROM awsdb.Stores WHERE StoreID = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Stores WHERE StoreID = ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//First verify the StoreID exists
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setInt(1, storeID);
			ResultSet rs = stmt.executeQuery();
			
			//StoreId exists, now remove it
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				stmt = connect.prepareStatement(removeQuery);
				stmt.setInt(1, storeID);
				stmt.executeUpdate();
			}
			else
				result = false;
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch(SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean updateStore(Store store) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.Stores SET StoreName = ?, WebPage = ? WHERE StoreID = ?";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, store.getStoreName());
			stmt.setString(2, store.getURL());
			stmt.setInt(3, store.getStoreID());
			stmt.executeUpdate();
			stmt.close();
		} catch(SQLException e) {
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
	private int calculateStoreID() {
		String query = "SELECT StoreID FROM awsdb.Stores ORDER BY StoreID DESC LIMIT 1";
		
		Connection connect = pool.getConnection();
		int storeID;
		try {
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			//If the set is empty (should only happen once, ever)
			if (!result.next())
				storeID = 1;
			else {
				storeID = result.getInt("StoreID");
				//Add one to itemID
				storeID += 1;
			}
			
			//Close to prevent resource leak
			result.close();
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			storeID = 1;
		}
		
		//Close connection
		pool.closeConnection(connect);
		
		return storeID;
	}

	@Override
	public Map<Integer, Store> getAllStores() {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Stores";
		Map<Integer, Store> result = new HashMap<Integer, Store>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			//If there is a result, then save it into a Store item
			while (rs.next()) {
				String name = rs.getString("StoreName"),
					   page = rs.getString("WebPage");
				int id = rs.getInt("StoreID");
				result.put(id, new DBStore(id, name, page));
			}
			
			//Close to prevent resource leak
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}
}
