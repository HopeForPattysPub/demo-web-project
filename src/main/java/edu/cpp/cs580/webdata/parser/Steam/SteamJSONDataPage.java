package edu.cpp.cs580.webdata.parser.Steam;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.javascript.host.media.rtc.webkitRTCPeerConnection;

import edu.cpp.cs580.webdata.parser.GameDataPage;
import edu.cpp.cs580.webdata.parser.GamePageInfo;
import edu.cpp.cs580.webdata.parser.ParserNotCompleteException;
import edu.cpp.cs580.webdata.parser.WebPageInfoNotInitializedException;

public class SteamJSONDataPage extends GameDataPage {
	
	private String gameDescription, releaseDate, gameMinReq, gameRecReq;
	private List<String> gameLanguages, gamePublishers, gameCategories, gameGenres, gameScreenshotURLs;
	private Double gameInitialPrice;
	
	public String getDescription() { return gameDescription; }
	public String getReleaseDate() { return releaseDate; }
	public String getMinimumRequirements() { return gameMinReq; }
	public String getRecommnededRequirements() { return gameRecReq; }
	public List<String> getLanguages() { return gameLanguages; }
	public List<String> getPublishers() { return gamePublishers; }
	public List<String> getCategories() { return gameCategories; }
	public List<String> getGenres() { return gameGenres; }
	public List<String> getScreenshots() { return gameScreenshotURLs; }
	public double getInitialPrice() { return gameInitialPrice; }
	
		
	public SteamJSONDataPage(String url) throws WebPageInfoNotInitializedException, IOException, ParserNotCompleteException {
//		super("http://store.steampowered.com/app/368500/");
//		super("http://store.steampowered.com/api/appdetails?appids=368500");
//		super("http://store.steampowered.com/stats/?l=english");//365590
		super("http://store.steampowered.com/api/appdetails?appids=" + url);
	}
	
