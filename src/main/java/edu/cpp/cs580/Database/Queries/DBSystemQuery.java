package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBSystem;
import edu.cpp.cs580.Database.Objects.Interfaces.Systems;
import edu.cpp.cs580.Database.Queries.Interface.SystemQuery;

public class DBSystemQuery implements SystemQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public boolean addSystem(Systems system) {
		Connection connect = pool.getConnection();
		boolean result = true;
		String query = "INSERT INTO awsdb.Systems(SystemID, SystemName) VALUES(?, ?)";
		PreparedStatement stmt = null;
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, system.getSystemID());
			stmt.setString(2, system.getSystemName());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
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
	public Systems getSystem(String systemID) {
		Connection connect = pool.getConnection();
		Systems result = null;
		String query = "SELECT * FROM awsdb.Systems WHERE SystemID = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, systemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				String sysID = rs.getString("SystemID"),
					   sysName = rs.getString("SystemName");
				result = new DBSystem(sysID, sysName);
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
	public boolean removeSystem(String systemID) {
		Connection connect = pool.getConnection();
		boolean result = true;
		String removeQuery = "DELETE FROM awsdb.Systems WHERE SystemID = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Systems WHERE SystemID = ?)";
		PreparedStatement stmt = null;
		
		try {
			//Verify SystemID exists before deleting
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setString(1, systemID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				//Delete the System
				stmt = connect.prepareStatement(removeQuery);
				stmt.setString(1, systemID);
				stmt.executeUpdate();
			}
			else
				result = false;
			
			//Close to prevent resource leaks
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
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
	public boolean updateSystem(Systems system) {
		Connection connect = pool.getConnection();
		String query = "UPDATE awsdb.Systems SET SystemName = ? WHERE SystemID = ?";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, system.getSystemName());
			stmt.setString(2, system.getSystemID());
			stmt.executeUpdate();
			stmt.close();
		} catch(SQLException e) {
			//There was an error updating. More than like systemID did not exist
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

}
