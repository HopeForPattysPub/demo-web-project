package edu.cpp.cs580.Database.Queries;

import java.math.BigInteger;
import java.util.List;

import edu.cpp.cs580.Database.Objects.Interfaces.PriceHistory;
import edu.cpp.cs580.Database.Queries.Interface.PriceHistoryQuery;

public class DBPriceHistoryQuery implements PriceHistoryQuery {

	@Override
	public boolean addHistory(PriceHistory newHistory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PriceHistory> getHistory(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

}
