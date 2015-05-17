/*
 * Board Layout
 * [00] [10] [20] [30] [40] [50] [60]
 * [01] [11] [21] [31] [41] [51] [61]
 * [02] [12] [22] [32] [42] [52] [62]
 * [03] [13] [23] [33] [43] [53] [63]
 * [04] [14] [24] [34] [44] [54] [64]
 * [05] [15] [25] [35] [45] [55] [65]
 * 
 * 
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
	 * alternate constructor with a current boardstate
	 * @param board	state of the board in array form
	 */
	public Board(Player[][] board){
		this.state = new Player[7][6];
		for (int column = 0; column < 7; column++) {
			for (int row = 0; row < 6; row++) {
				this.state[column][row] = Player.NOONE;
				if(board[column][row] == Player.P1){
					this.state[column][row] = Player.P1;
				} else if(board[column][row] == Player.P2){
					this.state[column][row] = Player.P2;
				}
				if(this.state[column][row] != Player.NOONE){
					this.numDisc++;
				}
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
	
	public void clear() {
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				this.state[col][row] = Player.NOONE;
			}
		}
		this.numDisc = 0;
	}
	/**
	 * Gets the current player based on a boardState. It assumes that the first player is white.
	 * @return the current player
	 */
	public Player getCurrentPlayer(){
		if(this.numDisc % 2 == 0){
			return Player.P1;
		} else {
			return Player.P2;
		}
	}
	
	/**
	 * A Different method for checking who has won the game, based on the last move only instead of scanning the whole board.
	 * @return winner of the game or nobody if noone has won yet
	 */
	public Player checkWin(int column){
		int Row = countEmptySlot(column);
		int r = 0;
		//check if there is a vertical win
		for(r = Row-3; r <= Row; r++){
			if(isInBounds(column,r) && isInBounds(column,r+3)){
				if(this.state[column][r] == this.state[column][r+1] 
						&& this.state[column][r+1] == this.state[column][r+2] 
						&& this.state[column][r+2] == this.state[column][r+3]){
					printWinner(column, r, 0);
					return this.state[column][r];
				}
			}
		}
		//check if there is a horizontal win
		int c = 0;
		for(c = column-3; c <= column; c++){
			if(isInBounds(c,Row) && isInBounds(c+3,Row)){
				if(this.state[c][Row] == this.state[c+1][Row] 
						&& this.state[c+1][Row] == this.state[c+2][Row] 
						&& this.state[c+2][Row] == this.state[c+3][Row]){
					printWinner(c, Row, 1);
					return this.state[c][Row];
				}
			}
		}
		//check if there is a diagonal win
		for(c = column-3; c <= column; c++){
			r= c-column+Row;
			if(isInBounds(c,r) && isInBounds(c+3,r+3)){
				if(this.state[c][r] == this.state[c+1][r+1] 
						&& this.state[c+1][r+1] == this.state[c+2][r+2] 
						&& this.state[c+2][r+2] == this.state[c+3][r+3]){
					printWinner(c, r, 2);
					return this.state[c][r];
				}
			}
			r= Row - (c-column);
			if(isInBounds(c,r) && isInBounds(c+3,r-3)){
				if(this.state[c][r] == this.state[c+1][r-1] 
						&& this.state[c+1][r-1] == this.state[c+2][r-2] 
						&& this.state[c+2][r-2] == this.state[c+3][r-3]){
					printWinner(c, r, 3);
					return this.state[c][r];
				}
			}
		}
		return checkDraw();
	}
	
	private void printWinner(int winCol, int winRow, int winType) {
		Player winner = this.state[winCol][winRow];
		if (winner == Player.P1) {
			winner = Player.P1WIN;
		} else {
			winner = Player.P2WIN;
		}
		switch (winType) {
		//Vertical Win
		case 0: this.state[winCol][winRow] = this.state[winCol][winRow+1] = this.state[winCol][winRow+2] = this.state[winCol][winRow+3] = winner; break;
		//Horizontal Win
		case 1: this.state[winCol][winRow] = this.state[winCol+1][winRow] = this.state[winCol+2][winRow] = this.state[winCol+3][winRow] = winner; break;
		//Diagonal Win Bottom Right
		case 2: this.state[winCol][winRow] = this.state[winCol+1][winRow+1] = this.state[winCol+2][winRow+2] = this.state[winCol+3][winRow+3] = winner; break;
		//Diagonal Win Top Right
		case 3: this.state[winCol][winRow] = this.state[winCol+1][winRow-1] = this.state[winCol+2][winRow-2] = this.state[winCol+3][winRow-3] = winner; break;
		}
	}
	
	private boolean isInBounds(int column, int row){
		if(row >= 0 && row <= 5 && column >= 0 && column <= 6){
			return true;
		} else {
			return false;	
		}	
	}
	
	private Player checkDraw() {
		if (this.numDisc == 42) {
			return Player.DRAW;
		} else {
			return Player.NOONE;
		}
	}
	
	public int countEmptySlot(int column) {
		//check if the column are full
		int depth = 0;
		for (int r = 0; r < 6; r++) {
			if (this.state[column][r] == Player.NOONE) {
				depth++;
			}
		}
		return depth;
	}
}
