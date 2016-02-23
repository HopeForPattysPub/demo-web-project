package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBNotification;
import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Queries.Interface.NotificationQuery;

public class DBNotificationQuery implements NotificationQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addNotification(Notification notice) {
		Connection connect = pool.getConnection();
		String query = "INSERT INTO awsdb.Notifications(Username, ItemID, NotifyPrice) VALUES(?, ?, ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, notice.getUsername());
			stmt.setLong(2, notice.getItemID());
			stmt.setDouble(3, notice.getNotifyPrice());
			stmt.executeUpdate();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Notification getNotice(String username, long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Notifications WHERE Username = ? AND ItemID = ?";
		Notification result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				String name = rs.getString("Username");
				long id = rs.getLong("ItemID");
				double notifyPrice= rs.getDouble("NotifyPrice");
				result = new DBNotification(id, notifyPrice, name);
			}
			
			rs.close();
			connect.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public Map<Long, Notification> getNotifications(String username) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Notifications WHERE Username = ?";
		Map<Long, Notification> result = new HashMap<Long, Notification>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("Username");
				long id = rs.getLong("ItemID");
				double notifyPrice= rs.getDouble("NotifyPrice");
				result.put(id, new DBNotification(id, notifyPrice, name));
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
	public Map<String, Notification> getNotifications(long itemID) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Notifications WHERE ItemID = ?";
		Map<String, Notification> result = new HashMap<String, Notification>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setLong(1, itemID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("Username");
				long id = rs.getLong("ItemID");
				double notifyPrice= rs.getDouble("NotifyPrice");
				result.put(name, new DBNotification(id, notifyPrice, name));
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
	public List<Notification> getFulfilledNotifications() {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Notifications AS Note WHERE (SELECT Prod.Price FROM awsdb.StoreProducts AS Prod WHERE Prod.ItemID = Note.ItemID ORDER BY Prod.Price ASC LIMIT 1) <= Note.NotifyPrice";
		List<Notification> result = new ArrayList<Notification>();
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("Username");
				long id = rs.getLong("ItemID");
				double notifyPrice= rs.getDouble("NotifyPrice");
				result.add(new DBNotification(id, notifyPrice, name));
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
	public boolean removeNotification(String username, long itemID) {
		Connection connect = pool.getConnection();
		String resultQuery = "DELETE FROM awsdb.Notifications WHERE Username = ? AND ItemID = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Notifications WHERE Username = ? AND ItemID = ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//First verify the notification exists
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setString(1, username);
			stmt.setLong(2, itemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				//Delete the notification
				stmt = connect.prepareStatement(resultQuery);
				stmt.setString(1, username);
				stmt.setLong(2, itemID);
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
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean updateNotification(Notification notice) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.Notifications SET NotifyPrice = ? WHERE Username = ? AND ItemID = ?";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//First verify the notification exists
			stmt = connect.prepareStatement(query);
			stmt.setDouble(1, notice.getNotifyPrice());
			stmt.setString(2, notice.getUsername());
			stmt.setLong(3, notice.getItemID());
			stmt.executeUpdate();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
			result = false;
			try {
				stmt.close();
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
		}
		
		pool.closeConnection(connect);
		return result;
	}

}
