
public interface IGameOptions {
    /**
     * Gets the AI that is being played against turn and proceeds with completing that move.
     * @return An integer that is the column the AI's disc is to be inserted into.
     */
    public Profile getProfile(String name);
    public void saveProfile(Profile p);
    public void deleteProfile(String name);
    public void setProfile(int playerNumber,String name);
}
