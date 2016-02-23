package edu.cpp.cs580.webdata.parser;

public abstract class QueryPage extends WebPage {
	
	public QueryPage(String query) {
		super();
		pageURL = getURL(query);
		grabRawData();
	}
	
	protected abstract String getURL(String query);

}
