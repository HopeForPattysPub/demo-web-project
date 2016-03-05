package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBStoreProduct;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Queries.Interface.StoreProductQuery;

public class DBStoreProductQuery implements StoreProductQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addStoreProduct(StoreProduct newProduct) {
		Connection connect = pool.getConnection();
		String query = "INSERT INTO awsdb.StoreProducts(StoreID, ItemID, StoreProductID, Price, PriceDate, URL) VALUES(?,?,?,?,?,?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, newProduct.getStoreID());
			stmt.setLong(2, newProduct.getItemID());
			stmt.setString(3, newProduct.getStoreProductID());
			stmt.setDouble(4, newProduct.getPrice());
			stmt.setTimestamp(5, newProduct.getPriceDate());
			stmt.setString(6, newProduct.getURL());
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
	public StoreProduct getSingleProduct(int storeID, long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.StoreProducts WHERE StoreID = ? AND ItemID = ?";
		StoreProduct result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int strID = rs.getInt("StoreID");
				long itmID = rs.getLong("ItemID");
				String spID = rs.getString("StoreProductID"),
					   url = rs.getString("URL");
				double price = rs.getDouble("Price");
				Timestamp date = rs.getTimestamp("PriceDate");
				
				result = new DBStoreProduct(itmID, price, date, strID, spID, url);
			}
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Map<Long, StoreProduct> getStoreProducts(int storeID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.StoreProducts WHERE StoreID = ?";
		Map<Long, StoreProduct> result = new HashMap<Long, StoreProduct>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				int strID = rs.getInt("StoreID");
				long itmID = rs.getLong("ItemID");
				String spID = rs.getString("StoreProductID"),
					   url = rs.getString("URL");
				double price = rs.getDouble("Price");
				Timestamp date = rs.getTimestamp("PriceDate");
				
				result.put(itmID, new DBStoreProduct(itmID, price, date, strID, spID, url));
			}
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Map<Integer, StoreProduct> getItemIDProducts(long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.StoreProducts WHERE ItemID = ?";
		Map<Integer, StoreProduct> result = new HashMap<Integer, StoreProduct>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setLong(1, itemID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				int strID = rs.getInt("StoreID");
				long itmID = rs.getLong("ItemID");
				String spID = rs.getString("StoreProductID"),
					   url = rs.getString("URL");
				double price = rs.getDouble("Price");
				Timestamp date = rs.getTimestamp("PriceDate");
				
				result.put(strID, new DBStoreProduct(itmID, price, date, strID, spID, url));
			}
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean removeStoreProduct(int storeID, long itemID) {
		Connection connect = pool.getConnection();
		String removeQuery = "DELETE FROM awsdb.StoreProducts WHERE StoreID = ? AND ItemID = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.StoreProducts WHERE StoreID = ? AND ItemID = ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//Verify that store product exists
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setInt(1, storeID);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				//Remove product
				stmt = connect.prepareStatement(removeQuery);
				stmt.setInt(1, storeID);
				stmt.setLong(2, itemID);
				stmt.executeUpdate();
			}
			else
				result = false;
			
			rs.close();
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
	public boolean updateStoreProduct(StoreProduct product) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.StoreProducts SET StoreProductID = ?, Price = ?, PriceDate = ?, URL = ? WHERE StoreID = ? AND ItemID = ?";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(5, product.getStoreID());
			stmt.setLong(6, product.getItemID());
			stmt.setString(1, product.getStoreProductID());
			stmt.setDouble(2, product.getPrice());
			stmt.setTimestamp(3, product.getPriceDate());
			stmt.setString(4, product.getURL());
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
	public StoreProduct getSingleProduct(int storeID, String storeProductID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.StoreProducts WHERE StoreID = ? AND StoreProductID = ?";
		StoreProduct result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			stmt.setString(2, storeProductID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				int strID = rs.getInt("StoreID");
				long itmID = rs.getLong("ItemID");
				String spID = rs.getString("StoreProductID"),
					   url = rs.getString("URL");
				double price = rs.getDouble("Price");
				Timestamp date = rs.getTimestamp("PriceDate");
				
				result = new DBStoreProduct(itmID, price, date, strID, spID, url);
			}
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

}
