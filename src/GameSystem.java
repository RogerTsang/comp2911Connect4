import java.util.Stack;


public class GameSystem implements Controller{
	private GameState state;
	private Player currentPlayer;
	private Player winner;
	private Board board;
	private int P1Score;
	private int P2Score;
	private Stack<Integer> UndoStack;
	private Stack<Integer> RedoStack;
	
	public GameSystem() {
		this.state = GameState.WAIT_FOR_START;
		this.currentPlayer = Player.P1;
		this.winner = Player.NOONE;
		this.board = new Board();
		this.P1Score = 0;
		this.P2Score = 0;
		this.UndoStack = new Stack<Integer>();
		this.RedoStack = new Stack<Integer>();
	}
	
	/**
	 * Method to generate a new game without disconnecting the controller.
	 * @return New game can only generate when PLAYABLE or FINISH
	 */
	public Boolean newGame() {
		if (this.state == GameState.PLAYABLE || this.state == GameState.FINISH) {
			this.state = GameState.WAIT_FOR_START;
			this.currentPlayer = Player.P1;
			this.winner = Player.NOONE;
			this.board = new Board();
			this.UndoStack.clear();
			this.RedoStack.clear();
			System.out.println("> A new game has been started");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Start a new game
	 */
	public Boolean startGame() {
		if (this.state == GameState.WAIT_FOR_START ) {
			this.state = GameState.PLAYABLE;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Inform you whose turn is it
	 * @return
	 */
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/**
	 * Choose a column to insert a disc
	 * @param column
	 * @return True if the move can be done
	 */
	public boolean move(int column) {
		if (this.state != GameState.PLAYABLE) {
			System.out.println("> The game has end");
			return false;
		}
		
		if (this.board.insert(currentPlayer, column)) {
			this.UndoStack.add(column);
			this.RedoStack.clear();
			this.winner = this.board.whosWin();
			this.board.debug_printBoard();
			switchPlayer();
			switch(this.winner){
				case P1: this.P1Score++; this.state = GameState.FINISH; break;
				case P2: this.P2Score++; this.state = GameState.FINISH; break;
				case DRAW: this.state = GameState.FINISH; break;
				default: break;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Undo the last move. This will cause a player switch
	 * @return True if the undo can be done
	 */
	public boolean undo() {
		if (this.state != GameState.PLAYABLE) {
			return false;
		}
		
		if (this.currentPlayer != Player.NOONE) {
			if (!this.UndoStack.isEmpty()) {
				int lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				this.board.debug_printBoard();
				switchPlayer();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Redo the last move, This will cause a player switch
	 * @return True if the redo can be done
	 */
	public boolean redo() {
		if (this.state != GameState.PLAYABLE) {
			return false;
		}
		
		if (this.currentPlayer != Player.NOONE) {
			if (!this.RedoStack.isEmpty()) {
				int reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.board.insert(this.currentPlayer, reMove);
				this.board.debug_printBoard();
				switchPlayer();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get who the winner is
	 * @return
	 */
	public Player getWinner() {
		return this.winner;
	}
	
	/**
	 * Get P1 Score
	 */
	public int getPlayerScore(Player p) {
		switch(p) {
			case P1: return this.P1Score;
			case P2: return this.P2Score;
			default: return -1;
		}
				
	}
	
	/**
	 * Check if the game is finished.
	 * @return
	 */
	public boolean isFinish() {
		return (this.state == GameState.FINISH);
	}
	
	private void switchPlayer() {
		if (this.currentPlayer == Player.P1) {
			this.currentPlayer = Player.P2;
			System.out.println("Current = Player 2");
		} else if (this.currentPlayer == Player.P2) {
			this.currentPlayer = Player.P1;
			System.out.println("Current = Player 1");
		} else {
			System.err.print("!Use setStarter(Player) to set");
			System.err.println(" the first player before the game start");
		}
	}
}
