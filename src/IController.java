import java.util.ArrayList;
import java.util.Stack;

public interface IController {
    /**
     * Resets the game state and game board for a new game.
     * @return A boolean, TRUE if successfully reseted otherwise FALSE.
     */
	public boolean newGame();
	/**
	 * Changes the game state to commence playing.
	 * @return A boolean, TRUE if in a Playable game state otherwise FALSE.
	 */
	public boolean startGame();
	/**
	 * Gets the player whose turn it is.
	 * @return A Player that is the ID of whose turn it is.
	 */
	public Player getCurrentPlayer();
	/**
	 * Gets the current game board.
	 * @return A Board object with the current game board.
	 */
	public Board getBoard();
	/**
	 * Completes a move given a column to insert the disc.
	 * @param column An integer that is the column the disc is to be inserted in to.
	 * @return A boolean, TRUE if successfully completed move otherwise FALSE.
	 */
	public boolean makeMove(int column);
	/**
	 * Undoes the previous move, or two move if against AI, 
	 * and changes the game system accordingly.
	 * @return A boolean, TRUE if successfully undoes move otherwise FALSE.
	 */
	public boolean undo();
	/**
	 * Gets the winner of the current game.
	 * @return A Player that is the ID of the player who has won.
	 */
	public Player getWinner();
	/**
	 * Gets the winning discs positions in the form of a Stack, 
	 * popping firstly the row and then the column.
	 * @return A Stack of integers of the winning disc positions.
	 */
    public Stack<Integer> getWinningDiscs();
    /**
     * Checks the game state to see if the game has finished.
     * @return A boolean, TRUE if the game has finished otherwise FALSE.
     */
	public boolean isFinish();
	/**
	 * Adds an AI to be played against to the game system.
	 * @param ai An AI object that will make the CMP Opponent move.
	 * @return A boolean, TRUE if successfully added otherwise FALSE.
	 */
	public boolean hasAI();
	/**
	 * Gets the AI that is being played against turn and proceeds with completing that move.
	 * @return An integer that is the column the AI's disc is to be inserted into.
	 */
	public int getAITurn();
	/**
	 * Gets the current score (amount of wins) of a player.
	 * @param p A Player that is the ID of the player whose score to get.
	 * @return An integer that is the players score.
	 */
    public boolean addAI(Iai ai);
    /**
     * Removes an AI that is being played against from the game system.
     * @return A boolean, TRUE if successfully removed otherwise FALSE.
     */
    public boolean removeAI();
	/**
	 * Get names of profiles that were saved previously
	 * @return List of names of profiles
	 */
	public ArrayList<String> getProfileNames();
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
	/**
	 * Set the given player as the given profile
	 * @param playerNumber Player number, either 1 or 2
	 * @param name Name of profile
	 */
	public void setProfile(int playerNumber,String name);
	/**
	 * Get the number of undo's left in the current game if playing against AI
	 * @return Number of undo's left
	 */
	public int getUndosLeft();
}