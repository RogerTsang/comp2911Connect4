public interface Iai {
    /**
     * Analyses the current state of the board and makes an interpretation
     * of the best move possible.
     * @param g Interface to access the main game systems available methods.
     * @param b The current state of the board.
     * @return An int that is the column the disc is to be inserted to.
     */
	public int makeMove(IGame g, Board b);
	/**
	 * Get the difficulty level of the AI.
	 * @return A String identifying the AI's difficulty level..
	 */
	public String getDifficulty();
}