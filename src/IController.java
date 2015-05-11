
public interface IController {
	public boolean move (int column);
	public boolean undo();
	public boolean redo();
	public boolean isEnd();
	public Player whosTurn();
	public Player whoWins();
	public void hookToGame(GameSystem gs);
	public void requestFirstMove();
	public void requestNewGame();
}