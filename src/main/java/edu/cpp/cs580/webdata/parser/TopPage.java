package edu.cpp.cs580.webdata.parser;

public abstract class TopPage extends WebPage {
	
	private java.util.List<Integer> topGameIDList;
	
	public TopPage() {
		super();
		topGameIDList = getTopList();
		grabRawData();
	}
	
	public java.util.List<Integer> getTopGameIDList()
	{
		return new java.util.ArrayList<Integer>(topGameIDList);
	}
	
	/**
	 * Takes a URL of a top games page for a games service and returns a 
	 * 
	 * @param url The URL of the top games page for a game service
	 * @return List containing Game Name and Game ID
	 */
	protected abstract java.util.List<Integer> getTopList();

}
