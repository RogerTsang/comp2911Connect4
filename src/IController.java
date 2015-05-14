public interface IController {
	public boolean newGame();
	public boolean startGame();
	public boolean move(int column);
	public boolean undo();
	public boolean redo();
	public boolean isFinish();
	public boolean attachAI(Iai bot);
	public boolean detachAI();
	public Player getCurrentPlayer();
	public Player getWinner();
	public Player[][] getBoard();
	public int getPlayerScore(Player p);
	public String getInfo();
}