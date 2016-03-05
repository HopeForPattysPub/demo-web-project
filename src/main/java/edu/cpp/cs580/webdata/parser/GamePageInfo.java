package edu.cpp.cs580.webdata.parser;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class GamePageInfo {

	protected Double currentPrice;

	@Deprecated
	protected Map<String, Object> gameAttributes;
	
//	public WebPageInfo()
//	{
//		currentPrice = 0.0;
//		gameAttributes = new HashMap<>();
//	}
	
	public GamePageInfo(double price, Map<String,Object> att)
	{
		currentPrice = price;
		gameAttributes = att;
	}
	
	public Double getCurrentPrice() { return currentPrice; }
	
	/**
	 * Not a good way of doing this since it is awkward to retrieve data from the Map.
	 * DataPage should return any extra data that might be needed to inform the DB of 
	 * game properties.
	 * 
	 * @return
	 */
	@Deprecated
	public Map<String, Object> getGameAttributes() { return gameAttributes; }
	
}
