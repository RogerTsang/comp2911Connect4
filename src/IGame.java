
public interface IGame {
    public Player checkWin(Board b, int column, Player p);
    public int getConnectToWin();
    public boolean isLegalMove(int column);
    public boolean isLegalMove(int column, Board b);
    public Player getCurrentPlayer();
}
