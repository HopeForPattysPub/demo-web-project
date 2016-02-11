package edu.cpp.cs580.Database.Queries.Interface;

import edu.cpp.cs580.Database.Objects.Interfaces.Systems;

public interface SystemQuery {
	/**
	 * Add a System to the database. SystemID must be unique.
	 * @param system	System to add
	 * @return			True if addition is successful, false otherwise
	 */
	public boolean addSystem(Systems system);
	/**
	 * Get system from the given systemID.
	 * @param systemID	SystemID of system
	 * @return			System object or NULL if not found
	 */
	public Systems getSystem(String systemID);
	/**
	 * Remove the system from the given systemID
	 * @param systemID	SystemID of system
	 * @return			True if removal is successful, false otherwise
	 */
	public boolean removeSystem(String systemID);
	/**
	 * Update system from the given systemID. Only updates the system name.
	 * @param system	System to update
	 * @return			True if update is successful, false otherwise
	 */
	public boolean updateSystem(Systems system);
}
