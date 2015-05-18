import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class HardAI implements Iai {
	
	private Player OurID;
	private Player OppID;
	
	public HardAI(Player id) {
	    this.OurID = id;
	    if (id == Player.P1) {
	        this.OppID = Player.P2; 
	    } else {
	        this.OppID = Player.P1;
	    }
	}

	@Override
	public int makeMove(IGame game, Board b) {
	    int centreColumn = b.getColumnSize()/2;
	    int move = centreColumn;
	    
	    // get a list of all legal moves
	    List<Integer> possibleMoves = new LinkedList<Integer>();
	    for (int col = 0; col < b.getColumnSize(); col++) {
	        if (game.isLegalMove(col)) {
	            possibleMoves.add(col);
	        }
	    }
	    if (!possibleMoves.contains(centreColumn)) move = possibleMoves.get(0); 
	    
	    for (int col : possibleMoves) {
	        // if we can win on this turn then we will
	        b.insert(this.OurID, col);
	        if (game.checkWin(b,col,this.OurID) == this.OurID) {
	            System.out.println("We Win");
	            move = col;
	            break;
	        }
	        b.remove(col);
	        // if they can win on this turn we will stop them
	        b.insert(this.OppID, col);
            if (game.checkWin(b,col,this.OppID) == this.OppID) {
                System.out.println("Stop Them!");
                move = col;
                break;
            }
            b.remove(col);
	    }
	    
	    // next we check to see if they have have two in a row
	    Queue<List<Integer>> q = new LinkedList<List<Integer>>();
	    for (int col = 0; col < b.getColumnSize(); col++) {
	        for (int row = 0; row < b.getRowSize(); row++) {
	            if (b.getState()[col][row] == this.OppID) {
	                List<Integer> l = new LinkedList<Integer>(); 
	                l.add(col);
	                l.add(row);
	                q.add(l);
	            }
	        }
	    }
	    while (!q.isEmpty()) {
	        List<Integer> l = q.poll();
	        int col = l.get(0);
	        int row = l.get(1);
	        // check whether the cell is in the edge or corner
	        if (col == 0 || col == b.getColumnSize() || row == 0 || row ==b.getRowSize()) {
	            
	        } else {
	        }
	    }
	    
	        return move;
	    }
}
