package edu.cpp.cs580.Database;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.Item;
import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;
import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Objects.Interfaces.System;
import edu.cpp.cs580.Database.Objects.Interfaces.User;

public class DBQuery implements Query {

	@Override
	public boolean addItem(String systemID, String Title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item getItem(String itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Item> getItems(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeItem(String itemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateItem(String itemID, String systemID, String Title) {
		// TODO Auto-generated method stub
		return false;
	}

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

	@Override
	public boolean addHistory(PriceHistory newHistory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PriceHistory> getHistory(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStore(String name, String url) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Store getStore(int storeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeStore(int storeID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateStore(int storeID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addStoreProduct(StoreProduct newProduct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StoreProduct getSingleProduct(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BigInteger, StoreProduct> getStoreProducts(int storeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, StoreProduct> getStoreProducts(BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeStoreProduct(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateStoreProduct(StoreProduct product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addSystem(System system) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public System getSystem(String systemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeSystem(String systemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSystem(System system) {
		// TODO Auto-generated method stub
		return false;
	}

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
