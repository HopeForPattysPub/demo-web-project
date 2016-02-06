package edu.cpp.cs580;

import org.junit.Test;

import edu.cpp.cs580.Database.DBSingleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;

public class TestDBSingleton {
	@Test
	public void testMakePool() {
		boolean result = DBSingleton.makePool();
		
		Assert.assertEquals(true, result);
		
	}
	
	@Test
	public void testGetConnection() {
		Connection c = DBSingleton.getConnection();
		
		Assert.assertNotNull(c);
	}
	
	@Test
	public void testConnection() {
		Connection c = null;
		try {
			DBSingleton.makePool();
			c = DBSingleton.getConnection();
			Statement s = c.createStatement();
			ResultSet result = s.executeQuery("SELECT * FROM USERS");
			
			Assert.assertEquals(3, result.getMetaData().getColumnCount());
			
			while (result.next()) {
				String username = result.getString("Username");
				String email = result.getString("Email");
				String password = result.getString("UserPassword");
				
				System.out.println(username + "\n" + email + "\n" + password + "\n");
			}
			
			if (c != null)
				c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
