/*
 * Board Layout
 * [00] [10] [20] [30] [40] [50] [60]
 * [01] [11] [21] [31] [41] [51] [61]
 * [02] [12] [22] [32] [42] [52] [62]
 * [03] [13] [23] [33] [43] [53] [63]
 * [04] [14] [24] [34] [44] [54] [64]
 * [05] [15] [25] [35] [45] [55] [65]
 */

public class Board {
    
	private int columnSize;
	public int rowSize;
	private Player[][] state;
	
	/**
	 * Board constructor that creates a standard 6*7 (row*col) 2D array of type Player. 
	 */
	public Board() {
		//Initial board as all slots are empty
		//Board distribute: [column][row]
		this.columnSize = 7;
		this.rowSize = 6;
		this.state = new Player[this.columnSize][this.rowSize];
		for (int column = 0; column < this.columnSize; column++) {
			for (int row = 0; row < this.rowSize; row++) {
				this.state[column][row] = Player.NOONE;
			}
		}
	}
	
	/**
	 * Alternate constructor with a current board state.
	 * @param State of the board in a 2D array of type Player.
	 */
	public Board(Board board){
	    this.columnSize = board.getColumnSize();
	    this.rowSize = board.getRowSize();
		this.state = new Player[this.columnSize][this.columnSize];
		for (int col = 0; col < this.columnSize; col++) {
			for (int row = 0; row < this.rowSize; row++) {
				this.state[col][row] = board.getState()[col][row];
			}
		}
	}
	
	/**
	 * Inserts a disc into the board.
	 * @param p Player who is inserting the disc.
	 * @param column The column the disc is to be inserted in to.
	 * @return A boolean, TRUE if successfully inserted otherwise FALSE.
	 */
	public boolean insert(Player p, int column) {
        if (this.state[column][0] == Player.NOONE) {
            for (int row = 0; row < this.rowSize; row++) {
                if (this.state[column][row] != Player.NOONE) {
                    this.state[column][row-1] = p;
                    return true;
                }
                if (row == this.rowSize-1) {
                    this.state[column][row] = p;
                    return true;
                }
            }
        }
        return false;
	}
	
	/**
	 * Removes a disc from the board.
	 * @param The column the disc is to be removed from.
	 * @return A boolean, TRUE if successfully removed otherwise FALSE.
	 */
	public boolean remove(int column) {
	    if (this.state[column][this.getRowSize()-1] != Player.NOONE) {
            for (int row = this.rowSize-1; row >= 0; row--) {
                if (this.state[column][row] == Player.NOONE) {
                    this.state[column][row+1] = Player.NOONE;
                    break;
                }
                if (row == 0) {
                    this.state[column][row] = Player.NOONE;
                }
            }
            return true;
	    }
	    return false;
	}
	
	/**
	 * Get the current state of the board as a 2D array of type Player.
	 * @return A 2D array of type Player
	 */
	public Player[][] getState() {
		return this.state;
	}
	
	/**
	 * Get the number of columns of the board.
	 * @return An integer that is the number of columns the board consists of.
	 */
	public int getColumnSize() {
	    return this.columnSize;
	}
	
	/**
     * Get the number of rows of the board.
     * @return An integer that is the number of rows the board consists of.
     */
	public int getRowSize() {
	    return this.rowSize;
	}
	
	/**
	 * Clear all discs from the board.
	 */
	public void clear() {
		for (int col = 0; col < this.columnSize; col++) {
			for (int row = 0; row < this.rowSize; row++) {
				this.state[col][row] = Player.NOONE;
			}
		}
	}
	
	/**
	 * Creates a copy of this board.
	 */
	public Board clone() {
	    return new Board(this);
	}
	
}
