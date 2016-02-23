package edu.cpp.cs580.webdata.parser;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import edu.cpp.cs580.webdata.parser.*;


public class SteamJSONDataPageTest {
	
	@Test
	public void steamJSONDataPageTest()
	{
		try {
			SteamJSONDataPage dataPage = new SteamJSONDataPage("http://store.steampowered.com/api/appdetails/?appids=368500");
			
			Assert.assertEquals("Assassin's Creed® Syndicate", dataPage.getGameName());
			Assert.assertEquals(dataPage.getPageURL(), "http://store.steampowered.com/api/appdetails/?appids=368500" );
			
		} catch (WebPageInfoNotInitializedException | IOException | ParserNotCompleteException e) { e.printStackTrace(); }
		
	}
}
