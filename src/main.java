import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		IController user = new GameSystem();
		SillyAI ai = new SillyAI();
		Scanner sc = new Scanner(System.in);
		String command;
		System.out.println("> Awating Command: ");
	    while((command = sc.next()) != "exit") {
	    	switch(command) {
	    	case "help": System.out.println("newGame-> (attachBot) -> startGame -> (0~6) ");
	    	case "newGame": user.newGame(); break;
	    	case "startGame": user.startGame(); break;
	    	case "attachBot": user.attachAI(ai); break;
	    	case "detachBot": user.detachAI(); break;
	    	case "undo": user.undo(); break;
	    	case "redo": user.redo(); break;
	    	case "who": System.out.println("Current = " + user.getCurrentPlayer()); break;
	    	case "score": System.out.println(user.getPlayerScore(Player.P1) + " : " + user.getPlayerScore(Player.P2)); break;
	    	case "exit": System.exit(0); break;
	    	default: user.move(Integer.parseInt(command));
	    	}
	    }
	}

}
