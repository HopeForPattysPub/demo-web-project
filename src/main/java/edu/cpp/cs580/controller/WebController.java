package edu.cpp.cs580.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.net.HostAndPort;
import com.google.common.net.HostSpecifier;
import com.google.gson.Gson;

import edu.cpp.cs580.App;
import edu.cpp.cs580.Database.DBScheduler;
import edu.cpp.cs580.Database.Objects.DBItem;
import edu.cpp.cs580.Database.Objects.DBNotification;
import edu.cpp.cs580.Database.Objects.DBStoreProduct;
import edu.cpp.cs580.Database.Objects.DBUser;
import edu.cpp.cs580.Database.Objects.UserTrackItemjava;
import edu.cpp.cs580.Database.Objects.Interfaces.Item;
import edu.cpp.cs580.Database.Objects.Interfaces.Notification;
import edu.cpp.cs580.Database.Objects.Interfaces.Store;
import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Objects.Interfaces.User;
//import edu.cpp.cs580.data.User;
import edu.cpp.cs580.data.provider.UserManager;
import edu.cpp.cs580.webdata.parser.GameDataPage;
import edu.cpp.cs580.webdata.parser.ParserNotCompleteException;
import edu.cpp.cs580.webdata.parser.WebPageInfoNotInitializedException;
import edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage;
import edu.cpp.cs580.webdata.parser.Steam.SteamQueryPage;
import edu.cpp.cs580.webdata.parser.Steam.SteamTopPage;
import edu.cpp.cs580.Database.Queries.DBItemQuery;
import edu.cpp.cs580.Database.Queries.DBNotificationQuery;
import edu.cpp.cs580.Database.Queries.DBStoreProductQuery;
import edu.cpp.cs580.Database.Queries.DBStoreQuery;
import edu.cpp.cs580.Database.Queries.Interface.UserQuery;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {

	/**
	 * When the class instance is annotated with
	 * {@link Autowired}, it will be looking for the actual
	 * instance from the defined beans.
	 * <p>
	 * In our project, all the beans are defined in
	 * the {@link App} class.
	 */
	@Autowired
	private UserManager userManager;
	private DBScheduler scheduler = DBScheduler.getScheduler();
	
	@Autowired
	private DBNotificationQuery dbNotificationQuery;
	@Autowired
	private DBItemQuery dbItemQuery;
	@Autowired
	private UserQuery dbUserQuery;
	@Autowired
	private DBStoreProductQuery dbStoreIDQuery;
	

	/**
	 * This is a simple example of how the HTTP API works.
	 * It returns a String "OK" in the HTTP response.
	 * To try it, run the web application locally,
	 * in your web browser, type the link:
	 * 	http://localhost:8080/cs580/ping
	 */
	@RequestMapping(value = "/cs580/ping", method = RequestMethod.GET)
	String healthCheck() {
		// You can replace this with other string,
		// and run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return "OK";
	}
	
	private boolean addStoreProduct(int store, String storeID)
	{
		if(dbStoreIDQuery.getSingleProduct(store, storeID) != null) return false;
		GameDataPage dataPage = null;
		
		if(store == 1)
			dataPage = new SteamJSONDataPage(Integer.parseInt(storeID));
		else throw new Error("Incorrect store");
		
		// TODO: Use dbItemQuery searchTitle function and utilize edit distance to determine if title is close
		if(dbItemQuery.getItems("Title", dataPage.getGameName()).size() == 0)
			dbItemQuery.addItem("PC", dataPage.getGameName());
		
		Item item = dbItemQuery.getItem(dataPage.getGameName(),"PC");
		System.out.println(dataPage.getGameName() + " created item" + item);
		return dbStoreIDQuery.addStoreProduct(new DBStoreProduct(item.getItemID(), 
															dataPage.getPrice(), 
															new Timestamp((new java.util.Date()).getTime()), 
															store, 
															storeID, 
															dataPage.getPageURL()));
		
	}
	
	@RequestMapping(value = "/parser/steam/query/{query}", method = RequestMethod.GET)
	public String getQueryResults(@PathVariable("query") String query)
	{
		SteamQueryPage queryPage = new SteamQueryPage(query);
		List<String> names = queryPage.getNames();
		List<UserTrackItemjava> queryItemsList = new ArrayList<UserTrackItemjava>();
		names.subList(0, 5).forEach(n -> {
			UserTrackItemjava matchedQueryObject = new UserTrackItemjava();;
			
			String storeItemID = ""+queryPage.getAppID(n);
			System.out.println("========== " + storeItemID);
			StoreProduct currentStoreItem = dbStoreIDQuery.getSingleProduct(1, storeItemID);
			if(currentStoreItem == null) {
				addStoreProduct(1, storeItemID);
				currentStoreItem = dbStoreIDQuery.getSingleProduct(1, storeItemID);
			}
			Item currentItem =  dbItemQuery.getItem(currentStoreItem.getItemID());
			matchedQueryObject.SetURL(currentStoreItem.getURL());
			matchedQueryObject.SetPrice(currentStoreItem.getPrice());
			matchedQueryObject.SetSystem(currentItem.getSystem());
			matchedQueryObject.SetItemID(currentStoreItem.getItemID());
			matchedQueryObject.SetTitle(currentItem.getTitle());
			queryItemsList.add(matchedQueryObject);
		});
		
		String results = null;
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(queryItemsList);
		System.out.println("{\"items\":" + jsonCartList + "}");
		//results = "{\"items\":" + jsonCartList + "}";
		results = jsonCartList;
		
		return results;
	}
	
	@RequestMapping(value = "/steamTopPage", method = RequestMethod.GET)
	public String getTopPage() {
		List<Integer> topList = new ArrayList<>();
		SteamTopPage test = new SteamTopPage();
		topList = test.getTopGameIDList(); 
		List<UserTrackItemjava> TopGamesList = new ArrayList<UserTrackItemjava>();
		
		String storeItemID;
		StoreProduct currentStoreItem = null;
		Item currentItem = null;
		UserTrackItemjava lowestPriceObject = null;
		
		for(Integer x:topList)
		{
			System.out.println(x.toString());
			storeItemID = Integer.toString(x);
			currentStoreItem = dbStoreIDQuery.getSingleProduct(1, storeItemID);
			
			if(currentStoreItem == null)
			{
				System.out.print("first query null");
				addStoreProduct(1, storeItemID);
				currentStoreItem = dbStoreIDQuery.getSingleProduct(1, storeItemID);				
			}
			System.out.print(currentStoreItem);
			currentItem =  dbItemQuery.getItem(currentStoreItem.getItemID());
			lowestPriceObject = new UserTrackItemjava();
			lowestPriceObject.SetURL(currentStoreItem.getURL());
			lowestPriceObject.SetPrice(currentStoreItem.getPrice());
			lowestPriceObject.SetSystem(currentItem.getSystem());
			lowestPriceObject.SetItemID(currentStoreItem.getItemID());
			lowestPriceObject.SetTitle(currentItem.getTitle());
			TopGamesList.add(lowestPriceObject);
			
		}
		
		String results = null;
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(TopGamesList);
		System.out.println("{\"items\":" + jsonCartList + "}");
		//results = "{\"items\":" + jsonCartList + "}";
		results = jsonCartList;
		
		return results;
		
		
	}

	/**
	 * Set welcome.html to the home page.
	 * @return	A model and view consisting of the welcome page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getWelcomePage() {
		ModelAndView mav = new ModelAndView("welcome.html");
		return mav;
		//TODO: Add results for tracking
//		return mav;
	}
	
	/**
	 * This is a simple example of how to use a data manager
	 * to retrieve the data and return it as an HTTP response.
	 *
	 * <p>
	 * Note, when it returns from the Spring, it will be
	 * automatically converted to JSON format.
	 * <p>
	 * Try it in your web browser:
	 * 	http://localhost:8080/cs580/user/user101
	 */
	/*@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}*/

	@RequestMapping(value = "/addUserNotification/{userName}/{itemID}/{noticePrice}", method = RequestMethod.POST)
	boolean addNotification(@PathVariable("userName") String uName, @PathVariable("itemID") long itemID, @PathVariable("noticePrice") double nPrice) {
		Notification note = new DBNotification(itemID, nPrice, uName);
		return dbNotificationQuery.addNotification(note);
	}
	
	/*
	 * Get info on what a user is tracking. query 3 tables together and finds lowest price of each item being tracked
	 */
	@RequestMapping(value = "/getUserNotifications/{userName}", method = RequestMethod.GET)
	String getNotification(@PathVariable("userName") String userName) {
//	String getNotification(String userName) {
//		System.out.println("In query");
		List<UserTrackItemjava> userTrackItemList = new ArrayList<UserTrackItemjava>();
		List<Notification> NotificationList = dbNotificationQuery.getNotificationsItemList(userName);
		System.out.println(NotificationList.toString());
		
		long itemID;
		Item currentItem = null;
		UserTrackItemjava lowestPriceObject;
		
		for(Notification x:NotificationList)
		{
			itemID = x.getItemID();
			System.out.println("Item " + itemID);
			currentItem = dbItemQuery.getItem(itemID);
			System.out.println(currentItem.getTitle());
			
			lowestPriceObject = dbNotificationQuery.getNotificationsLowestPrice(itemID);
			System.out.println(lowestPriceObject.getPrice() + "");
			lowestPriceObject.SetNotifyPrice(x.getNotifyPrice());
			lowestPriceObject.SetSystem(currentItem.getSystem());
			lowestPriceObject.SetTitle(currentItem.getTitle());
			lowestPriceObject.SetItemID(itemID);
			userTrackItemList.add(lowestPriceObject);
		}
		
		String results = null;
		Gson gson = new Gson();
		String jsonCartList = gson.toJson(userTrackItemList);
		System.out.println("{\"items\":" + jsonCartList + "}");
		//results = "{\"items\":" + jsonCartList + "}";
		results = jsonCartList;
		
		return results;
	}
	
	@RequestMapping(value = "/deleteNotification/{username}/{itemID}", method = RequestMethod.DELETE)
	void deleteNotification(@PathVariable("username") String uName, @PathVariable("itemID") long itemID) {
		dbNotificationQuery.removeNotification(uName, itemID);
	}
	
	@RequestMapping(value = "/updateNotification/{userName}/{itemID}/{noticePrice}", method = RequestMethod.POST)
	boolean updateNotification(@PathVariable("userName") String uName, @PathVariable("itemID") long itemID, @PathVariable("noticePrice") double nPrice) {
		Notification note = new DBNotification(itemID, nPrice, uName);
		return dbNotificationQuery.updateNotification(note);
	}
	
	/**
	 * This is an example of sending an HTTP POST request to
	 * update a user's information (or create the user if not
	 * exists before).
	 *
	 * You can test this with a HTTP client by sending
	 *  http://localhost:8080/cs580/user/user101
	 *  	name=John major=CS
	 *
	 * Note, the URL will not work directly in browser, because
	 * it is not a GET request. You need to use a tool such as
	 * curl.
	 *
	 * @param id
	 * @param name
	 * @param major
	 * @return
	 */
	/*@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.POST)
	User updateUser(
			@PathVariable("userId") String id,
			@RequestParam("name") String name,
			@RequestParam(value = "major", required = false) String major) {
		User user = new User();
		user.setId(id);
		user.setMajor(major);
		user.setName(name);
		userManager.updateUser(user);
		return user;
	}*/

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	/*@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}*/

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	/*@RequestMapping(value = "/cs580/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}*/

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	/*@RequestMapping(value = "/cs580/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}*/

	
	/***********Assignment 3******************/
	/**
	 * This method is Jose's implementation of a HTTP GET method
	 */
	@RequestMapping(value = "/cs580/joseA3", method = RequestMethod.GET)
	String joseA3() {
		return "This is Jose's implementation of a HTTP GET method.";
	}
	
	/**
	 * This method is Matthew's implementation of a HTTP GET Method
	 */

	@RequestMapping(value = "/cs580/echo/{message}", method = RequestMethod.GET)
	String mattA3(@PathVariable("message") String message) {
		return "Matt: " + message;

	}
	
	/***********Assignment 4******************/
	
	/**
	 * A4 for Claude
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/cs580/claudeA4/{Fname}", method = RequestMethod.GET)
	String claudeA3(@PathVariable("Fname") String Fname) throws SQLException {
		
		// Server is local host, instance is dbo
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
      dataSource.setDriver(new com.mysql.jdbc.Driver());
      dataSource.setUrl("jdbc:mysql://localhost/dbo");
      dataSource.setUsername("root");
      dataSource.setPassword("admin");
      
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
     
      //jdbcTemplate.update("INSERT INTO test (`Fname`, `Lname`, `Age`) VALUES ('Bob', 'Dole', '10')");
      
      String sql = "SELECT Lname FROM test WHERE Fname = ?";
      String Lname = (String)jdbcTemplate.queryForObject(sql, new Object[] {Fname}, String.class);
      
		
		return "Querying " + Fname + ", " + Lname;
	}
	
	/**
	 * Login Logic
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/login/{Pw}/{Uname}", method = RequestMethod.GET)
	int claudeLogin(@PathVariable("Pw") String Pw, @PathVariable("Uname") String Uname, HttpServletResponse response) throws SQLException {
      boolean result = dbUserQuery.loginUser(Uname, Pw);
      
      if (result)
      {
    	  Cookie userCookie = new Cookie("gameTrackerUser", Uname); //bake cookie
    	  userCookie.setMaxAge(86400); //set expire time to 1 day
    	  userCookie.setPath("/");
    	  response.addCookie(userCookie); //put cookie in response 
    	  
    	  return 1;
      }
      return 0;
	}
	
	/**
	 * Recovery Logic
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/recovery/{Un}/{Email}", method = RequestMethod.GET)
	int recovery(@PathVariable("Un") String Uname, @PathVariable("Email") String Email) throws SQLException {
		int tempChecker = 0;
		User user = dbUserQuery.getUser(Uname);
		
		//TODO: Some sort of recovery stuff.
      if(tempChecker != 1)
      {
      	return 0;
      }
      else
      	return 1;
      
      
      
	}
	
	
	
	
	/**
	 * Create Account Logic
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/newaccount/{Email}/{Pw}/{Uname}", method = RequestMethod.GET)
	int claudeLogin(@PathVariable("Email") String Email, @PathVariable("Pw") String Pw, @PathVariable("Uname") String Uname) throws SQLException {
		User newUser = new DBUser(Uname, Email, Pw);
		return dbUserQuery.addUser(newUser);
	}
	
	/**
	 * This method is Jose's implementation of Google Guava for Assignment 4.
	 * Given a message, it encodes it using the Guava SHA512 algorithm
	 */
	@RequestMapping(value = "/CS580/JoseA4/{message}", method = RequestMethod.GET)
	String joseA4(@PathVariable("message") String message) {
		//HashCode hash = HashCode.fromString(message);
		HashFunction function = Hashing.sha512();
		Hasher hasher = function.newHasher();		
		hasher.putString(message, Charset.defaultCharset());
		HashCode code = hasher.hash();
		return code.toString();
	}
	
	/**
	 * This method is Matthew's implementation of a Java JSON parser library for Assignment 4.
	 * Builds a rudimentary JSON object and returns it and its XML equivalent.
	 */
	@RequestMapping(value = "/CS580/MattA4", method = RequestMethod.GET)
	String mattA4() {
		JSONObject test = new JSONObject();
		JSONArray membersArray = new JSONArray();
		test.put("members", membersArray);
		JSONObject matt = new JSONObject();
		matt.put("name", "Matthew Lai");
		matt.put("university", "CPP");
		matt.put("name_hash", "Matthew Lai".hashCode());
		JSONObject claude = new JSONObject();
		claude.put("name", "Claude Phan");
		claude.put("university", "CPP");
		claude.put("name_hash", "Claude Phan".hashCode());
		JSONObject jose = new JSONObject();
		jose.put("name", "Jose Figueroa");
		jose.put("university", "CPP");
		jose.put("name_hash", "Jose Figueroa".hashCode());
		membersArray.put(matt);
		membersArray.put(claude);
		membersArray.put(jose);
		
		return test.toString() + "<hr><xmp>" + org.json.XML.toString(test) + "</xmp>";
	}
	
	
	/***********Project Mappings******************/
	
	@RequestMapping(value = "/parser/steam/{url}", method = RequestMethod.GET)
	String getSteamInfo(@PathVariable("url") String url) {
		String retString = "";
		edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage steamJSONPage = new edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage(Integer.parseInt(url));
		edu.cpp.cs580.webdata.parser.GamePageInfo wPI = steamJSONPage.getWebPageInfo();
		retString += "Current Price: $" + wPI.getCurrentPrice() + "<hr>";
		
		for(String key : new java.util.TreeSet<String>(wPI.getGameAttributes().keySet())) {
			retString += key.substring(0,key.lastIndexOf("_")) + ": ";
			String type = key.substring(key.lastIndexOf("_") + 1);
			switch(type)
			{
				case "string":
					retString += (String)wPI.getGameAttributes().get(key);
					break;
				case "double":
					retString += (Double)wPI.getGameAttributes().get(key);
					break;
				case "list":
					List<String> list = (List<String>)wPI.getGameAttributes().get(key);
					for(String value : list) {
						retString += value;
						if(!list.get(list.size()-1).equals(value)) retString += ", "; 
					}
					break;
			}
			retString += "<hr>";
		}
//		System.out.println(retString); 
		return retString;
	}
}


















