import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class NoviceAI implements Iai {

    private Player OurID;
    private Player OppID;
    
    public NoviceAI(Player id) {
        this.OurID = id;
        if (id == Player.P1) {
            this.OppID = Player.P2; 
        } else {
            this.OppID = Player.P1;
        }
    }
    
    @Override
    public int makeMove(IGame g, Board b) {
        int move = 0;
        
        // get a list of all legal moves
        List<Integer> possibleMoves = new LinkedList<Integer>();
        for (int col = 0; col < b.getColumnSize(); col++) {
            if (g.isLegalMove(col)) {
                possibleMoves.add(col);
            }
        }
        
        Random r = new Random();
        int randomElement = r.nextInt(possibleMoves.size());
        move = possibleMoves.get(randomElement);
        
        for (int col : possibleMoves) {
            // if we can win on this turn then we will
            b.insert(this.OurID, col);
            if (g.checkWin(b,col,this.OurID) == this.OurID) {
                move = col;
                break;
            }
            b.remove(col);
            // if they can win on this turn we will stop them
            b.insert(this.OppID, col);
            if (g.checkWin(b,col,this.OppID) == this.OppID) {
                move = col;
                break;
            }
            b.remove(col);
        }
        
        return move;
    }

    public String type(){
    	return "Novice";
    }
    
}
