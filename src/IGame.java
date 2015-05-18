
public interface IGame {
    Player checkWin(Board b, int column, Player p);
    int getConnectToWin();
    boolean isLegalMove(int column);
}
