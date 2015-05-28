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
	public int getPlayerScore(Player p);
	
	public ArrayList<String> getProfileNames();
	public Profile getProfile(String name);
	public void saveProfile(Profile p);
	public void deleteProfile(String name);
	public void setProfile(int playerNumber,String name);
}