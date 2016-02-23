package edu.cpp.cs580.Database.Queries.Interface;

import java.util.List;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.Notification;

public interface NotificationQuery {
	/**
	 * Adds a notification for the user on the given itemID. Requires the current price of the item, and at
	 * which price the user wants the notification.
	 * @param notice	Notification which will be added
	 * @return			True if addition is successful, false otherwise
	 */
	public boolean addNotification(Notification notice);
	/**
	 * Retrieves a notification for a given user and item. This is used to retrieve a single notification.
	 * @param username	Username of user
	 * @param itemID	ItemID of item
	 * @return			Notification object or NULL if none is found
	 */
	public Notification getNotice(String username, long itemID);
	/**
	 * Get multiple notifications for a given user. May be zero or more depending on query results.
	 * @param username	Username of user
	 * @return			A map with all notifications for a single user. Key will be itemID
	 */
	public Map<Long, Notification> getNotifications(String username);
	/**
	 * Get multiple notifications for a given item. May be zero or more depending on query results.
	 * @param itemID	ItemID of item
	 * @return			A map with all notifications for a single item. Key will be username.
	 */
	public Map<String, Notification> getNotifications(long itemID);
	public List<Notification> getNotifications2(String username);
	/**
	 * Gets a list of all notifications which are ready to be sent out. These notifications are at or
	 * below their notify price.
	 * @return			List of all notifications ready to be sent out.
	 */
	public List<Notification> getFulfilledNotifications();
	/**
	 * Removes a single notification for a given username and itemID.
	 * @param username	Username of user
	 * @param itemID	ItemID of item
	 * @return			True if removal is successful, false otherwise.
	 */
	public boolean removeNotification(String username, long itemID);
	/**
	 * Updates a given notification. Only the notification price may be updated.
	 * @param notice	Notification which will be updated
	 * @return			True if update is successful, false otherwise.
	 */
	public boolean updateNotification(Notification notice);
}
