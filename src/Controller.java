
public interface Controller {
	public Player getWinner();
	public Boolean newGame();
	public Boolean startGame();
	public boolean move(int column);
	public boolean isFinish();
	public boolean undo();
	public boolean redo();
	public int getPlayerScore(Player p);
}