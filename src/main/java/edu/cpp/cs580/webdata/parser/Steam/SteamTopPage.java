package edu.cpp.cs580.webdata.parser.Steam;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import edu.cpp.cs580.webdata.parser.TopPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Returns AppID of the top 25 games from Steam's Top Sellers List.
 * 
 * @author Matthew Lai
 *
 */
public class SteamTopPage extends TopPage {

	//"http://store.steampowered.com/search/?filter=topsellers#sort_by=_ASC&page=1";

	private Set<Integer> parseTopPage(String url)
	{
		Set<Integer> retList = new HashSet<>();
		try {
			Document doc = Jsoup.connect(url).timeout(600000).maxBodySize(0)
					.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").cookie("birthtime", "568022401")
			        .get();
			Elements result = doc.select("div#search_result_container").select("a[data-ds-appid]");
			result.forEach(item -> {
				String appids = item.select("a").first().attr("data-ds-appid");
				List<String> split = new ArrayList<>(Arrays.asList(appids.split(",")));				
				if(split.size() < 4) 
					split.forEach(s -> retList.add(Integer.parseInt(s)));
			});
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println(url + "\t" + retList.size());
		return retList;
	}
	
	@Override
	protected List<Integer> getTopList() {
		Set<Integer> retList = new HashSet<>();
		for (int i = 1; i <= 4; i++) {
			String url = "http://store.steampowered.com/search/?filter=topsellers#sort_by=_ASC&page=" + i;
			retList.addAll(parseTopPage(url));
		}
		return new ArrayList<>(retList);
	}
	
	public static void main(String[] args) {
		SteamTopPage test = new SteamTopPage();
		test.getTopGameIDList().forEach(i -> System.out.println(i));
		
	}
}
