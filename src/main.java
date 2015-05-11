
public class main {

	public static void main(String[] args) {
		GameSystem gs = new GameSystem();
		IController player1 = new UserController();
		IController player2 = new UserController();
		player1.hookToGame(gs);
		player2.hookToGame(gs);
		player1.requestFirstMove();
		
		player1.redo();//This should do nothing
		player1.move(1);
		player2.move(2);
		player1.requestNewGame();
		player2.requestFirstMove();
		player2.move(0);
	}

}
