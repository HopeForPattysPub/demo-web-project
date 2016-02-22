package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBPriceHistory;
import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;
import edu.cpp.cs580.Database.Queries.Interface.PriceHistoryQuery;

public class DBPriceHistoryQuery implements PriceHistoryQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addHistory(PriceHistory newHistory) {
		Connection connect = pool.getConnection();
		String query = "INSERT INTO awsdb.PriceHistory(ItemID, StoreID, PriceDate, Price) VALUES (?,?,?,?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setLong(1, newHistory.getItemID());
			stmt.setInt(2, newHistory.getStoreID());
			stmt.setDate(3, newHistory.getPriceDate());
			stmt.setDouble(4, newHistory.getPrice());
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
	public List<PriceHistory> getHistory(int storeID, long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.PriceHistory WHERE StoreID = ? AND ItemID = ?";
		List<PriceHistory> result = new ArrayList<PriceHistory>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				long itID = rs.getLong("ItemID");
				int strID = rs.getInt("StoreID");
				Date date = rs.getDate("PriceDate");
				double price = rs.getDouble("Price");
				result.add(new DBPriceHistory(itID, price, date, strID));
			}
			
			//Close resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Date getRecentDate(int storeID, long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT PriceDate FROM awsdb.PriceHistory WHERE StoreID = ? AND ItemID = ? ORDER BY PriceDate DESC LIMIT 1";
		Date result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setInt(1, storeID);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getDate("PriceDate");
			}
			
			//Close resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}
}
