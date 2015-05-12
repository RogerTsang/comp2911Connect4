
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
	
	
	
    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);
        
        for(int row = 1;row<8;row++){
     
        		g2d.fillOval(0,(row-1)*50,30,30);
        }
        
   }
            

    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(Color.ORANGE);
        this.setSize(30, 5*50+30);
        super.paintComponent(g);
        doDrawing(g);
        
    }
    
    
    
	
	

	
}



public class BasicBoard extends JFrame {

    public BasicBoard() {
        initUI();
    }

    public final void initUI() {
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(1,5,0,0));
    	panel.setBackground(Color.black);
    	panel.setSize(210,300);
    	
    	
    	/*for(int col = 7;col>0;col--){
    		DrawBoard dpnl = new DrawBoard();
    		panel.add(dpnl);
    		
    	}
    	*/
    	DrawBoard dpnl = new DrawBoard();
    	 Test_Mouse2 mouseL = new Test_Mouse2(dpnl);
       
		panel.add(dpnl);
        add(panel);
       
        
        setSize(210, 300);
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



