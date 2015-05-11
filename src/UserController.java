
public class UserController implements IController {
	private GameSystem gs;
	private Player account;
	
	public boolean move(int column) {
		if (this.gs.getCurrentPlayer() == this.account) {
			return gs.doMove(column);			
		} else {
			return false;
		}
	}

	public boolean undo() {
		if (this.gs.getCurrentPlayer() == this.account) {
			return gs.undo();			
		} else {
			return false;
		}
	}

	public boolean redo() {
		if (this.gs.getCurrentPlayer() == this.account) {
			return gs.redo();			
		} else {
			return false;
		}
	}

	public boolean isEnd() {
		return gs.isFinish();
	}

	public Player whosTurn() {
		return gs.getCurrentPlayer();
	}

	public Player whoWins() {
		return gs.getWinner();
	}

	public void hookToGame(GameSystem gs) {
		this.gs = gs;
		this.account = gs.JoinGame();
	}
	
	public void requestFirstMove() {
		this.gs.setStarter(this.account);
	}

	public void requestNewGame() {
		this.gs.newGame();
	}
	
}