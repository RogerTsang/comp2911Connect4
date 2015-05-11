
public class main {

	public static void main(String[] args) {
		Controller control = new GameSystem();
		control.newGame();
		control.startGame();
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		System.out.println(control.getPlayerScore(Player.P1));
		System.out.println(control.getPlayerScore(Player.P2));
		
		control.newGame();
		control.startGame();
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		control.move(1);
		control.move(2);
		System.out.println(control.getPlayerScore(Player.P1));
		System.out.println(control.getPlayerScore(Player.P2));
	}

}
