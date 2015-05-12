import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {
	
	public GameBoardPanel() {
		//Create the board squares
		for (int i = 0; i < 6*7; i++) {
			GameSquare square = new GameSquare();
			add(square);
		}
		
		//Set up look of board
		setBackground(Color.BLACK);
		setLayout(new GridLayout(6,7,0,0));
	}
	
	/*
	public void repaint(Board b) {
		TO DO ONCE WE INTEGRATE BACK-END
	}
	*/
	
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
