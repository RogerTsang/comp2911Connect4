import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Easy Connect4 AI
 * @author patrickgilfillan
 *
 */
public class NoviceAI implements Iai {

    private Player OurID;
    private Player OppID;
    
    /**
     * Constructor that sets the ID of the AI.
     * @param id Player type that is the AI's ID.
     */
    public NoviceAI(Player id) {
        this.OurID = id;
        if (id == Player.P1) {
            this.OppID = Player.P2; 
        } else {
            this.OppID = Player.P1;
        }
    }
    
    /**
     * Get AI to select a move
     * @return Column number
     */
    @Override
    public int makeMove(IGame g, Board b) {
        int move = 0;
        
        // get a list of all legal moves
        List<Integer> possibleMoves = new LinkedList<Integer>();
        for (int col = 0; col < b.getColumnSize(); col++) {
            if (g.isLegalMove(b,col)) {
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
    
    @Override
    public String getDifficulty() {
    	return "Novice";
    }
    
}
