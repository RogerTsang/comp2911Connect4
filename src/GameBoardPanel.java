import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

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
	
	public void update(Player[][] board) {
		this.removeAll();
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				GameSquare square;
				switch(board[col][row]) {
				case P1: square = new GameSquare(Color.RED); break;
				case P2: square = new GameSquare(Color.GREEN); break;
				case NOONE: square = new GameSquare(Color.WHITE); break;
				default: square = new GameSquare(Color.BLACK); break;
				}
				add(square);
				square.repaint();
			}
		}
		
		//Set up look of board
		setBackground(Color.BLACK);
		setLayout(new GridLayout(6,7,0,0));
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