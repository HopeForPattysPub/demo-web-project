package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBUser;
import edu.cpp.cs580.Database.Objects.Interfaces.User;
import edu.cpp.cs580.Database.Queries.DBUserQuery;
import edu.cpp.cs580.Database.Queries.Interface.UserQuery;
import org.junit.Assert;

public class DBUserQueryTest {
	@Test
	public void userQueryTest() {
		UserQuery uq = new DBUserQuery();
		
		//Add a user
		User user = new DBUser("testuser", "testuser@test", "password");
		int rs = uq.addUser(user);
		Assert.assertEquals(2, rs);
		
		//Get user using username
		user = uq.getUser("testuser");
		Assert.assertEquals("testuser@test", user.getEmail());
		
		//Get user using email
		user = uq.getUser("testuser@test");
		Assert.assertEquals("testuser", user.getUsername());
		
		//Test login
		boolean result = uq.loginUser("testuser", "password");
		Assert.assertEquals(true, result);
		
		//Update user
		user.setPassword("hunter2");
		result = uq.updateUser(user);
		Assert.assertEquals(true, result);
		
		//Retest login with old password
		result = uq.loginUser("testuser", "password");
		Assert.assertEquals(false, result);
		
		//Remove user
		result = uq.removeUser("testuser");
		Assert.assertEquals(true, result);
	}
}
