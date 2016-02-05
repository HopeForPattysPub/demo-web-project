package edu.cpp.cs580;

import org.junit.Test;

import edu.cpp.cs580.Database.DBSingleton;

import java.sql.Connection;

import org.junit.Assert;

public class TestDatabaseSingleton {
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
}
