package edu.cpp.cs580.Database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBSingleton {
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	
	public static boolean makePool() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost:3306/awsdb");	//TODO: Change this once moved to AWS
			cpds.setUser("root");
			cpds.setPassword("admin1");
			cpds.setMaxPoolSize(20);
			cpds.setMinPoolSize(3);
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
	
	public static Connection getConnection() {
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
