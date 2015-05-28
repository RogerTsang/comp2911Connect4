import java.util.ArrayList;

public class ExperiencedAI implements Iai {

    private Player ourID;
	private Player oppID;
	
	/**
	 * Constructor that sets the ID of the AI.
	 * @param p Player type that is the AI's ID.
	 */
	public ExperiencedAI(Player p) {
		this.ourID = p;
		if (p == Player.P1) {
			this.oppID = Player.P2;
		} else {
			this.oppID = Player.P2;
		}
		return;
	}	
	
	@Override
	public int makeMove(IGame g, Board b) {
	    
	    // These are moves we want the AI to make
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		// This is all legal moves
		ArrayList<Integer> allPossibleMoves = new ArrayList<Integer>();
		// This array allows us to avoid concurrent modification errors.
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		
		// Adding all legal moves. We remove moves later on if they are poor.
		for (int col = 0; col < b.getColumnSize(); col++) {
			if (g.isLegalMove(b,col)) {
				possibleMoves.add(col);
				allPossibleMoves.add(col);
			}
		}
		
		for (int col : possibleMoves) {
            // If we can win on this turn then we will
            b.insert(this.ourID, col);
            if (g.checkWin(b,col,this.ourID) == this.ourID) {
                return col;
            }
            b.remove(col);
            // if they can win on this turn we will stop them
            b.insert(this.oppID, col);
            if (g.checkWin(b,col,this.oppID) == this.oppID) {
                return col;
            }
            b.remove(col);
        }

		// Does your move immediately allow the other player to win? If so, don't make it.
		for (int ourMove : possibleMoves) {
			b.insert(this.ourID,ourMove);
			for (int oppMove : allPossibleMoves) {
				if (g.isLegalMove(b,oppMove)) {
					b.insert(this.oppID,oppMove);
					if (g.checkWin(b,oppMove,this.oppID) == this.oppID) {
						toRemove.add(ourMove);
					}
					b.remove(oppMove);
				}
			}
			b.remove(ourMove);
		}

		if (toRemove.size() != 0) {
			for (int move : toRemove) {
				if (possibleMoves.indexOf(move) != -1) {
					possibleMoves.remove(possibleMoves.indexOf(move));
				}
			}
		}
		toRemove.clear();
		
		// Will the other player win with two moves?
		// We need to move to interrupt
		// i.e.(The classic example of two on the bottom with a space either side)
		for (int ourMove : allPossibleMoves) {
			b.insert(this.oppID,ourMove);
			for (int oppMove:allPossibleMoves) {
				if (g.isLegalMove(b,oppMove)) {
					b.insert(this.oppID,oppMove);
					if (g.checkWin(b,oppMove,this.oppID) == this.oppID) {
						return oppMove;
					}
					b.remove(oppMove);
				}
			}
			b.remove(ourMove);
		}
		
		// If we haven't made an automatic move by this point
		// we have to decide between the remaining possible moves.
		int bestMove = -1;
		if (possibleMoves.size() != 0) {
			for (int col = b.getColumnSize()/2; col >= 0; col--) {
				for (int ourMove : possibleMoves) {
					if (ourMove + col == b.getColumnSize() || ourMove + col == 2*col) {
						bestMove = ourMove;
						break;
					}
				}
				if (bestMove != -1) {
					break;
				}
			}
		} else {
			for (int col = b.getColumnSize()/2; col >= 0; col--) {
				for (int ourMove : allPossibleMoves) {
					if (ourMove + col == b.getColumnSize() || ourMove + col == 2*col) {
						bestMove = ourMove;
						break;
					}
				}
				if (bestMove != -1) {
					break;
				}
			}
		}

		return bestMove;
	}
	
	@Override
	public String getDifficulty() {
	    return "Experienced";
	}
	
}
