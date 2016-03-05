package edu.cpp.cs580.webdata.parser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class GameDataPage extends WebPage {

	protected GamePageInfo webPageInfo;
	protected String gameName;
	protected String storeID;
	protected Double gamePrice = null;
	
	public String getStoreID() { return storeID; }
	
	public GameDataPage(String url, String storeID)
	{
		super(url);
		this.gameName = storeID;
		parseWebPage(url);
		if(webPageInfo == null)
			try {
				throw new WebPageInfoNotInitializedException();
			} catch (WebPageInfoNotInitializedException e) {
				e.printStackTrace();
			}
		if(gamePrice == null|| rawData == null || pageURL == null || gameName == null)
			try {
				throw new ParserNotCompleteException();
			} catch (ParserNotCompleteException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Initialize Double price, String description, and (optionally) Map<String, String> misc
	 * with data parsed from the jsoup Document doc.
	 */
	public abstract void parseWebPage(String url);
	
	public double getPrice()
	{
		return gamePrice;
	}
	
	public GamePageInfo getWebPageInfo()
	{
		return webPageInfo;
	}
	
	public String getRawPageData()
	{
		return rawData;
	}
	
	public String getPageURL()
	{
		return pageURL;
	}
	
	public String getGameName()
	{
		return gameName;
	}

}
