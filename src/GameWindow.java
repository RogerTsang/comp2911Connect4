import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	//Game g;
	private GameBoardPanel boardPanel;

	//Pass controller to GUI
	private IController gameController;
	
	//current column mouse point at
	private int col;
	
	private Timer timer;
	
	//y coordination of falling dice
	private int y ;
	
	
	//Info panels and panel for showing piece above column
	JPanel p1Info;
	JPanel p2Info;
	JPanel aboveBoardPanel;
	
	public GameWindow(IController g) {
		y = 0;
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
		c.gridheight = 1;
		c.ipadx = 40;
		add(p1Info, c);
		//Undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ButtonAction());
		undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Info.add(undoButton);
		
		//Player 2 info panel
		Profile p2Profile = new Profile("Player 2");
		p2Info = new ProfilePanel(p2Profile);
		c.gridx = 5;
		c.gridy = 0;
		c.gridheight = 1;
		c.ipadx = 40;
		add(p2Info, c);
		
		//The board panel
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 0;
		c.gridheight = 1;
		c.gridwidth = 4;
		add(boardPanel, c);
		
		c.weightx = 1;
		//Quit button
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ButtonAction());
		c.gridx = 1;
		c.gridy = 1;
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
		
		//Options button
		JButton optionsButton = new JButton("Options");
		optionsButton.addActionListener(new ButtonAction());
		c.gridy = 2;
		c.gridx = 1;
		add(optionsButton, c);
		
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
			case "Quit": {
				System.exit(0);
				break;
			}	
			case "Restart": {
				gameController.newGame();
				gameController.detachAI();
				gameController.startGame();
				boardPanel.update(gameController.getBoard());
				boardPanel.updateUI();
				break;
			}
			case "EnableAI": {
				gameController.newGame();
				gameController.attachAI(new SmartAI(Player.P2));
				gameController.startGame();
				boardPanel.update(gameController.getBoard());
				boardPanel.updateUI();
				break;
			}
			case "AIMove": {
				gameController.getAITurn();
				if (!gameController.isFinish()) {
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
				} else {
					boardPanel.endGame(gameController.getBoard(), gameController.getWinningDiscs());
					boardPanel.updateUI();
				}
				break;
			}
			case "Undo": {
				gameController.undo();
				if (!gameController.isFinish()) {
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
				}
				break;
			}
			case "Redo": {
				gameController.redo();
				if (!gameController.isFinish()) {
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
				}
				break;
			}
			case "Options": {
				showOptions(true);
				break;
			}
			default:break;
			}
		}
		
	}
	
	private void showOptions(boolean isInGame) {
		OptionsPanel options = new OptionsPanel();
		Object[] message = {options};
		int option = JOptionPane.showConfirmDialog(this, message, "Choose players", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			System.out.println(options.getPlayer1Name() + " " + options.getPlayer2Name());
		}
	}
	
	  private  void  FallingAnimation() {   
		  	FallingListener fallingListener = new FallingListener();
	        this.timer = new Timer(1, fallingListener);
	        timer.start();
	    }
	
	 private class FallingListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 if( y < boardPanel.getHeight()){
					y = y +5;
					boardPanel.paintNextMove(gameController.getBoard(), col,y);
					
				 }else{
					 y = 0;
					 timer.stop();
				 }
			}
	    	
	    }
	
	public class MouseAction extends MouseAdapter {
		private boolean mouseEnable = true;
		@Override
		public void mouseReleased(MouseEvent e) {
			if (mouseEnable == true) {
				int nextMove = -1;
				if (e.getButton() == MouseEvent.BUTTON1) nextMove = translateMouse(e.getX(), boardPanel.getWidth());
				if (nextMove >= 0 && nextMove <= 6){
					if(gameController.move(nextMove)){
						col = nextMove;
						FallingAnimation();
						
						
					}
				
				}
			}
			mouseMoved(e);
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
				col = column;
				boardPanel.highlightCol(gameController.getBoard(), gameController.getCurrentPlayer(), col);
				boardPanel.updateUI();
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
		
		private int translateMouse(int x, int boardWidth) {
			int buffer = 3;
			if (x%(boardWidth/7) < buffer || (x+buffer)%(boardWidth/7) < buffer) return - 1;
			else {
				int nextMove = (int) Math.floor(x/(boardWidth/7));
				return nextMove;
			}
			
		}
	}
		
}

