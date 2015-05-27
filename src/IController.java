import java.util.ArrayList;
import java.util.Stack;

public interface IController {
	public boolean newGame();
	public boolean startGame();
	public Player getCurrentPlayer();
	public Board getBoard();
	public boolean makeMove(int column);
	public boolean undo();
	public boolean redo();
	public Player getWinner();
    public Stack<Integer> getWinningDiscs();
	public boolean isFinish();
	public boolean attachAI(Iai bot);
	public boolean detachAI();
	public boolean hasAIAttached();
	public int getAITurn();
	public int getPlayerScore(Player p);
	public ArrayList<String> getProfileNames();
	public Profile getProfile(String name);
	public void saveProfile(Profile p);
	public void deleteProfile(String name);
	public void setProfile(int playerNumber,String name);
}