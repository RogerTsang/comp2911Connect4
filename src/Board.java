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
	private Player[][] state;
	private int numDisc;
	
	public Board() {
		//Initial board as all slots are empty
		//Board distribute: [column][row]
		this.state = new Player[7][6];
		this.numDisc = 0;
		for (int column = 0; column < 7; column++) {
			for (int row = 0; row < 6; row++) {
				this.state[column][row] = Player.NOONE;
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
		int emptySlot = countEmptySlot(column);
		if (emptySlot > 0 && column >= 0 && column <= 6) {
			this.state[column][emptySlot-1] = p;
			this.numDisc++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Remove a disc from the board
	 * @param column Which column
	 * @return
	 */
	public boolean remove(int column) {
		int emptySlot = countEmptySlot(column);
		if (emptySlot < 6 && column >= 0 && column <= 6) {
			this.state[column][emptySlot] = Player.NOONE;
			this.numDisc--;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get direct board info
	 * @return
	 */
	public Player[][] getState() {
		return this.state;
	}
	
	/**
	 * This method should be called after every move is done
	 * @return Player who win the game.
	 */
	public Player whosWin() {
		Player currentWin = Player.NOONE;
		if ((currentWin = checkFourInRow()) != Player.NOONE) {
			return currentWin;
		} else if ((currentWin = checkFourInColumn()) != Player.NOONE) {
			return currentWin;
		} else if ((currentWin = checkFourInDiagonalTopRight()) != Player.NOONE) {
			return currentWin;
		} else if ((currentWin = checkFourInDiagonalBottomRight()) != Player.NOONE) {
			return currentWin;
		} else if ((currentWin = checkDraw()) != Player.NOONE) {
			return currentWin;
		} else {
			return Player.NOONE;
		}
	}
	
	private Player checkFourInRow() {
		int rowCounter;
		for (int r = 0; r < 6; r++) {
			rowCounter = 0;
			for (int c = 0; c < 6; c++) {
				if (this.state[c][r] != Player.NOONE &&
					this.state[c][r] == this.state[c+1][r]) {
					rowCounter++;
				} else {
					rowCounter = 0;
				}
				
				if (rowCounter >= 3) {
					return this.state[c][r];
				}
			}
		}
		return Player.NOONE;
	}
	
	private Player checkFourInColumn() {
		int columnCounter;
		for (int c = 0; c < 7; c++) {
			columnCounter = 0;
			for (int r = 0; r < 5; r++) {
				if (this.state[c][r] != Player.NOONE &&
					this.state[c][r] == this.state[c][r+1]) {
					columnCounter++;
				} else {
					columnCounter = 0;
				}
				
				if (columnCounter >= 3) {
					return this.state[c][r];
				}
			}
		}
		return Player.NOONE;
	}
	
	private Player checkFourInDiagonalTopRight() {
		for (int r = 3; r <= 5; r++) {
			for (int c = 0; c <= 3; c++) {
				if (this.state[c][r] != Player.NOONE &&
					this.state[c][r] == this.state[c+1][r-1] &&
					this.state[c][r] == this.state[c+2][r-2] && 
					this.state[c][r] == this.state[c+3][r-3]) {
					return this.state[c][r];
				}
			}
		}
		return Player.NOONE;
	}
	
	private Player checkFourInDiagonalBottomRight() {
		for (int r = 0; r <= 2; r++) {
			for (int c = 0; c <= 3; c++) {
				if (this.state[c][r] != Player.NOONE &&
					this.state[c][r] == this.state[c+1][r+1] &&
					this.state[c][r] == this.state[c+2][r+2] && 
					this.state[c][r] == this.state[c+3][r+3]) {
					return this.state[c][r];
				}
			}
		}
		return Player.NOONE;
	}
	
	private Player checkDraw() {
		if (this.numDisc == 42) {
			return Player.DRAW;
		} else {
			return Player.NOONE;
		}
	}
	
	private int countEmptySlot(int column) {
		//check if the column are full
		int depth = 0;
		for (int r = 0; r < 6; r++) {
			if (this.state[column][r] == Player.NOONE) {
				depth++;
			}
		}
		return depth;
	}
	
	/**
	 * This method is for debug use
	 */
	public void debug_printBoard() {
		System.out.println("P1 = O         P2 = X");
		System.out.println(" 0  1  2  3  4  5  6");
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				switch (this.state[c][r]) {
					case NOONE: System.out.printf("[ ]"); break;
					case P1: System.out.printf("[O]"); break;
					case P2: System.out.printf("[X]"); break;
					case DRAW: System.err.printf("!Unexpected Player"); break;
				}
			}
			System.out.printf("\n");
		}
		System.out.println("<===================>");
	}
}
