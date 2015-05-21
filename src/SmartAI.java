import java.util.ArrayList;

public class SmartAI implements Iai {
	Player[][] control;
	private Player OurID;
	private Player theirID;
	
	public SmartAI(Player p){
		this.OurID = p;
		if(p == Player.P1){
			this.theirID = Player.P2;
		} else {
			this.theirID = Player.P2;
		}
		return;
	}
	
	private void setID(Player p){
		this.OurID = p;
		if(p == Player.P1){
			this.theirID = Player.P2;
		} else {
			this.theirID = Player.P1;
		}
		return;
	}
	
	public int makeMove(IGame g, Board b) {
		Board testBoard = new Board(b);
		this.setID(g.getCurrentPlayer());
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();		//These are moves we want the AI to make
		ArrayList<Integer> allPossibleMoves = new ArrayList<Integer>();		//This is all legal moves
		ArrayList<Integer> toRemove = new ArrayList<Integer>();				//This array allows us to avoid concurrent modification errors.
		
		//Adding all legal moves. We remove moves later on if they are poor.
		for(int i=0;i<=6;i++){
			if(g.isLegalMove(i)){
				possibleMoves.add(i);
				allPossibleMoves.add(i);
			}
		}
		
		System.out.println("PossibleMoves are " + possibleMoves);
		
		//if we immediately win, we make that move.
		for(int move:possibleMoves){
			testBoard.insert(this.OurID, move);
			if(g.checkWin(testBoard, move, this.OurID) == this.OurID){
				System.out.println("We immediately Won");
				return move;
			}
			testBoard.remove(move);
		}

		//Does your move immediately allow the other player to win? If so, don't make it.
		for(int move:possibleMoves){
			testBoard.insert(this.OurID,move);
			for(int m:allPossibleMoves){
				if(g.isLegalMove(m, testBoard)){
					testBoard.insert(this.theirID, m);
					if(g.checkWin(testBoard, m, this.theirID) == this.theirID){
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
			testBoard.insert(this.theirID, move2);
			System.out.println(g.checkWin(testBoard, move2, this.theirID));
			if(g.checkWin(testBoard, move2, this.theirID) == this.theirID){
				System.out.println("We blocked an immediate win");
				return move2;
			}
			testBoard.remove(move2);
		}
		
		//Will the other player win with two moves? We need to move to interrupt i.e.(The classic example of two on the bottom with a space either side).
		for(int move:allPossibleMoves){
			testBoard.insert(this.theirID,move);
			for(int move2:allPossibleMoves){
				if(g.isLegalMove(move2, testBoard)){
					testBoard.insert(this.theirID, move2);
					if(g.checkWin(testBoard,move2,this.theirID) == this.theirID){
						return move2;
					}
					testBoard.remove(move2);
				}
			}
			testBoard.remove(move);
		}
		
		//if we haven't made an automatic move by this point, we have to decide between the remaining possible moves.
		int bestMove = -1;
		if(possibleMoves.size()!=0){
			for(int i = (int) testBoard.getColumnSize()/2; i>=0; i--){
				for(int m: possibleMoves){
					if(m+i == testBoard.getColumnSize() || m+i == 2*i){
						bestMove = m;
						break;
					}
				}
				if(bestMove != -1){
					break;
				}
			}
		}else{
			for(int i = (int) testBoard.getColumnSize()/2; i>=0; i--){
				for(int m: allPossibleMoves){
					if(m+i == testBoard.getColumnSize() || m+i == 2*i){
						bestMove = m;
						break;
					}
				}
				if(bestMove != -1){
					break;
				}
			}
		}

		return bestMove;
	}

	
	/*private void evaluateBoardPosition(){
		
	}*/
	
	public String toString() {
		return "I'm smart, I'm sorry :^)";
	}
}
