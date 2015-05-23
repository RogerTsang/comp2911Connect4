import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.GradientPaint;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameSquare extends JPanel {
	
	private Ellipse2D.Double circle;
	private Color currentColor;
	private Color highlighter;
	private final int ALPHA = 120;
	
	/**
	 * The new square unit with disc highlight
	 * @param c Disc Color
	 * @param ol Disc highlight toggle
	 */
	public GameSquare(Color c, boolean ol) {
		currentColor = c;
		highlighter = null;
		if (ol) {
			setBackground(Color.YELLOW);
		} else {
			setBackground(Color.BLUE);
		}
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Create a square unit with Highlighted transparent.
	 * @param c Current Disc Color
	 * @param h Highlighter Color
	 */
	public GameSquare(Color c, Color h) {
		currentColor = c;
		highlighter = new Color(h.getRed(), h.getGreen(), h.getBlue(), ALPHA);
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Create a square unit with no Highlighter.
	 * @param c Current Disc Color
	 */
	public GameSquare(Color c) {
		currentColor = c;
		highlighter = null;
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	/**
	 * Create a square unit for position of dropping dice
	 * @param i dropping dice identifier
	 * @param c color of Dice dropping
	 */
	public GameSquare(boolean i,Color c){
		currentColor = c;
		highlighter = null;
		setBackground(Color.WHITE);
		
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		float squareCircleRatio = 0.75f;
		int width = getWidth();
		float x = width/2 - width*squareCircleRatio/2;
		float y = getHeight()/2 - width*squareCircleRatio/2;
		
		//Set anti-aliasing and draw circle
		g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		circle = new Ellipse2D.Double(x,y,width*squareCircleRatio,width*squareCircleRatio);
		g2d.setColor(currentColor);
		g2d.fill(circle);
		
		if (highlighter != null) {
			getHighlighter(g2d);
		}
	}
	
	private void getHighlighter(Graphics2D g2d) {
		Rectangle2D.Double rectangle= new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
		GradientPaint highLighter = new GradientPaint(0,0,
                new Color(0,0,0,ALPHA), this.getWidth()/2, 0,highlighter, true);
		setBackground(Color.BLUE);
		g2d.setPaint(highLighter);
		g2d.fill(rectangle);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		
	}
}