	public void parseWebPage(int appid)
	{
		Double currentPrice = 0.0;
		Map<String,Object> gameAttributes = new HashMap<>();
		URL urlObject;
		Scanner scan;
//		String str = "";
//		String url = "http://store.steampowered.com/api/appdetails?appids=" + appid;
//		try {			
//			urlObject = new URL(url);
//			scan = new Scanner(urlObject.openStream());
//			while(scan.hasNext()) str+= scan.nextLine();
//			scan.close();
//		} catch (IOException e) { e.printStackTrace(); }
		
//		JSONObject obj = new JSONObject(str);
		JSONObject obj = new JSONObject(getRawPageData());
		if(obj.getJSONObject(""+appid).getBoolean("success") == false) return;
		JSONObject results = obj.getJSONObject(""+appid).getJSONObject("data");
		currentPrice = (double)results.getJSONObject("price_overview").getInt("final")/100.0;
		
		gameAttributes.put("description_string", results.getString("about_the_game"));
		gameDescription = results.getString("about_the_game");
		
		gameAttributes.put("languages_string", results.getString("supported_languages"));
		gameLanguages = new ArrayList<>();
		Arrays.asList(results.getString("supported_languages").split(","))
			.forEach(s -> gameLanguages.add(s.trim().replace("<strong>*</strong>", "").replace("<br>languages with full audio support", "")));
		
		if(results.has("pc_requirements"))
		{
			gameMinReq = null;
			gameRecReq = null;
			if(results.getJSONObject("pc_requirements").has("minimum"))
			{
				gameAttributes.put("pc_requirements_min_string", results.getJSONObject("pc_requirements").getString("minimum"));
				gameMinReq = results.getJSONObject("pc_requirements").getString("minimum");
			}
			if(results.getJSONObject("pc_requirements").has("recommended"))
			{
				gameAttributes.put("pc_requirements_rec_string", results.getJSONObject("pc_requirements").getString("recommended"));
				gameRecReq = results.getJSONObject("pc_requirements").getString("recommended");
			}
		}
		
		gameAttributes.put("initial_price_double", (double)results.getJSONObject("price_overview").getInt("initial")/100.0);
		gameInitialPrice = (double)results.getJSONObject("price_overview").getInt("initial")/100.0;
		
		gameAttributes.put("release_date_string", results.getJSONObject("release_date").getString("date"));
		releaseDate = results.getJSONObject("release_date").getString("date");
		
		Iterator<Object> pIt = results.getJSONArray("publishers").iterator();
		List<String> pList = new ArrayList<>();
		while(pIt.hasNext()) pList.add((String)pIt.next());
		gameAttributes.put("publishers_list", pList);
		gamePublishers = new ArrayList<>(pList);
		
		Iterator<Object> edIt = ((JSONObject)results.getJSONArray("package_groups").get(0)).getJSONArray("subs").iterator();
		int count = 0;
		while(edIt.hasNext())
		{
			count++;
			JSONObject edition = (JSONObject)edIt.next();
			String editionName = edition.getString("option_text").split(" - ")[0];
			double editionPrice = (double)edition.getInt("price_in_cents_with_discount")/100.0;
			gameAttributes.put("edition_name_" + count + "_string", editionName);
			gameAttributes.put("edition_price_" + count + "_double", editionPrice);
		}
		
		Iterator<Object> catIt = results.getJSONArray("categories").iterator();
		List<String> catList = new ArrayList<>();
		while(catIt.hasNext()) catList.add(((JSONObject)catIt.next()).getString("description"));
		gameAttributes.put("categories_list", catList);
		gameCategories = new ArrayList<>(catList);

		Iterator<Object> genreIt = results.getJSONArray("genres").iterator();
		List<String> genreList = new ArrayList<>();
		while(genreIt.hasNext()) genreList.add(((JSONObject)genreIt.next()).getString("description"));
		gameAttributes.put("genre_list", genreList);
		gameGenres = new ArrayList<>(genreList);
		
		Iterator<Object> screenIt = results.getJSONArray("screenshots").iterator();
		List<String> screenList = new ArrayList<>();
		while(screenIt.hasNext()) screenList.add(((JSONObject)screenIt.next()).getString("path_full"));
		gameAttributes.put("screenshot_list", screenList);
		gameScreenshotURLs = new ArrayList<>(screenList);
		
//		pageURL = url;
//		rawData = str;
		gameName = results.getString("name");
		webPageInfo = new GamePageInfo(currentPrice, gameAttributes); 
		gamePrice = currentPrice;
	}

	@Override
	public void parseWebPage(String url) {
		int appid = Integer.parseInt(url.substring(url.indexOf("=")+1).replaceAll("[/\\\\]", ""));
		parseWebPage(appid);
	}
	
	public static void main(String[] args) throws ParserNotCompleteException, IOException, ParserNotCompleteException, WebPageInfoNotInitializedException 
	{
//		System.out.println("test");
//		List<String> ret = new ArrayList<>();
//		new SteamJSONDataPage("http://store.steampowered.com/api/appdetails/?appids=368500");
//		GamePageInfo info = (new SteamJSONDataPage("365590")).getWebPageInfo();
//		System.out.println(info.getCurrentPrice());
//		info.getGameAttributes().keySet().forEach(k ->{
//			System.out.println(k + ": " + info.getGameAttributes().get(k);
//		});
		edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage steamJSONPage = new edu.cpp.cs580.webdata.parser.Steam.SteamJSONDataPage("365590");
		edu.cpp.cs580.webdata.parser.GamePageInfo wPI = steamJSONPage.getWebPageInfo();
		String retString = "";
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
		System.out.println(retString);

		
		//English<strong>*</strong>, French<strong>*</strong>, Italian<strong>*</strong>, German<strong>*</strong>, Spanish<strong>*</strong>, Czech, Dutch, Hungarian, Japanese<strong>*</strong>, Korean, Polish, Portuguese-Brazil<strong>*</strong>, Russian<strong>*</strong>, Simplified Chinese, Traditional Chinese<br><strong>*</strong>languages with full audio support
		
		
	}

}
