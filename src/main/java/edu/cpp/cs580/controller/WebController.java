package edu.cpp.cs580.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.net.HostAndPort;
import com.google.common.net.HostSpecifier;

import edu.cpp.cs580.App;
import edu.cpp.cs580.data.User;
import edu.cpp.cs580.data.provider.UserManager;
import edu.cpp.cs580.webdata.parser.ParserNotCompleteException;
import edu.cpp.cs580.webdata.parser.WebPageInfoNotInitializedException;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
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
	@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.GET)
	User getUser(@PathVariable("userId") String userId) {
		User user = userManager.getUser(userId);
		return user;
	}

	/*@RequestMapping(value = "/{userName}/userLanding", method = RequestMethod.GET)
	String geUserName(@PathVariable("userName") String UN) {
		return UN;
	}*/
	
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
	@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.POST)
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
	}

	/**
	 * This API deletes the user. It uses HTTP DELETE method.
	 *
	 * @param userId
	 */
	@RequestMapping(value = "/cs580/user/{userId}", method = RequestMethod.DELETE)
	void deleteUser(
			@PathVariable("userId") String userId) {
		userManager.deleteUser(userId);
	}

	/**
	 * This API lists all the users in the current database.
	 *
	 * @return
	 */
	@RequestMapping(value = "/cs580/users/list", method = RequestMethod.GET)
	List<User> listAllUsers() {
		return userManager.listAllUsers();
	}

	/*********** Web UI Test Utility **********/
	/**
	 * This method provide a simple web UI for you to test the different
	 * functionalities used in this web service.
	 */
	@RequestMapping(value = "/cs580/home", method = RequestMethod.GET)
	ModelAndView getUserHomepage() {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("users", listAllUsers());
		return modelAndView;
	}

	
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
	int claudeLogin(@PathVariable("Pw") String Pw, @PathVariable("Uname") String Uname) throws SQLException {
		
		// Server is local host, instance is dbo
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
      dataSource.setDriver(new com.mysql.jdbc.Driver());
      dataSource.setUrl("jdbc:mysql://localhost/awsdb");
      dataSource.setUsername("root");
      dataSource.setPassword("admin");
      
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      String pwChecker;
      Integer tempChecker;
      String sql;
      //check if Uname exists
    
      sql = "SELECT count(UserPassword) FROM awsdb.users WHERE Username = ?";
      tempChecker = (Integer) jdbcTemplate.queryForObject(sql, new Object[] {Uname}, Integer.class);
      System.out.println("UnCheck " + tempChecker + " login: " + Uname);
      if(tempChecker != 1)
      {
      	return 0;
      }
      
      //Check PW
      sql = "SELECT UserPassword FROM awsdb.Users WHERE Username = ?";
      pwChecker = (String)jdbcTemplate.queryForObject(sql, new Object[] {Uname}, String.class);
      System.out.println("DB Pw lookup: " + pwChecker);
      System.out.println("Input pw: " + Pw);
      if(!pwChecker.equals(Pw))
      {
      	return 0;
      }
      else
      {
      	return 1;
      }
      
	}
	
	/**
	 * Recovery Logic
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/recovery/{Un}/{Email}", method = RequestMethod.GET)
	int recovery(@PathVariable("Un") String Uname, @PathVariable("Email") String Email) throws SQLException {
		
		// Server is local host, instance is dbo
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
      dataSource.setDriver(new com.mysql.jdbc.Driver());
      dataSource.setUrl("jdbc:mysql://localhost/awsdb");
      dataSource.setUsername("root");
      dataSource.setPassword("admin");
      
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      Integer tempChecker;
      String sql;
      //check if Uname exists
    
      sql = "SELECT count(UserPassword) FROM awsdb.users WHERE Username = ? and Email = ?";
      tempChecker = (Integer) jdbcTemplate.queryForObject(sql, new Object[] {Uname, Email}, Integer.class);
      System.out.println("UnCheck " + tempChecker + "UN: " + Uname + " Email: " + Email);
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
		System.out.println("inside web");
		// Server is local host, instance is dbo
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
      dataSource.setDriver(new com.mysql.jdbc.Driver());
      dataSource.setUrl("jdbc:mysql://localhost/awsdb");
      dataSource.setUsername("root");
      dataSource.setPassword("admin");
      
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      Integer tempChecker;
      
      //Check UN
      String sql = "SELECT count(Email) FROM awsdb.users WHERE Username = ?";
      tempChecker = (Integer) jdbcTemplate.queryForObject(sql, new Object[] {Uname}, Integer.class);
      //System.out.println(tempChecker);
      
      if(tempChecker != 0)
      {
      	return 0;
      }
      
      //Check Email
      sql = "SELECT count(Username) FROM awsdb.users WHERE Email = ?";
      tempChecker = (Integer) jdbcTemplate.queryForObject(sql, new Object[] {Email}, Integer.class);
      System.out.println(tempChecker);
      
      if(tempChecker != 0)
      {
      	return 1;
      }
      
      
      //Add user to DB
      jdbcTemplate.update(
      	    "INSERT INTO awsdb.Users (Username, Email, UserPassword) VALUES (?, ?, ?)",
      	    new Object[]{Uname, Email, Pw}
      );
      
		
		return 3;
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
		try {
			edu.cpp.cs580.webdata.parser.SteamJSONDataPage steamJSONPage = new edu.cpp.cs580.webdata.parser.SteamJSONDataPage(url);
			edu.cpp.cs580.webdata.parser.WebPageInfo wPI = steamJSONPage.getWebPageInfo();
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
			
		} catch (WebPageInfoNotInitializedException | IOException | ParserNotCompleteException e) { System.out.println("Parser Implemented Wrong. Sorry About That."); System.exit(1); }
//		System.out.println(retString);
		return retString;
	}
}


















