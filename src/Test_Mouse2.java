import java.awt.Color;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Test_Mouse2 implements MouseMotionListener,MouseListener{
	private int Xaxis;
	private int Yaxis;
	private JPanel boardPainter;
	private IController game;
	
	public Test_Mouse2(JPanel panel, IController game){
		Label label = new Label("");
		label.setBackground(Color.BLACK);
		panel.add(label, "South");
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.setVisible(true);
		this.boardPainter = panel;
		this.game = game;
	}
	
	@Override
	/**
	 * This method is used to control the game.
	 * Single click for insert.
	 * Doulbe click for restart.
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
//		System.out.print("mouse click----" + "\t");
		if (e.getClickCount() == 1) {
			this.game.move(Xaxis / 100);
			this.boardPainter.repaint();
		} else if (e.getClickCount() == 2) {
			this.game.newGame();
			this.game.startGame();
			this.boardPainter.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("press mouse");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("loose mouse");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.Xaxis = e.getX();
		this.Yaxis = e.getY();
	}

}