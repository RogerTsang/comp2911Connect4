
public interface IController {
	/**
	 * A controller always need to check if this is my turn to make a move.
	 * @return
	 */
	public boolean isMyTurn();
	public boolean move (int column);
	public boolean undo();
	public boolean redo();
	public boolean isEnd();
	public Player whosTurn();
	public Player whoWins();
	public void hookToGame(GameSystem gs);
	public void requestFirstMove();
}