import java.util.Stack;


 public class GameSystem implements IController{
	private GameState state;
	private Player currentPlayer;
	private Player winner;
	private Board board;
	private int P1Score;
	private int P2Score;
	private Stack<Integer> UndoStack;
	private Stack<Integer> RedoStack;
	private Iai ai;
	private String info;
	
	public GameSystem() {
		this.state = GameState.WAIT_FOR_START;
		this.currentPlayer = Player.P1;
		this.winner = Player.NOONE;
		this.board = new Board();
		this.P1Score = 0;
		this.P2Score = 0;
		this.UndoStack = new Stack<Integer>();
		this.RedoStack = new Stack<Integer>();
		this.ai = null;
		this.info = null;
		
	}
	
	/**
	 * Method to generate a new game without disconnecting the Player(AI)s.
	 * @return New game can only generate when PLAYABLE or FINISH
	 */
	public boolean newGame() {
		if (this.state == GameState.PLAYABLE || this.state == GameState.FINISH) {
			this.state = GameState.WAIT_FOR_START;
			this.currentPlayer = Player.P1;
			this.winner = Player.NOONE;
			this.board.clear();
			this.UndoStack.clear();
			this.RedoStack.clear();
			this.info = "> A new game has been started";
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Start a new game
	 */
	public boolean startGame() {
		if (this.state == GameState.WAIT_FOR_START) {
			this.state = GameState.PLAYABLE;
			this.info = "> Use mouse to insert dice,enjoy";
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
			this.info = "> The game has end";
			return false;
		}
		
		if (this.board.insert(currentPlayer, column)) {
			this.UndoStack.add(column);
			this.RedoStack.clear();
			this.winner = this.board.checkWin(column);
			switch(this.winner){
				case P1WIN: this.P1Score++; 
						 this.state = GameState.FINISH; 
						 this.info = "> Player 1 has won";
						 break;
				case P2WIN: this.P2Score++; 
						 this.state = GameState.FINISH; 
						 this.info = "> Player 2 has won";
						 break;
				case DRAW: this.state = GameState.FINISH; 
						 this.info = "> Game draw";
						 break;
				default: switchPlayer(); break;
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
		
		if (this.currentPlayer != Player.NOONE && this.ai == null) {
			if (!this.UndoStack.isEmpty()) {
				int lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				switchPlayer();
				return true;
			}
		} else if (this.currentPlayer != Player.NOONE && this.ai != null) {
			if (!this.UndoStack.isEmpty()) {
				int lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				lastMove = this.UndoStack.pop();
				this.RedoStack.add(lastMove);
				this.board.remove(lastMove);
				return true;
			}
		}
		this.info = "> Cannot Undo";
		System.out.println("> Cannot Undo");
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
		
		if (this.currentPlayer != Player.NOONE && this.ai == null) {
			if (!this.RedoStack.isEmpty()) {
				int reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.board.insert(this.currentPlayer, reMove);
				switchPlayer();
				return true;
			}
		} else if (this.currentPlayer != Player.NOONE && this.ai != null) {
			if (!this.RedoStack.isEmpty()) {
				int reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.board.insert(this.currentPlayer, reMove);
				reMove = this.RedoStack.pop();
				this.UndoStack.add(reMove);
				this.currentPlayer = Player.P2;
				this.board.insert(this.currentPlayer, reMove);
				this.currentPlayer = Player.P1;
				return true;
			}
		}
		System.out.println("> Cannot Redo");
		return false;
	}
	
	/**
	 * Get info
	 * @return the info
	 */
	public String getInfo(){
		return this.info;
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
		this.info = String.format("Score: %d - %d", this.P1Score, this.P2Score);
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
	
	/**
	 * This method will switch player between human P1 or P2(AI).
	 */
	private void switchPlayer() {
		if (this.currentPlayer == Player.P1) {
			this.currentPlayer = Player.P2;
			this.info = "Player 2";
		} else if (this.currentPlayer == Player.P2) {
			this.currentPlayer = Player.P1;
			this.info = "Player 1";
		}
	}	

	public boolean attachAI(Iai bot) {
		if (this.state == GameState.WAIT_FOR_START) {
			this.ai = bot;
			bot.getBoard(this.getBoard());
			return true;
		} else {
			return false;			
		}
	}

	public boolean detachAI() {
		if (this.state == GameState.WAIT_FOR_START && this.ai != null) {
			this.ai.removeBoard();
			this.ai = null;
			return true;
		} else {
			return false;			
		}
	}
	
	/**
	 * In order to debug, Now the AI can move whatever if it is its turn.
	 */
	public boolean getAITurn() {
		if (this.ai != null && this.currentPlayer == Player.P2) {
			this.move(this.ai.makeMove());
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Player[][] getBoard() {
		return this.board.getState();
	}
}
