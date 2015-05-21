import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	//Game g;
	private GameBoardPanel boardPanel;

	//Pass controller to GUI
	private IController gameController;
	
	//label for print information
	private JLabel label;
	
	//current column mouse point at
	private int col;
	
	//Info panels and panel for showing piece above column
	JPanel p1Info;
	JPanel p2Info;
	JPanel aboveBoardPanel;
	
	public GameWindow(IController g) {
		gameController = g;
		initUI();
	}

	private void initUI() {
		this.col = -1;
		
		//Set up layout and components
		initLayout();
		
		//Set up mouse
		MouseAction action = new MouseAction();
		boardPanel.addMouseListener(action);
		boardPanel.addMouseMotionListener(action);
		
        //Set up window
		setTitle("Connect Four");
		setMinimumSize(new Dimension(870, 500));
		pack();
		setSize(870,500);
		setBackground(Color.GRAY);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initLayout() {
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		boardPanel = new GameBoardPanel();
		//c.weightx = 0.5;
		
		//Player 1 info panel
		Profile p1Profile = new Profile("Player 1");
		p1Info = new ProfilePanel(p1Profile);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.ipadx = 40;
		add(p1Info, c);
		
		//The panel above the board for displaying the piece when hovering over the column
		aboveBoardPanel = new JPanel();
		c.gridx = 1;
		c.gridheight = 1;
		c.gridwidth = 4;
		
		c.ipady = boardPanel.getWidth()/7;
		c.ipadx = 0;
		add(aboveBoardPanel, c);
		
		//Player 2 info panel
		Profile p2Profile = new Profile("Player 2");
		p2Info = new ProfilePanel(p2Profile);
		c.gridx = 5;
		c.gridy = 0;
		c.gridheight = 2;
		c.ipadx = 40;
		add(p2Info, c);
		
		//The board panel
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.gridheight = 1;
		c.gridwidth = 4;
		add(boardPanel, c);
		
		c.weightx = 1;
		//Quit button
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ButtonAction());
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.ipady = 0;
		add(quitButton, c);
		
		//Restart button
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new ButtonAction());
		c.gridx = 2;
		add(restartButton, c);
		
		//AI move button
		JButton AIMove = new JButton("AIMove");
		AIMove.addActionListener(new ButtonAction());
		c.gridx = 3;
		add(AIMove, c);
		
		//Enable AI button
		JButton restartAIButton = new JButton("EnableAI");
		restartAIButton.addActionListener(new ButtonAction());
		c.gridx = 4;
		add(restartAIButton, c);
		
		//Undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ButtonAction());
		c.gridx = 2;
		c.gridy = 3;
		add(undoButton, c);
		
		//Re-do button
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(new ButtonAction());
		c.gridx = 3;
		add(redoButton, c);
		
		
		//Options button
		JButton optionsButton = new JButton("Options");
		optionsButton.addActionListener(new ButtonAction());
		c.gridx = 1;
		add(optionsButton, c);
		
		//Label (temporary)
		label = new JLabel();
		c.gridx = 4;
		add(label, c);
	}
	
	private int translateMouse(int x, int boardWidth) {
		int buffer = 3;
		if (x%(boardWidth/7) < buffer || (x+buffer)%(boardWidth/7) < buffer) return - 1;
		else {
			int nextMove = (int) Math.floor(x/(boardWidth/7));
			return nextMove;
		}
		
	}
	
	public void updateUI() {
		boardPanel.update(gameController.getBoard());
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

	public class ButtonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()){
			case "Quit":System.exit(0);
						break;
			case "Restart":gameController.newGame();
						  gameController.detachAI();
						  gameController.startGame();
						  break;
			case "EnableAI":gameController.newGame();
			  				   gameController.attachAI(new SmartAI(Player.P2));
			  				   gameController.startGame();
			  				   break;
			case "AIMove": gameController.getAITurn();
						   break;
			case "Undo":gameController.undo();
						break;
			case "Redo":gameController.redo();
						break;
			case "Score":gameController.getPlayerScore(Player.P1);
						break;
			case "Options"://launch options menu
						break;
			default:break;
			}
			//We need to update GUI when action is done
			//So I put the following two lines here
			boardPanel.update(gameController.getBoard());
			boardPanel.updateUI();
			
			label.removeAll();
			label.setText(gameController.getInfo());
			label.updateUI();
		}
		
	}
	
	public class MouseAction extends MouseAdapter {
		private boolean mouseEnable = true;
		@Override
		public void mouseReleased(MouseEvent e) {
			if (mouseEnable == true) {
				int nextMove = -1;
				if (e.getButton() == MouseEvent.BUTTON1) nextMove = translateMouse(e.getX(), boardPanel.getWidth());
				if (nextMove >= 0 && nextMove <= 6) gameController.move(nextMove);
				updateUI();
				label.removeAll();
				label.setText(gameController.getInfo());
				label.updateUI();
			}
			
			if (!gameController.isFinish()) {
				mouseEnable = true;
			} else {
				endGameUI();
				mouseEnable = false;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (mouseEnable == true) {
				int column = translateMouse(e.getX(), boardPanel.getWidth());
				if(column == col) return;
				else{
					col = column;
					boardPanel.highlightCol(gameController.getBoard(), gameController.getCurrentPlayer(), col);
					boardPanel.updateUI();
				}
			}
		}
		
		/**
		 * This method is only called once when the game is finished
		 */
		public void endGameUI() {
			if (mouseEnable == true) {
				boardPanel.endGame(gameController.getBoard(), gameController.getWinningDiscs());
				boardPanel.updateUI();
			}
		}
	}
		
}

