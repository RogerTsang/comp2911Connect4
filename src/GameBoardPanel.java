import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {

	public GameBoardPanel() {
		//Create the board squares
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				GameSquare square = new GameSquare(Color.WHITE);
				add(square);
			}
		}
		//Set up look of board
		setBackground(Color.BLACK);
		setLayout(new GridLayout(6,7,0,0));
	}
	 
	/**
	 * When GUI is changed, the method should be always called.
	 * @param board
	 */
	public void update(Player[][] board) {
		this.removeAll();
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				GameSquare square;
				switch(board[col][row]) {
					case P1: square = new GameSquare(Color.RED); break;
					case P2: square = new GameSquare(Color.GREEN); break;
					case P1WIN: square = new GameSquare(Color.ORANGE); break;
					case P2WIN: square = new GameSquare(Color.CYAN); break;
					case NOONE: square = new GameSquare(Color.WHITE); break;
					default: square = new GameSquare(Color.BLACK); break;
				}
				add(square);
				
			}
		}
	}
	
	/**
	 * Highlight the current column.
	 * @param board Current Board state
	 * @param current Current Player
	 * @param col Current Column the mouse is on
	 */
	public void highlightCol(Player[][] board, Player current, int col){
	//repaint preCol
		this.removeAll();
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				GameSquare square;
				if (column == col) {
					Color currentPlayer;
					if (current == Player.P1) {
						currentPlayer = Color.RED;
					} else {
						currentPlayer = Color.GREEN;
					}
					switch(board[column][row]) {
						case P1: square = new GameSquare(Color.RED, currentPlayer); break;
						case P2: square = new GameSquare(Color.GREEN, currentPlayer); break;
						case NOONE: square = new GameSquare(Color.WHITE, currentPlayer); break;
						default: square = new GameSquare(Color.BLACK, currentPlayer); break;
					}
				} else {
					switch(board[column][row]) {
						case P1: square = new GameSquare(Color.RED); break;
						case P2: square = new GameSquare(Color.GREEN); break;
						case NOONE: square = new GameSquare(Color.WHITE); break;
						default: square = new GameSquare(Color.BLACK); break;
					}
				}
				add(square);
			}
		}
	}
	
	public void endGame(Player[][] board, Stack<Integer> winningDiscs) {
		this.removeAll();
		boolean[][] winningState = new boolean[7][6];
		
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				winningState[col][row] = false;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			int winRow = winningDiscs.pop();
			int winCol = winningDiscs.pop();
			winningState[winCol][winRow] = true;
		}
		
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				GameSquare square;
				switch(board[column][row]) {
					case P1: square = new GameSquare(Color.RED, winningState[column][row]); break;
					case P2: square = new GameSquare(Color.GREEN, winningState[column][row]); break;
					case NOONE: square = new GameSquare(Color.WHITE, winningState[column][row]); break;
					default: square = new GameSquare(Color.BLACK, winningState[column][row]); break;
				}
				add(square);
			}
		}
	}
	
	@Override
    public Dimension getPreferredSize() {
        Dimension d;
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(7, 6);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s*6/7);
    }
}
