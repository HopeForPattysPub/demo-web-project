package edu.cpp.cs580.Database.Objects;

import edu.cpp.cs580.Database.Objects.Interfaces.User;

public class DBUser implements User {
	/******************Data Members********************/
	private String email,
				   password,
				   username;
	
	/******************Constructors*******************/
	/**
	 * Creates a default user with nothing initialized.
	 */
	public DBUser() {
		email = null;
		password = null;
		username = null;
	}
	
	/**
	 * Creates a DBUser with the username, email, and password initalized
	 * to the given parameters.
	 * 
	 * @param uname	User username
	 * @param mail	User email
	 * @param pass	User password
	 */
	public DBUser(String uname, String mail, String pass) {
		username = uname;
		email = mail;
		password = pass;
	}
	
	/******************Methods************************/
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setEmail(String em) {
		email = em;
	}

	@Override
	public void setPassword(String ps) {
		password = ps;
	}
	
	@Override
	public void setUsername(String un) {
		username = un;
	}

}
