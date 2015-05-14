import java.util.ArrayList;

public class SmartAI implements Iai {
	IController control;

	public SmartAI(IController gameController){
		this.control = gameController;
	}
	
	public void makeMove() {
		System.out.println("we got here third");
		Board testBoard = new Board(control.getBoard());
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		//adding all possible moves. We remove them if they are bad (i.e. immediately lose you the game).
		
		for(int i=0;i<=6;i++){
			testBoard.debug_printBoard();
			if(testBoard.countEmptySlot(i) != 0){
				possibleMoves.add(i);
			}
		}
		
		//if we immediately win, we make that move.
		for(int move:possibleMoves){
			testBoard.insert(control.getCurrentPlayer(),move);
			if(testBoard.checkWin(move) == control.getCurrentPlayer()){
				control.move(move);
				return;
			}
		}
		System.out.println("we got here sixth");
		//does your move immediately allow the other player to win? If so, don't make it.
		for(int move:possibleMoves){
			testBoard.insert(control.getCurrentPlayer(),move);
			for(int i2=0;i2 <= 6;i2++){
				if(testBoard.countEmptySlot(i2) != 0){				
					testBoard.insert(Player.P1,i2);
					if(testBoard.checkWin(i2) != Player.NOONE){
						possibleMoves.remove(move);
					}
					testBoard.remove(i2);
				}
			}
			testBoard.remove(move);
		}
		
		int bestMove = 0;
		if(possibleMoves.size()!=0){
			bestMove = possibleMoves.get(0);
		}else{
			bestMove = 0;
		}
		System.out.println("we got here");
		control.move(bestMove);
		return;
	}

	
	/*private void evaluateBoardPosition(){
		
	}*/
	
	public void getController(IController c) {
		this.control = c;
	}
	
	public void removeController() {
		this.control = null;
	}
	
	public String toString() {
		return "I'm smart, I'm sorry :^)";
	}
}
