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
	 * alternate constructor with a current board state
	 * @param board	state of the board in array form
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
	 * Insert a disc into the board 
	 * @param p Player
	 * @param column Which column you want to insert
	 * @return
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
	 * Remove a disc from the board
	 * @param column Which column
	 * @return
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
	 * Get direct board info
	 * @return
	 */
	public Player[][] getState() {
		return this.state;
	}
	
	public int getColumnSize() {
	    return this.columnSize;
	}
	
	public int getRowSize() {
	    return this.rowSize;
	}
	
	public void clear() {
		for (int col = 0; col < this.columnSize; col++) {
			for (int row = 0; row < this.rowSize; row++) {
				this.state[col][row] = Player.NOONE;
			}
		}
	}
	
	public Board clone() {
	    return new Board(this);
	}
	
}
