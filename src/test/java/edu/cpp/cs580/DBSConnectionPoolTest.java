package edu.cpp.cs580;

import org.junit.Test;

import edu.cpp.cs580.Database.DBConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;

public class DBSConnectionPoolTest {
	@Test
	public void testGetInstance() {
		DBConnectionPool test = DBConnectionPool.getInstance();
		
		Assert.assertNotNull(test);
	}
	
	@Test
	public void testGetConnection() {
		DBConnectionPool test = DBConnectionPool.getInstance();
		Connection c = test.getConnection();
		
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testConnection() {
		DBConnectionPool pool = DBConnectionPool.getInstance();
		Connection c = null;
		try {
			c = pool.getConnection();
			Statement s = c.createStatement();
			ResultSet result = s.executeQuery("SELECT * FROM USERS");
			
			Assert.assertEquals(3, result.getMetaData().getColumnCount());
			
			while (result.next()) {
				String username = result.getString("Username");
				String email = result.getString("Email");
				String password = result.getString("UserPassword");
				
				System.out.println(username + "\n" + email + "\n" + password + "\n");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
