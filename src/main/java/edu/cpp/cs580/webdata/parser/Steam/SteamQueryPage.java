package edu.cpp.cs580.webdata.parser.Steam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.cpp.cs580.webdata.parser.QueryPage;

public class SteamQueryPage extends QueryPage {

	private final static String BASEURL = "http://store.steampowered.com/search/?snr=1_4_4__12&term=";
	LinkedHashMap<String,String> linksMap;
	public SteamQueryPage(String query) {
		super(query);
		linksMap = new LinkedHashMap<>();
		parseQueryPage();
	}
	
	private void parseQueryPage()
	{
		try {
			Document doc = Jsoup.connect(getPageURL()).timeout(600000).maxBodySize(0)
					.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").cookie("birthtime", "568022401")
			        .get();
			Elements result = doc.select("div#search_result_container").select("a[href]");
			result.forEach(item -> linksMap.put(item.select("span.title").first().text(), item.attr("href")));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public List<String> getNames()
	{
		List<String> names = new ArrayList<>();
		names.addAll(linksMap.keySet());
		return names;
	}
	
	public String getLink(String name)
	{
		return linksMap.get(name);
	}

	@Override
	protected String getURL(String query) {
		try {
			return BASEURL+URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) { throw new RuntimeException("This shouldn't happen.", e); }
	}
	
	public static void main(String[] args) {
		SteamQueryPage test = new SteamQueryPage("assassin's creed unity");
		List<String> list = test.getNames();
		list.forEach(n -> System.out.println(n + "\t" +test.getLink(n)));
	}
}
