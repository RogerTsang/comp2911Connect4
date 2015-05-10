
public class main {

	public static void main(String[] args) {
		GameSystem gs = new GameSystem();
		IController player1 = new UserController();
		IController player2 = new UserController();
		player1.hookToGame(gs);
		player2.hookToGame(gs);
		player1.requestFirstMove();
		
		if(player1.isMyTurn()) player1.move(0);
		if(player2.isMyTurn()) player2.move(0);
		if(player1.isMyTurn()) player1.move(0);
		if(player2.isMyTurn()) player2.move(0);
		if(player1.isMyTurn()) player1.move(2);
		if(player2.isMyTurn()) player2.move(2);
		if(player1.isMyTurn()) player1.move(2);
		if(player2.isMyTurn()) player2.move(1);
		if(player1.isMyTurn()) player1.move(1);
		if(player1.isMyTurn()) player1.move(1);//Prevented Invalid Move
		if(player2.isMyTurn()) player2.move(1);
		if(player1.isMyTurn()) player1.move(6);
		if(player2.isMyTurn()) player2.move(5);
		if(player1.isMyTurn()) player1.move(4);
		if(player2.isMyTurn()) player2.undo();
		if(player1.isMyTurn()) player1.undo();
		if(player2.isMyTurn()) player2.undo();
		if(player1.isMyTurn()) player1.undo();
		if(player2.isMyTurn()) player2.undo();
		if(player1.isMyTurn()) player1.undo();
		if(player2.isMyTurn()) player2.undo();
		if(player1.isMyTurn()) player1.move(6);
		if(player2.isMyTurn()) player2.move(6);
		if(player1.isMyTurn()) player1.undo();
		if(player2.isMyTurn()) player2.undo();
		if(player1.isMyTurn()) player1.redo();
		if(player2.isMyTurn()) player2.redo();
		if(player1.isMyTurn()) player1.redo();//This should do nothing
	}

}
