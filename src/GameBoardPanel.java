import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {
	
	//Layout
	//CurrentIndex = ROW * 7 + COL
	private GridLayout boardLayout;
    
    /**
     * Constructor that creates the visual grid composing of individual game squares.
     * @param b
     */
	public GameBoardPanel(Board b) {
	    
		//Create the board squares
		for (int col = 0; col < b.getColumnSize(); col++) {
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}

		for (int col = 0; col < b.getColumnSize(); col++) {
			for (int row = 0; row < b.getRowSize(); row++) {
				GameSquare square = new GameSquare(Color.WHITE,false,0);
				add(square);
			}
		}

		//Set up look of board
		setBackground(Color.BLACK);
		boardLayout = new GridLayout(b.getRowSize()+1,b.getColumnSize(),0,0);
		setLayout(boardLayout);
	}
	
	/**
	 * Changes the current GUI to reflect the current state of the game board.
	 * @param board A board object reflecting the current game board.
	 */
	public void update(Board b) {
		this.removeAll();
		
		//The very first row for disc indicator
		for (int col = 0; col < b.getColumnSize();col++) {
			GameSquare square = new GameSquare(true, Color.WHITE, false, 1);
			add(square, boardLayout, col);
		}
		
		for (int row = 0; row < b.getRowSize(); row++) {
			for (int col = 0; col < b.getColumnSize(); col++) {
				GameSquare square;
				switch(b.getState()[col][row]) {
					case P1:
					    square = new GameSquare(Color.RED); break;
					case P2:
					    square = new GameSquare(Color.GREEN); break;
					default:
					    square = new GameSquare(Color.WHITE); break;
				}
				add(square, boardLayout, (row+1) * 7 + col);
			}
		}
	}
	
	/**
	 * Highlights a column with the colour of the current player.
	 * @param b Board object that has the current board state.
	 * @param current Current Player whos turn it is.
	 * @param col The selected column to be highlighted.
	 */
	public void highlightCol(Board b, Player current, int column, int previousColumn) {
		Color currentPlayer;
		if (current == Player.P1) {
			currentPlayer = Color.RED;
		} else {
			currentPlayer = Color.GREEN;
		}
		
	    //remove previous column
		for (int row = 6; row >= 0; row--) {
			this.remove(row * 7 + previousColumn);
		}
		
		GameSquare preindicator = new GameSquare(true, Color.WHITE, false, 0);
		add(preindicator, boardLayout, previousColumn);
		
		for (int row = 0; row < b.getRowSize(); row++) {
			GameSquare square;
			switch (b.getState()[previousColumn][row]) {
				case P1:
				    square = new GameSquare(Color.RED); break;
				case P2:
				    square = new GameSquare(Color.GREEN); break;
				default:
				    square = new GameSquare(Color.WHITE); break;
			}
			add(square, boardLayout, (row+1) * 7 + previousColumn);
		}
		
	    //repaint current highlight column
		for (int row = 6; row >= 0; row--) {
			this.remove(row * 7 + column);
		}
		
		GameSquare indicator = new GameSquare(true, currentPlayer, false, 0);
		add(indicator, boardLayout, column);
			
		for (int row = 0; row < b.getRowSize(); row++) {
			GameSquare square;
			switch (b.getState()[column][row]) {
				case P1:
				    square = new GameSquare(Color.RED, currentPlayer); break;
				case P2:
				    square = new GameSquare(Color.GREEN, currentPlayer); break;
				default:
				    square = new GameSquare(Color.WHITE, currentPlayer); break;
			}
			add(square, boardLayout, (row+1) * 7 + column);
		}
		
	}
	
	/**
	 * Draws a disc in a given column at a given position which is used for the animation.
	 * @param b Board object that has the current board state.
	 * @param col Column the disc is to be drawn in.
	 * @param y The exact y-coordinate of the disc to be drawn.
	 */
	public void paintNextMove(Board b, int col, int y) {
    
		this.removeAll();
		
		for (int c = 0; c < b.getColumnSize(); c++) {
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		
		// Get lastMove: [col][row]
		int row = -1;
		for (row = 0; row < b.getRowSize(); row++) {
			if (b.getState()[col][row] != Player.NOONE) {
			    break;
			}
		}
		for (int r = 0; r < b.getRowSize(); r++) {
			for (int c = 0; c < b.getColumnSize(); c++) {
				GameSquare square;
				if (c == col && r <= row && y < this.getHeight()/b.getColumnSize()*row) {
					switch (b.getState()[col][row]) {
    					case P1:
    					    square = new GameSquare(Color.RED,true,y-this.getHeight()/b.getColumnSize()*r); break;
    					case P2:
    					    square = new GameSquare(Color.GREEN,true,y-this.getHeight()/b.getColumnSize()*r); break;
    					default:
    					    square = new GameSquare(Color.WHITE,true,y-this.getHeight()/b.getColumnSize()*r); break;

					}	
				} else {
					switch (b.getState()[c][r]) {
						case P1:
						    square = new GameSquare(Color.RED); break;
						case P2:
						    square = new GameSquare(Color.GREEN); break;
						default:
						    square = new GameSquare(Color.WHITE); break;
					}
				}
				add(square);
				}
			}
		
		updateUI();	
		
	}
	
	/**
	 * Highlights the winning line of discs.
	 * @param b Board object that has the current board state.
	 * @param winningDiscs A Stack that has the winning disc coordinates; popping the row then the column.
	 */
	public void highlightWinningLine(Board b, Stack<Integer> winningDiscs) {
	    
		this.removeAll();
		
		boolean[][] winningState = new boolean[b.getColumnSize()][b.getRowSize()];
		
		for (int col = 0; col < b.getColumnSize(); col++) {
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		
		for (int row = 0; row < b.getRowSize(); row++) {
			for (int col = 0; col < b.getColumnSize(); col++) {
				winningState[col][row] = false;
			}
		}
		
		int connectToWin = winningDiscs.size()/2;
		for (int i = 0; i < connectToWin; i++) {
			int winRow = winningDiscs.pop();
			int winCol = winningDiscs.pop();
			winningState[winCol][winRow] = true;
		}
		
		for (int row = 0; row < b.getRowSize(); row++) {
			for (int col = 0; col < b.getColumnSize(); col++) {
				GameSquare square;
				switch (b.getState()[col][row]) {
					case P1:
					    square = new GameSquare(Color.RED, winningState[col][row]); break;
					case P2:
					    square = new GameSquare(Color.GREEN, winningState[col][row]); break;
					default:
					    square = new GameSquare(Color.WHITE, winningState[col][row]); break;
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
            return new Dimension(7,6);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s*6/7);
    }
}
