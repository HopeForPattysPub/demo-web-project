package edu.cpp.cs580.Database.Queries;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Queries.Interface.NotificationQuery;

public class DBNotificationQuery implements NotificationQuery {

	@Override
	public boolean addNotification(Notification notice) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Notification getNotice(String username, BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BigInteger, Notification> getNotifications(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Notification> getNotifications(BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notification> getFulfilledNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeNotification(String username, BigInteger itemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNotification(Notification notice) {
		// TODO Auto-generated method stub
		return false;
	}

}
