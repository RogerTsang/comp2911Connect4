
public interface IGame {
    /**
     * Checks the current state of the board to see if a player has won.
     * @param b A Board object with the current board state.
     * @param column An int that is the column the disc is to be inserted in to.
     * @param p Player type that is the current player's ID.
     * @return A boolean, TRUE if a player has one otherwise FALSE.
     */
    public Player checkWin(Board b, int column, Player p);
    /**
     * Get how many discs in a line are need to win.
     * @return An integer that is the connected line length to win.
     */
    public int getConnectToWin();
    /**
     * Check to see whether a move can be made in a column.
     * @param column An integer that is the column to see if a disc may be inserted.
     * @param b A Board object who's current state is to be analysed.
     * @return A boolean, TRUE is a disc may be inserted otherwise FALSE.
     */
    public boolean isLegalMove(Board b, int column);
}
