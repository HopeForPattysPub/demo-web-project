package edu.cpp.cs580.Database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnectionPool {
	private static DBConnectionPool instance = null;
	private ComboPooledDataSource cpds = null;
	
	/**
	 * Private default constructor to force Singleton design
	 */
	private DBConnectionPool() {
		makePool();
	}
	
	private boolean makePool() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost:3306/awsdb");	//TODO: Change this once moved to AWS
			cpds.setUser("root");
			cpds.setPassword("admin");
			cpds.setMaxPoolSize(50);
			cpds.setMinPoolSize(10);
			cpds.setAcquireIncrement(1);
			cpds.setTestConnectionOnCheckin(true);
			cpds.setIdleConnectionTestPeriod(300);
			cpds.setMaxIdleTimeExcessConnections(240);
		} catch(PropertyVetoException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets a connection from the connection pool
	 * @return
	 */
	public Connection getConnection() {
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get the Singleton instance of DBSingleton
	 * @return instance of DBConnectionSingleton
	 */
	public static DBConnectionPool getInstance() {
		if (instance == null)
			instance = new DBConnectionPool();
		
		return instance;
	}
}
