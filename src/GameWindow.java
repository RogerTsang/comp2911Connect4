import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

	//Pass controller to GUI
	private IController gameController;
	
	public GameWindow(IController g) {
		gameController = g;
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
        //quit button
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(3,1,0,0));
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
		//restart button
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameController.newGame();
				gameController.startGame();

				boardPanel.update(gameController.getBoard());
				boardPanel.updateUI();
			}
			
		});
		//undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.undo();
				boardPanel.update(gameController.getBoard());
				boardPanel.updateUI();
			}
			
		});
		optionsPanel.add(quitButton);
		optionsPanel.add(restartButton);
		optionsPanel.add(undoButton);
		
        
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
		else {
			int nextMove = (int) Math.floor(x/(boardWidth/7));
			this.gameController.move(nextMove);
			updateUI();
		}
		
	}
	
	public void updateUI() {
		boardPanel.update(gameController.getBoard());
		boardPanel.updateUI();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			IController game = new GameSystem();
			@Override
			public void run() {
				GameWindow window = new GameWindow(game);
				//The following two lines should be handle by GUI
				game.newGame();
				game.startGame();
				window.setVisible(true);
			}
		});
	}
}
