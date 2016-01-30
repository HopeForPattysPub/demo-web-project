package edu.cpp.cs580.webdata.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.javascript.host.media.rtc.webkitRTCPeerConnection;

public class SteamJSONDataPage extends DataPage {

	public SteamJSONDataPage(String url) throws WebPageInfoNotInitializedException, IOException, ParserNotCompleteException {
//		super("http://store.steampowered.com/app/368500/");
//		super("http://store.steampowered.com/api/appdetails?appids=368500");
//		super("http://store.steampowered.com/stats/?l=english");
		super(url);
	}
	
	public void parseWebPage(int appid)
	{
		Double currentPrice = 0.0;
		Map<String,Object> gameAttributes = new HashMap<>();
		URL urlObject;
		Scanner scan;
		String str = "";
		String url = "http://store.steampowered.com/api/appdetails?appids=" + appid;
		try {			
			urlObject = new URL(url);
			scan = new Scanner(urlObject.openStream());
			while(scan.hasNext()) str+= scan.nextLine();
			scan.close();
		} catch (IOException e) { e.printStackTrace(); }
		
		JSONObject obj = new JSONObject(str);
		if(obj.getJSONObject(""+appid).getBoolean("success") == false) return;
		JSONObject results = obj.getJSONObject(""+appid).getJSONObject("data");
		currentPrice = (double)results.getJSONObject("price_overview").getInt("final")/100.0;
		
		gameAttributes.put("description_string", results.getString("about_the_game"));
		gameAttributes.put("languages_string", results.getString("supported_languages"));
		if(results.has("pc_requirements"))
		{
			if(results.getJSONObject("pc_requirements").has("minimum"))
				gameAttributes.put("pc_requirements_min_string", results.getJSONObject("pc_requirements").getString("minimum"));
			if(results.getJSONObject("pc_requirements").has("recommended"))
				gameAttributes.put("pc_requirements_rec_string", results.getJSONObject("pc_requirements").getString("recommended"));
		}
		gameAttributes.put("initial_price_double", (double)results.getJSONObject("price_overview").getInt("initial")/100.0);
		gameAttributes.put("release_date_string", results.getJSONObject("release_date").getString("date"));
		
		Iterator<Object> pIt = results.getJSONArray("publishers").iterator();
		List<String> pList = new ArrayList<>();
		while(pIt.hasNext()) pList.add((String)pIt.next());
		gameAttributes.put("publishers_list", pList);
		
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

		Iterator<Object> genreIt = results.getJSONArray("genres").iterator();
		List<String> genreList = new ArrayList<>();
		while(genreIt.hasNext()) genreList.add(((JSONObject)genreIt.next()).getString("description"));
		gameAttributes.put("genre_list", genreList);

		Iterator<Object> screenIt = results.getJSONArray("screenshots").iterator();
		List<String> screenList = new ArrayList<>();
		while(screenIt.hasNext()) screenList.add(((JSONObject)screenIt.next()).getString("path_full"));
		gameAttributes.put("screenshot_list", screenList);
		
		pageURL = url;
		rawData = str;
		gameName = results.getString("name");
		webPageInfo = new WebPageInfo(currentPrice, gameAttributes); 
	}

	@Override
	public void parseWebPage(String url) {
		int appid = Integer.parseInt(url.substring(url.indexOf("=")+1).replaceAll("[/\\\\]", ""));
		parseWebPage(appid);
	}
	
	public static void main(String[] args) throws ParserNotCompleteException, IOException, ParserNotCompleteException, WebPageInfoNotInitializedException {

		new SteamJSONDataPage("http://store.steampowered.com/api/appdetails?appids=368500/");
	}

}
