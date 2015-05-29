
public interface IGameOptions {
	/**
	 * Get a specific profile with the given name which has been saved previously
	 * @param name Name of profile
	 * @return Profile requested
	 */
    public Profile getProfile(String name);
	/**
	 * Save a profile to the disk
	 * @param p Profile to save
	 */
    public void saveProfile(Profile p);
	/**
	 * Delete a profile which was previously saved
	 * @param name Name of the profile to delete
	 */
    public void deleteProfile(String name);
}
