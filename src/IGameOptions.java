
public interface IGameOptions {
    public boolean addAI(Iai ai);
    /**
     * Removes an AI that is being played against from the game system.
     * @return A boolean, TRUE if successfully removed otherwise FALSE.
     */
    public boolean removeAI();
    /**
     * Checks to see whether the current game is being played against an AI.
     * @return A boolean, TRUE if the opponent is an AI otherwise FALSE.
     */
    public boolean hasAI();
    /**
     * Gets the AI that is being played against turn and proceeds with completing that move.
     * @return An integer that is the column the AI's disc is to be inserted into.
     */
    public Profile getProfile(String name);
    public void saveProfile(Profile p);
    public void deleteProfile(String name);
    public void setProfile(int playerNumber,String name);
}
