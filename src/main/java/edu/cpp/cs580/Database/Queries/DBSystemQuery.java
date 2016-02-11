package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.SQLException;

import edu.cpp.cs580.Database.DBConnectionPool;
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
		String query = "INSERT INTO awsdb.System(SystemID, SystemName) VALUES(?, ?)";
		
		try {
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public Systems getSystem(String systemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeSystem(String systemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSystem(Systems system) {
		// TODO Auto-generated method stub
		return false;
	}

}
