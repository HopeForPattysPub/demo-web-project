package edu.cpp.cs580.webdata.parser;

public class ParserNotCompleteException extends Exception {

	private static final long serialVersionUID = 1839272363943742983L;
	
	public ParserNotCompleteException()
	{
		super("The parser for this webpage has not initialized all of the necessary fields. Check the DataPage and ensure that all data has been initialized.");
	}

}
