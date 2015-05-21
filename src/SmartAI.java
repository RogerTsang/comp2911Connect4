import java.util.ArrayList;

public class SmartAI implements Iai {
	Player[][] control;
	private Player OurID;
	private Player theirID;
	
	public SmartAI(Player[][] players){
		this.control = players;
	}
	
	private void setID(Player p){
		this.OurID = p;
		if(p == Player.P1){
			this.theirID = Player.P2;
		} else {
			this.theirID = Player.P2;
		}
		return;
	}
	
	public int makeMove() {
		Board testBoard = new Board(control);
		this.setID(testBoard.getCurrentPlayer());
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();		//These are moves we want the AI to make
		ArrayList<Integer> allPossibleMoves = new ArrayList<Integer>();		//This is all legal moves
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		//Adding all legal moves. We remove moves later on if they are poor.
		for(int i=0;i<=6;i++){
			if(testBoard.countEmptySlot(i) != 0){
				possibleMoves.add(i);
				allPossibleMoves.add(i);
			}
		}
		
		//if we immediately win, we make that move.
		for(int move:possibleMoves){
			testBoard.insert(this.OurID,move);
			if(testBoard.checkWin(move) == this.OurID){
				//System.out.println("We immediately Won");
				return move;
			}
			testBoard.remove(move);
		}
		
		//Does your move immediately allow the other player to win? If so, don't make it.
		for(int move:possibleMoves){
			testBoard.insert(testBoard.getCurrentPlayer(),move);
			for(int m:allPossibleMoves){
				if(testBoard.countEmptySlot(m) != 0){				
					testBoard.insert(this.theirID,m);
					if(testBoard.checkWin(m) == this.theirID){
						toRemove.add(move);
					}
					testBoard.remove(m);
				}
			}
			testBoard.remove(move);
		}
		
		if(toRemove.size() != 0){
			for(int m:toRemove){
				if(possibleMoves.indexOf(m) != -1){
					possibleMoves.remove(possibleMoves.indexOf(m));
				}
			}
		}
		toRemove.clear();
		
		//Will the other player win immediately? Block that move.
		
		for(int move2:allPossibleMoves){
			if(testBoard.countEmptySlot(move2) != 0){				
				testBoard.insert(testBoard.getCurrentPlayer(),move2);
				if(testBoard.checkWin(move2) == Player.P1){
					//System.out.println("We blocked an immediate win");
					return move2;
				}
				testBoard.remove(move2);
			}
		}
		
		/*
		for(int m:possibleMoves){
			System.out.println(m);
		}
		*/
		//Will the other player win with two moves? We need to move to interrupt i.e.(The classic example of two on the bottom with a space either side).
		for(int move:allPossibleMoves){
			testBoard.insert(this.theirID,move);
			for(int move2:allPossibleMoves){
				if(testBoard.countEmptySlot(move2) != 0){				
					testBoard.insert(this.theirID,move2);
					if(testBoard.checkWin(move2) == this.theirID){
						return move2;
					}
					testBoard.remove(move2);
				}
			}
			testBoard.remove(move);
		}
		
		//if we haven't made an automatic move by this point, we have to decide between the remaining possible moves.
		int bestMove = 0;
		if(possibleMoves.size()!=0){
			//The possibleMoves list has [0, 1, 2, 3, 4, 5, 6]
			//And the AI always make move 0 first.
			bestMove = possibleMoves.get(0);
			System.out.println(possibleMoves);
		}else{
			bestMove = 0;
		}
		
		return bestMove;
	}

	
	/*private void evaluateBoardPosition(){
		
	}*/
	
	public void getBoard(Player[][] c) {
		this.control = c;
	}
	
	public void removeBoard() {
		this.control = null;
	}
	
	public String toString() {
		return "I'm smart, I'm sorry :^)";
	}
}
