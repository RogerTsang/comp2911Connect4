import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameSquare extends JPanel {
	
	private final int CIRCLE_DIAMETER = 50;
	private Ellipse2D.Double circle;
	private Color currentColor;
	
	public GameSquare(Color c) {
		currentColor = c;
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		float x = getWidth()/2 - CIRCLE_DIAMETER/2;
		float y = getHeight()/2 - CIRCLE_DIAMETER/2;
		
		//Set anti-aliasing and draw circle
		g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		circle = new Ellipse2D.Double(x,y,CIRCLE_DIAMETER,CIRCLE_DIAMETER);
		g2d.setColor(currentColor);
		g2d.fill(circle);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
}
