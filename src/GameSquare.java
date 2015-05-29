import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.GradientPaint;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameSquare extends JPanel {
	
	private Ellipse2D.Double circle;
	private Color currentColor;
	private Color highlighter;
	private final int ALPHA;
	// Moving index
	private boolean MI;
	// Position
	private int y;
	
	/**
	 * The basic panel with colored circle inside
	 * @param c The color of circle
	 */
	public GameSquare(Color c) {
		currentColor = c;
		ALPHA = 120;
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		highlighter = null;
		MI = false;
		y = 0;
	}
	
	/**
	 * The new square unit with disc winning highlight
	 * @param c Disc Color
	 * @param criticalDisc Discs which make the win
	 */
	public GameSquare(Color c, boolean criticalDisc) {
		currentColor = c;
		highlighter = null;
		ALPHA = 120;
		if (criticalDisc) {
			setBackground(Color.YELLOW);
		} else {
			setBackground(Color.BLUE);
		}
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MI = false;
		y = 0;
	}
	
	/**
	 * Create a square unit with Highlighted transparent.
	 * @param c Current Disc Color
	 * @param h Highlighter Color
	 */
	public GameSquare(Color c, Color h) {
		currentColor = c;
		ALPHA = 120;
		highlighter = new Color(h.getRed(), h.getGreen(), h.getBlue(), ALPHA);
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		MI = false;
		y = 0;
	}
	
	/**
	 * Create a square unit with no Highlighter.
	 * @param c Current Disc Color
	 */
	public GameSquare(Color c, boolean MI, int y) {
		currentColor = c;
		ALPHA = 120;
		highlighter = null;
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.MI = MI;
		this.y = y;
	}
	
	/**
	 * Create a square unit for position of dropping dice
	 * @param i dropping dice indicator
	 * @param c color of Dice dropping
	 */
	public GameSquare(boolean i, Color c, boolean MI, int y){
		currentColor = c;
		ALPHA = 120;
		highlighter = null;
		setBackground(Color.WHITE);
		this.MI = MI;
		this.y = y;
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		float squareCircleRatio = 0.85f;
		int width = getWidth();
		if (getHeight() < width) width = getHeight();
		float x = getWidth()/2 - width*squareCircleRatio/2;
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
		if (MI == true) {
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f);

	        BufferedImage buffImg = new BufferedImage(this.getWidth(), this.getHeight(),
	                    BufferedImage.TYPE_INT_ARGB);
	        Graphics2D gbi = buffImg.createGraphics();
	        
	        gbi.setPaint(Color.white);
	        circle = new Ellipse2D.Double(x,y,width*squareCircleRatio,width*squareCircleRatio);
	        gbi.setRenderingHint(
	        		RenderingHints.KEY_ANTIALIASING, 
	        		RenderingHints.VALUE_ANTIALIAS_ON);
	        gbi.fill(circle);
	        gbi.setComposite(ac);
	        y=this.y;
	       

	        gbi.setPaint(currentColor);
	        circle = new Ellipse2D.Double(x,y,width*squareCircleRatio,width*squareCircleRatio);
	        gbi.fill(circle);
	        
	        g2d.drawImage(buffImg, 0, 0, null);
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
