package edu.cpp.cs580.Database;

import org.junit.Test;

import edu.cpp.cs580.Database.Objects.DBSystem;
import edu.cpp.cs580.Database.Objects.Interfaces.Systems;
import edu.cpp.cs580.Database.Queries.DBSystemQuery;
import edu.cpp.cs580.Database.Queries.Interface.SystemQuery;
import org.junit.Assert;

public class DBSystemQueryTest {
	@Test
	public void addSystemTest() {
		SystemQuery sq = new DBSystemQuery();
		
		//Add a System
		Systems system = new DBSystem("XBOX360", "XBOX 360");
		boolean result = sq.addSystem(system);
		Assert.assertEquals(true, result);
		
		//Get a system
		system = sq.getSystem("XBOX360");
		Assert.assertEquals("XBOX 360", system.getSystemName());
		
		system = new DBSystem("XBOX360", "XBX");
		result = sq.updateSystem(system);
		Assert.assertEquals(true, result);
		
		//Remove system
		result = sq.removeSystem("XBOX360");
		Assert.assertEquals(true, result);
	}
}
