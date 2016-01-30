package edu.cpp.cs580.webdata.parser;

public class WebPageInfoNotInitializedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2928525739167512368L;
	public WebPageInfoNotInitializedException()
	{
		super("WebPageInfo object is not initialized by this parser.");
	}

}
