package edu.cpp.cs580.Database;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cpp.cs580.Database.Objects.DBItem;
import edu.cpp.cs580.Database.Objects.DBNotification;
import edu.cpp.cs580.Database.Objects.DBPriceHistory;
import edu.cpp.cs580.Database.Objects.DBStore;
import edu.cpp.cs580.Database.Objects.DBStoreProduct;
import edu.cpp.cs580.Database.Objects.DBSystem;
import edu.cpp.cs580.Database.Objects.DBUser;

public class JSONConverter {
	public static enum JsonClassType {
		ITEM(DBItem.class), NOTIFICATION(DBNotification.class), PRICE_HISTORY(DBPriceHistory.class), STORE(DBStore.class)
		, STORE_PRODUCT(DBStoreProduct.class), SYSTEM(DBSystem.class), USER(DBUser.class);
		private Class<?> classType;
		
		/**
		 * Constructor for the enum type. Sets the classType to the given parameter
		 * @param cType	Class type
		 */
		private JsonClassType(Class<?> cType) {
			classType = cType;
		}
		
		/**
		 * Get the stored class type
		 * @return	Stored class type
		 */
		public Class<?> getClassType() {
			return classType;
		}
	}
	/**
	 * Converts a JSON string to the appropriate object. This method is used locally by the
	 * class.
	 * @param json		JSON string that will be converted
	 * @param classType	Class that JSON will be used for conversion
	 * @return			Object of type classType or Null if it failed
	 */
	public static Object jsonToObject(String json, JsonClassType classType) {
		ObjectMapper om = new ObjectMapper();
		Object result = null;
		
		try {
			result = om.readValue(json, classType.getClassType());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Converts and Object into a JSON string
	 * @param obj	Object to convert
	 * @return		JSON representation of object or NULL if failed
	 */
	public static String objectToJson(Object obj) {
		ObjectMapper om = new ObjectMapper();
		String result = null;
		try {
			result = om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
