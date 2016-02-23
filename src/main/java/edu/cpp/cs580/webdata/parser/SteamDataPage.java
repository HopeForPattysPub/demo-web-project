package edu.cpp.cs580.webdata.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SteamDataPage extends GameDataPage {

	public SteamDataPage(String url) throws WebPageInfoNotInitializedException, IOException, ParserNotCompleteException {
//		super("http://store.steampowered.com/app/368500/");
//		super("http://store.steampowered.com/api/appdetails?appids=57690");
//		super("http://store.steampowered.com/stats/?l=english");
		super(url);
	}

	public void parseWebPage(String url) {

		Map<String, Object> gameAttributes = new HashMap<>();
		Double currentPrice = 0.0;
//		WebDriver driver = new FirefoxDriver();
//		driver.get(url);
//		String html_c = driver.getPageSource();
//		driver.close();
//		System.out.println(html_c);
		try {
			Document doc = Jsoup.connect(url).timeout(600000).maxBodySize(0)
					.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").cookie("birthtime", "568022401")
			        .get();
//			Elements priceE = doc.select("div.game_purchase_price.price");
			Elements resultLinks = doc.select("div.game_purchase_action_bg > *");
			System.out.println(resultLinks.toString());
//			System.out.println(priceE.text());
//			System.out.println(doc.html());
			
			rawData = doc.html();
			pageURL = url;
			gameName = doc.select("span[itemprop=name]").text().replaceAll("[^\\s\\d\\w]", "");
			
		} catch (IOException e) { e.printStackTrace(); }
		
		webPageInfo = new GamePageInfo(currentPrice, gameAttributes);
		
	}
	
	public static void main(String[]args) throws ParserNotCompleteException, IOException, ParserNotCompleteException, WebPageInfoNotInitializedException
	{
		new SteamDataPage("http://store.steampowered.com/app/368500/");
//		System.out.println(org.apache.commons.lang3.StringUtils.getJaroWinklerDistance("Assassin's Creed Black Flag", "Assassins Creed Black Flag"));
//		System.out.println(org.apache.commons.lang3.StringUtils.getJaroWinklerDistance("Assassin's Creed Black Flag", "Assassin's Creed $ Black Flag"));
//		String html = "<div class=\"game_purchase_action\">\n"+
//						"<div class=\"game_purchase_action_bg\">\n"+
//															"<div class=\"game_purchase_price price\">\n"+
//							"$59.99						</div>";
//		org.jsoup.nodes.Document document = Jsoup.parse(html);
//		Elements elements = document.select("div.game_purchase_price.price");
//		System.out.println(elements.text()); // foo
	}

}
