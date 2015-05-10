import java.util.Stack;


public class GameSystem {
	private GameState state;
	private Player currentPlayer;
	private Player winner;
	private Board board;
	private Stack<Integer> UndoStack;
	private Stack<Integer> RedoStack;
	
	public GameSystem() {
		this.state = GameState.WAIT_FOR_JOIN;
		this.currentPlayer = Player.NOONE;
		this.winner = Player.NOONE;
		this.board = new Board();
		this.UndoStack = new Stack<Integer>();
		this.RedoStack = new Stack<Integer>();
	}
	
	/**
	 * A controller must ask for join game in order to identify itself.
	 * @return Player account
	 */
	public Player JoinGame() {
		if (this.state == GameState.WAIT_FOR_JOIN && this.currentPlayer == Player.NOONE) {
			this.currentPlayer = Player.P1;
			return this.currentPlayer;
		} else if (this.state == GameState.WAIT_FOR_JOIN && this.currentPlayer == Player.P1) {
			this.currentPlayer = Player.P2;
			this.state = GameState.WAIT_FOR_START;
			return this.currentPlayer;
		} else {
			System.err.println("!The Game Already Started");
			return null;
		}
	}
	
	/**
	 * This is called by controller to ask for first move
	 * @param p First Player
	 * @return False if the first player has already been set
	 */
	public Boolean setStarter(Player p) {
		if (this.state == GameState.WAIT_FOR_START) {
			this.currentPlayer = p;
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
	public boolean doMove(int column) {
		if (this.state != GameState.PLAYABLE) {
			System.err.println("!The game has end");
			return false;
		}
		
		if (this.board.insert(currentPlayer, column)) {
			this.UndoStack.add(column);
			switchPlayer();
			this.RedoStack.clear();
			this.winner = this.board.whosWin();
			this.board.debug_printBoard();
			if (this.winner == Player.P1 ||
				this.winner == Player.P2 ||
				this.winner == Player.DRAW) {
				this.state = GameState.FINISH;
				System.out.println("The winner is " + this.winner);
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
	 * Check if the game is finished.
	 * @return
	 */
	public boolean isFinish() {
		return (this.state == GameState.FINISH);
	}
	
	private void switchPlayer() {
		if (this.currentPlayer == Player.P1) {
			this.currentPlayer = Player.P2;
		} else if (this.currentPlayer == Player.P2) {
			this.currentPlayer = Player.P1;
		} else {
			System.err.print("!Use setStarter(Player) to set");
			System.err.println(" the first player before the game start");
		}
	}
}
