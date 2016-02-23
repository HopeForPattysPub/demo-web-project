package edu.cpp.cs580.webdata.parser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class GameDataPage extends WebPage {

//	protected Double currentPrice;
//	protected Map<String, Object> gameAttributes;
	protected GamePageInfo webPageInfo;
	protected String rawData;
	protected String pageURL;
	protected String gameName;
	
	public GameDataPage(String url) throws WebPageInfoNotInitializedException, IOException, ParserNotCompleteException
	{
		super(url);
		parseWebPage(url);
		if(webPageInfo == null) throw new WebPageInfoNotInitializedException();
		if(rawData == null || pageURL == null || gameName == null) throw new ParserNotCompleteException();
	}
	
	/**
	 * Initialize Double price, String description, and (optionally) Map<String, String> misc
	 * with data parsed from the jsoup Document doc.
	 */
	public abstract void parseWebPage(String url);
	
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
