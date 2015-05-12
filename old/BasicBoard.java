
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Shape;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class DrawBoard extends JPanel {
	Player[][] board;
	
	public DrawBoard(Player[][] currentBoard) {
		this.board = currentBoard;
	}
	
	public void initBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        for(int row = 0; row < 6; row++){
        	for(int col = 0; col < 7; col++) {
        		g2d.fillOval(col * 100 + 10, row * 100 + 10, 80, 80);
        	}
        }
   }
	
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(int row = 0; row < 6; row++){
        	for(int col = 0; col < 7; col++) {
        		switch(this.board[col][row]) {
        		case P1: g2d.setColor(Color.RED); break;
        		case P2: g2d.setColor(Color.BLUE); break;
        		case NOONE: g2d.setColor(Color.WHITE); break;
        		}
        		g2d.fillOval(col * 100 + 10, row * 100 + 10, 80, 80);
        	}
        }
   }
            
    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(Color.BLACK);
        this.setSize(700, 600);
        super.paintComponent(g);
        doDrawing(g);
    }

}

public class BasicBoard extends JFrame {
	private IController GameControl;

    public BasicBoard() {
    	this.GameControl = new GameSystem();
    	this.GameControl.newGame();
    	this.GameControl.startGame();
        initUI();
    }

    public final void initUI() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1,5,0,0));
    	panel.setBackground(Color.black);
    	panel.setSize(700,600);
    	
    	DrawBoard dpnl = new DrawBoard(this.GameControl.getBoard());
    	Test_Mouse2 mouseL = new Test_Mouse2(dpnl, GameControl);
       
		panel.add(dpnl);
        add(panel);
        
        setSize(1024, 768);
        setTitle("Board");
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
    	
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            BasicBoard ex = new BasicBoard();
	            ex.setVisible(true);
	        }
	    });
    }
}



