package edu.cpp.cs580.Database.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cpp.cs580.Database.DBConnectionPool;
import edu.cpp.cs580.Database.Objects.DBUser;
import edu.cpp.cs580.Database.Objects.Interfaces.User;
import edu.cpp.cs580.Database.Queries.Interface.UserQuery;

public class DBUserQuery implements UserQuery {
	/******************Data Members*******************/
	private DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************Methods************************/
	@Override
	public int addUser(User newUser) {
		Connection connect = pool.getConnection();
		String insertQuery = "INSERT INTO awsdb.Users(Username, Email, UserPassword) VALUES(?,?,?)",
			   verifyUserQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Users WHERE Username = ?)";
		int result = 0;
		PreparedStatement stmt = null;
		
		try {
			//First verify that the username does not exist
			stmt = connect.prepareStatement(verifyUserQuery);
			stmt.setString(1, newUser.getUsername());
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 0) {
				stmt.close();
				
				//Username does not exist so add user. If email is not unique, an exception will be thrown
				//which would be handled by the Catch block
				stmt = connect.prepareStatement(insertQuery);
				stmt.setString(1, newUser.getUsername());
				stmt.setString(2, newUser.getEmail());
				stmt.setString(3, newUser.getPassword());
				stmt.executeUpdate();
				result = 2;		//Successful
			}
			
			//Clear resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			//There was an error inserting row. This means the email was not unique.
			System.err.println(e.getMessage());
			result = 1;		//Email exists already
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
	public User getUser(String option) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Users WHERE Username = ? OR Email = ?";
		User result = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, option);
			stmt.setString(2, option);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				String user = rs.getString("Username"),
					   email = rs.getString("Email"),
					   pwd = rs.getString("UserPassword");
				result = new DBUser(user, email, pwd);
			}
			
			//Clear resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
		pool.closeConnection(connect);
		return result;
	}

	@Override
	public boolean loginUser(String user, String password) {
		Connection connect = pool.getConnection();
		String query = "SELECT * FROM awsdb.Users WHERE Username = ?";
		boolean result = false;
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement(query);
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			
			//Verify if username exists
			if (rs.next()) {
				//Verify password
				String pwd = rs.getString("UserPassword");
				if (pwd.equals(password))
					result = true;
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
	public boolean removeUser(String user) {
		Connection connect = pool.getConnection();
		String removeQuery = "DELETE FROM awsdb.Users WHERE Username = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Users WHERE Username = ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//First verify that the username exist
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setString(1, user);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				//Username exists so delete
				stmt = connect.prepareStatement(removeQuery);
				stmt.setString(1, user);
				stmt.executeUpdate();
			}
			
			//Clear resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			//There was an error removing user
			System.err.println(e.getMessage());
			result = false;		//Email exists already
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
	public boolean updateUser(User user) {
		Connection connect = pool.getConnection();
		String updateQuery = "UPDATE awsdb.Users SET Email = ?, UserPassword = ? WHERE Username = ?",
			   verifyQuery = "SELECT EXISTS(SELECT 1 FROM awsdb.Users WHERE Username = ?)";
		boolean result = true;
		PreparedStatement stmt = null;
		
		try {
			//First verify that the username exist
			stmt = connect.prepareStatement(verifyQuery);
			stmt.setString(1, user.getUsername());
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 1) {
				stmt.close();
				
				//Username exists, so update it
				stmt = connect.prepareStatement(updateQuery);
				stmt.setString(1, user.getEmail());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getUsername());
				stmt.executeUpdate();
			}
			
			//Clear resources
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			//There was an error inserting row. This means the email was not unique.
			System.err.println(e.getMessage());
			result = false;		//Email exists already
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
