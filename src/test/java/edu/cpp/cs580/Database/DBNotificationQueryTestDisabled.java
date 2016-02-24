package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBNotification;
import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Queries.DBNotificationQuery;
import edu.cpp.cs580.Database.Queries.Interface.NotificationQuery;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

public class DBNotificationQueryTestDisabled {
	@Test
	public void notificationTest() {
		NotificationQuery nq = new DBNotificationQuery();
		
		//Add a notification for ItemID 1 and username Test
		Notification notice = new DBNotification(1, 25.00, "test");
		boolean result = nq.addNotification(notice);
		Assert.assertEquals(true, result);
		
		//Get Notification
		notice = nq.getNotice("test", 1);
		Assert.assertEquals("test", notice.getUsername());
		Assert.assertEquals(25.00, notice.getNotifyPrice(), 0.5);
		
		//Get Notification for ItemID 1
		Map<String, Notification> map = nq.getNotifications(1);
		Assert.assertEquals(25.00, map.get("test").getNotifyPrice(), 0.5);
		
		//Get notification for username
		Map<Long, Notification> map2 = nq.getNotifications("test");
		Assert.assertEquals(25.00, map2.get(new Long(1)).getNotifyPrice(), 0.5);
		
		//Update a notification for given username and itemID
		notice = new DBNotification(1, 25.00, "test");
		result = nq.updateNotification(notice);
		Assert.assertEquals(true, result);
		
		//Get items which are ready to send off notifications
		List<Notification> list = nq.getFulfilledNotifications();
		Assert.assertEquals("test", list.get(0).getUsername());
		
		//Remove notification
		result = nq.removeNotification("test", 1);
		Assert.assertEquals(true, result);
	}
}
