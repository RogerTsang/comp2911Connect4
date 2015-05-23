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
		for(int col = 0;col<7;col++){
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				GameSquare square = new GameSquare(Color.WHITE,false,0);
				add(square);
			}
		}
		//Set up look of board
		setBackground(Color.BLACK);
		setLayout(new GridLayout(7,7,0,0));
	}
	 
	/**
	 * When GUI is changed, the method should be always called.
	 * @param board
	 */
	public void update(Player[][] board) {
		this.removeAll();
		for(int col = 0;col<7;col++){
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				GameSquare square;
				switch(board[col][row]) {
					case P1: square = new GameSquare(Color.RED,false,0); break;
					case P2: square = new GameSquare(Color.GREEN,false,0); break;
					case NOONE: square = new GameSquare(Color.WHITE,false,0); break;
					default: square = new GameSquare(Color.BLACK,false,0); break;
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
		
		Color currentPlayer;
		if (current == Player.P1) {
			currentPlayer = Color.RED;
		} else {
			currentPlayer = Color.GREEN;
		}
		for(int colum = 0;colum<7;colum++){
			if(colum != col){
				GameSquare square = new GameSquare(true,Color.WHITE,false,0);
				add(square);
			}else{
				GameSquare square = new GameSquare(true,currentPlayer,false,0);
				add(square);
			}
			
		}
		
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				GameSquare square;
				if (column == col) {
					switch(board[column][row]) {
						case P1: square = new GameSquare(Color.RED, currentPlayer); break;
						case P2: square = new GameSquare(Color.GREEN, currentPlayer); break;
						case NOONE: square = new GameSquare(Color.WHITE, currentPlayer); break;
						default: square = new GameSquare(Color.BLACK, currentPlayer); break;
					}
				} else {
					switch(board[column][row]) {
						case P1: square = new GameSquare(Color.RED,false,0); break;
						case P2: square = new GameSquare(Color.GREEN,false,0); break;
						case NOONE: square = new GameSquare(Color.WHITE,false,0); break;
						default: square = new GameSquare(Color.BLACK,false,0); break;
					}
				}
				add(square);
			}
		}
	}
	
	
	public void paintNextMove(Player[][] board ,int col,int y){
		this.removeAll();
		for(int c = 0;c<7;c++){
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		//get lastMove: [col][row]
		int row;
		for(row = 0;row<6;row++){
			if(board[col][row] != Player.NOONE) break;
		}
		int height = this.getHeight();
		int fallingHeight = (height/6)*(row+1);
	//	for(int y = 0 ; y <fallingHeight;y++){
			
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				GameSquare square;
				if(c==col && y<this.getHeight()/7*row){
					switch(board[col][row]) {
					case P1: square = new GameSquare(Color.RED,true,y-this.getHeight()/7*r); break;
					case P2: square = new GameSquare(Color.GREEN,true,y-this.getHeight()/7*r); break;
					case NOONE: square = new GameSquare(Color.WHITE,true,y-this.getHeight()/7*r); break;
					default: square = new GameSquare(Color.BLACK,true,y-this.getHeight()/7*r); break;
				}
					
					
				}else{
						
					switch(board[c][r]) {
						case P1: square = new GameSquare(Color.RED,false,0); break;
						case P2: square = new GameSquare(Color.GREEN,false,0); break;
						case NOONE: square = new GameSquare(Color.WHITE,false,0); break;
						default: square = new GameSquare(Color.BLACK,false,0); break;
					}
				}
				add(square);
				}
			}
				
				updateUI();
					
	//	}
			
			
		
	}
	
	
	
	
	public void endGame(Player[][] board, Stack<Integer> winningDiscs) {
		this.removeAll();
		boolean[][] winningState = new boolean[7][6];
		for(int colum = 0;colum<7;colum++){
			GameSquare square = new GameSquare(true,Color.WHITE,false,0);
			add(square);
		}
		
		
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
