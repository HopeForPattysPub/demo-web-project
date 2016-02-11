package edu.cpp.cs580.Database.Queries;

import java.math.BigInteger;
import java.util.Map;

import edu.cpp.cs580.Database.Objects.Interfaces.StoreProduct;
import edu.cpp.cs580.Database.Queries.Interface.StoreProductQuery;

public class DBStoreProductQuery implements StoreProductQuery {

	@Override
	public boolean addStoreProduct(StoreProduct newProduct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StoreProduct getSingleProduct(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<BigInteger, StoreProduct> getStoreProducts(int storeID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, StoreProduct> getStoreProducts(BigInteger itemID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeStoreProduct(int storeID, BigInteger itemID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateStoreProduct(StoreProduct product) {
		// TODO Auto-generated method stub
		return false;
	}

}
