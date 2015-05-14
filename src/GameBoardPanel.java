import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {

	public GameBoardPanel() {
		//Create the board squares
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 6; row++) {
				GameSquare square = new GameSquare(Color.WHITE, 0);
				add(square);
			}
		}
		
		//Set up look of board
		setBackground(Color.BLACK);
		setLayout(new GridLayout(6,7,0,0));
	}
	  
	public void update(Player[][] board) {
		this.removeAll();
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				GameSquare square;
				switch(board[col][row]) {
				case P1: square = new GameSquare(Color.RED,0); break;
				case P2: square = new GameSquare(Color.GREEN,0); break;
				case NOONE: square = new GameSquare(Color.WHITE,0); break;
				default: square = new GameSquare(Color.BLACK,0); break;
				}
				add(square);
				
			}
		}
		
		
		//Set up look of board 
	}
	
	
	public void highlightCol(Player[][] board,int col){
	//repaint preCol
		this.removeAll();
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 7; column++) {
				
				GameSquare square;
				if(column == col){
				switch(board[column][row]) {
				case P1: square = new GameSquare(Color.RED,100);
								break;
				case P2: square = new GameSquare(Color.GREEN,100); 
								break;
				case NOONE: square = new GameSquare(Color.WHITE,100);
									break;
				default: square = new GameSquare(Color.BLACK,100); 
							break;
				}
				}
				else{
				switch(board[column][row]) {
				case P1: square = new GameSquare(Color.RED,0); break;
				case P2: square = new GameSquare(Color.GREEN,0); break;
				case NOONE: square = new GameSquare(Color.WHITE,0); break;
				default: square = new GameSquare(Color.BLACK,0); break;
				}
					
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
