
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class DrawPanel extends JPanel {

    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        this.setSize(30,50*6);
        this.setBackground(Color.blue);
        g2d.setColor(new Color(125, 167, 116));
        for(int row =0;row<6;row++) g2d.fillOval(0, row*50, 30, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
}

public class RectanglesExample extends JFrame {

    public RectanglesExample() {
        initUI();
    }

    public final void initUI() {
    	
    	JPanel panel = new JPanel();
        DrawPanel dpnl = new DrawPanel();
        Test_Mouse2 lisentner = new Test_Mouse2(dpnl);
        panel.add(dpnl);
        add(panel);
        
        setSize(360, 300);
        setTitle("SampleBoard");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RectanglesExample ex = new RectanglesExample();
                ex.setVisible(true);
            }
        });
    }
}