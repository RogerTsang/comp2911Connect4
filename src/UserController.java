
public class UserController implements IController {
	private GameSystem gs;
	private Player account;
	
	public boolean move(int column) {
		return gs.doMove(column);
	}

	public boolean undo() {
		return gs.undo();
	}

	public boolean redo() {
		return gs.redo();
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

	@Override
	public boolean isMyTurn() {
		return (this.gs.getCurrentPlayer() == this.account);
	}
}