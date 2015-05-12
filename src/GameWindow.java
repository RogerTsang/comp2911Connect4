import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	//Game g;
	private GameBoardPanel boardPanel;
	
	public GameWindow() {
		initUI();
	}
	
	private void initUI() {
		
		//Create panel where board will go
		boardPanel = new GameBoardPanel();
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) translateClick(e.getX(), boardPanel.getWidth());
			}
		});
		
		//Create panel where game options will go
        JPanel optionsPanel = new JPanel();
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
		optionsPanel.add(quitButton);
        
		//Add both panels
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
        add(boardPanel);
        add(optionsPanel);
        
        //Set up window
		setTitle("First window");
		pack();
		setSize(625, 500);
		pane.setBackground(Color.GRAY);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void translateClick(int x, int boardWidth) {
		int buffer = 3;
		if (x%(boardWidth/7) < buffer || (x+buffer)%(boardWidth/7) < buffer) return;
		else System.out.println((int) Math.floor(x/(boardWidth/7)));
		
	}
	
	/*
	public updateUI(Board b) {
		TO DO ONCE WE INTEGRATE BACK-END
	}
	
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GameWindow window = new GameWindow();
				window.setVisible(true);
			}
		});
	}
}
