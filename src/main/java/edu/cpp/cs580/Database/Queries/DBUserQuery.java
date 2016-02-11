package edu.cpp.cs580.Database.Queries;

import edu.cpp.cs580.Database.Objects.Interfaces.User;
import edu.cpp.cs580.Database.Queries.Interface.UserQuery;

public class DBUserQuery implements UserQuery {

	@Override
	public boolean addUser(User newUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loginUser(String user, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
