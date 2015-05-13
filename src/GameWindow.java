import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener,MouseListener{
	
	//Game g;
	private GameBoardPanel boardPanel;

	//Pass controller to GUI
	private GameSystem gameController;
	
	//label for print information
	private JLabel label;
	
	public GameWindow(GameSystem g) {
		gameController = g;
		initUI();
	}
	
	private void initUI() {
		
		//Create panel where board will go
		boardPanel = new GameBoardPanel();
		boardPanel.addMouseListener(this); 		
		//Create panel where game options will go
        //quit buttonc
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(4,1,0,0));
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		//restart button
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(this);
		//undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(this);
		optionsPanel.add(quitButton);
		optionsPanel.add(restartButton);
		optionsPanel.add(undoButton);
		
		//JLable for show info
		label = new JLabel("info");
		optionsPanel.add(label);
		
		
		
        
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
			GameSystem game = new GameSystem();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
		case "Quit":System.exit(0);
					break;
		case "Restart":gameController.newGame();
					  gameController.startGame();
					  boardPanel.update(gameController.getBoard());
					  boardPanel.updateUI();
					  break;
		case "Undo":gameController.undo();
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
					break;
		default:break;
		}
		this.label.removeAll();
		this.label.setText(gameController.getInfo());
		label.updateUI();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) translateClick(e.getX(), boardPanel.getWidth());
		this.label.removeAll();
		this.label.setText(gameController.getInfo());
		label.updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


		
		
}

