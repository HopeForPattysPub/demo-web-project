package edu.cpp.cs580.Database.Queries.Interface;

import edu.cpp.cs580.Database.Objects.Interfaces.User;

public interface UserQuery {
	/**
	 * Add a user to the Database. Username and email must be unique, and the password is already
	 * hashed.
	 * @param newUser	New user which will be added
	 * @return			True if new user is added, false otherwise.
	 */
	public boolean addUser(User newUser);
	/**
	 * Get the user given a username or email. Should use email whenever possible.
	 * @param option	Username or email of user to retrieve.
	 * @return			User object with result. NULL if no user found.
	 */
	public User getUser(String option);
	/**
	 * Verify and validate a user.
	 * @param user		Username of user
	 * @param password	Hashed password to be used for comparison
	 * @return			True if username and password match, false otherwise.
	 */
	public boolean loginUser(String user, String password);
	/**
	 * Remove a user. To ensure some security, only the username can be used.
	 * @param user	Username of user to remove
	 * @return		True if removal is successful, false otherwise.
	 */
	public boolean removeUser(String user);
	/**
	 * Update a user. Only the email and/or password can be updated. If the new email must still
	 * be unique, and the password must be hashed.
	 * @param user	User object which will be updated
	 * @return		True if update is successful, false otherwise.
	 */
	public boolean updateUser(User user);
}
