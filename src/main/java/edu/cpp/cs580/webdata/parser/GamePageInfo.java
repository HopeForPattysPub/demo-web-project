package edu.cpp.cs580.webdata.parser;

import java.util.HashMap;
import java.util.Map;

public class GamePageInfo {

	protected Double currentPrice;
//	protected String description;
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
	
	public Map<String, Object> getGameAttributes() { return gameAttributes; }
	
}
