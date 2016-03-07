package edu.cpp.cs580.webdata.parser;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import edu.cpp.cs580.webdata.parser.*;
import edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage;


public class SteamJSONDataPageTest {
	
	@Test
	public void steamJSONDataPageTest()
	{
		SteamJSONDataPage dataPage = new SteamJSONDataPage(368500);
		
//		Assert.assertEquals("Assassin's Creed® Syndicate", dataPage.getGameName());
//		Assert.assertEquals(dataPage.getPageURL(), "http://store.steampowered.com/api/appdetails/?appids=368500" );
		
	}
}
