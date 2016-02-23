package edu.cpp.cs580.webdata.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class WebPage {

	String pageURL;
	String rawData;
	
	public WebPage(String URL)
	{
		pageURL = URL;
		grabRawData();
	}
	
	public WebPage() {
		pageURL = null;
	}
	
	public String getRawPageData() { return rawData; }

	protected void grabRawData()
	{
		URL urlObject;
		Scanner scan;
		String str = "";
		try {
			urlObject = new URL(pageURL);
			scan = new Scanner(urlObject.openStream());
			while(scan.hasNext()) str+= scan.nextLine();
			scan.close();
		} catch (IOException e) { e.printStackTrace(); }
		rawData = str;
	}
	
}
