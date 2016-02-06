package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBUser;
import edu.cpp.cs580.Database.Objects.Interfaces.User;

import org.junit.Assert;

public class TestDBUser {
	@Test
	public void testDBUserInitalizedConstructor() {
		String u = "Test",
			   e = "test@test",
			   p = "password";
		//Test initialized constructor and get methods
		User user = new DBUser(u, e, p);
		Assert.assertEquals(u, user.getUsername());
		Assert.assertEquals(e, user.getEmail());
		Assert.assertEquals(p, user.getPassword());
	}
	
	@Test
	public void testDBUserDefaultConstructor() {
		String u = "Test2",
			   e = "test2@test",
			   p = "password2";
		
		//Test default constructors
		User user = new DBUser();
		Assert.assertNull(user.getUsername());
		Assert.assertNull(user.getEmail());
		Assert.assertNull(user.getPassword());
		
		//Test set methods
		user.setEmail(e);
		user.setPassword(p);
		user.setUsername(u);
		Assert.assertEquals(u, user.getUsername());
		Assert.assertEquals(e, user.getEmail());
		Assert.assertEquals(p, user.getPassword());
	}
}
