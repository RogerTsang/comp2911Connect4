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

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	//Game g;
	private GameBoardPanel boardPanel;

	//Pass controller to GUI
	private IController gameController;
	
	//current column mouse point at
	private int mousePointingcolumn;
	
	//y coordination of falling dice
	private boolean mouseEnable;
	private boolean fallingAnimationMutex;
	
	//Info panels and panel for showing piece above column
	private JPanel p1Info;
	private JPanel p2Info;
	private Profile p1Profile;
	private Profile p2Profile;
	
	public GameWindow(IController g) {
		gameController = g;
		mouseEnable = true;
		fallingAnimationMutex = false;
		showOptions(false);
		initUI();
	}

	private void initUI() {
		this.mousePointingcolumn = -1;
		
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
		boardPanel = new GameBoardPanel(this.gameController.getBoard());
		//c.weightx = 0.5;
		
		//Player 1 info panel
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
        c.gridy = 1;
        c.gridwidth = 1;
        c.ipady = 0;
		
		//Options button
        JButton optionsButton = new JButton("Options");
        optionsButton.addActionListener(new ButtonAction());
        //c.gridy = 2;
        c.gridx = 1;
        add(optionsButton, c);
		
        //Restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ButtonAction());
        c.gridx = 2;
        add(restartButton, c);
        
		//Quit button
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ButtonAction());
		c.gridx = 3;
		add(quitButton, c);
		
		 /*
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
		*/
		
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
				int confirmQuit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Game", JOptionPane.OK_CANCEL_OPTION);
				if (confirmQuit == JOptionPane.OK_OPTION) System.exit(0);
				break;
			}	
			case "Restart": {
				gameController.newGame();
				gameController.startGame();
				mouseEnable = true;
				boardPanel.update(gameController.getBoard());
				boardPanel.updateUI();
				break;
			}
			case "Undo": {
				gameController.undo();
				if (!gameController.isFinish()) {
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
				}
				break;
			}/*
			case "Redo": {
				gameController.redo();
				if (!gameController.isFinish()) {
					boardPanel.update(gameController.getBoard());
					boardPanel.updateUI();
				}
				break;
			}*/
			case "Options": {
				showOptions(true);
				break;
			}
			default:break;
			}
		}
		
	}
	
	private void showOptions(boolean isInGame) {
		OptionsPanel options = new OptionsPanel((IGameOptions)gameController, gameController.getProfileNames());
		int option = JOptionPane.showConfirmDialog(this, options, "Choose players", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			p1Profile = gameController.getProfile(options.getPlayer1Name());
			p1Info = new ProfilePanel(p1Profile);
			if (options.getPlayer2Name() == "Novice CMP Opponent" || options.getPlayer2Name() == "Experienced CMP Opponent") {
				p2Info = new ProfilePanel(options.getPlayer2Name());
			} else {
				p2Profile = gameController.getProfile(options.getPlayer2Name());
				p2Info = new ProfilePanel(p2Profile);
			}
		} else {
			if (!isInGame) System.exit(0);
		}
	}
	
	public void letAImove() {
		int pushMousePointingColumn = mousePointingcolumn;
		boolean preEndGame = gameController.isFinish();
		if ((mousePointingcolumn = gameController.getAITurn()) < 0) {
			mousePointingcolumn = pushMousePointingColumn;
			return;
		}
		if (!preEndGame && !fallingAnimationMutex) {
			FallingAnimation();
			mousePointingcolumn = pushMousePointingColumn;
		} else {
			return;
		}
	}
	
	public void endGameUI() {
		boardPanel.highlightWinningLine(gameController.getBoard(), gameController.getWinningDiscs());
		boardPanel.updateUI();
	}
	
	private synchronized void FallingAnimation() {   
		Thread FallingThread = new Thread(new Falling());
		this.fallingAnimationMutex = true;
		FallingThread.start();
	}
	
	 private class Falling implements Runnable {
		private int y;
		 
		@Override
		public void run() {
			while (fallingAnimationMutex) {
				if (y < boardPanel.getHeight()){
					y += boardPanel.getHeight()/10;
					boardPanel.paintNextMove(gameController.getBoard(), mousePointingcolumn, y);
					//1000ms/60fps = 16.7ms 
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					y = 0;
					if (gameController.isFinish()) {
						mouseEnable = false;
						endGameUI();
					}
					fallingAnimationMutex = false;
				}
			}
		}
    }
	
	public class MouseAction extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (mouseEnable == true && fallingAnimationMutex == false) {
				int nextMove = -1;
				if (e.getButton() == MouseEvent.BUTTON1) {
				    nextMove = translateMouse(e.getX(), boardPanel.getWidth());
				}
				if (nextMove >= 0 && nextMove < gameController.getBoard().getColumnSize()) {
					if (gameController.makeMove(nextMove)) {
						mousePointingcolumn = nextMove;
						FallingAnimation();
						System.out.println("Human move made");
						if (gameController.hasAI() && !gameController.isFinish()) {
					        letAImove();
			            }
					}
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (mouseEnable == true && fallingAnimationMutex == false) {
				mousePointingcolumn = translateMouse(e.getX(), boardPanel.getWidth());
				boardPanel.highlightCol(gameController.getBoard(), gameController.getCurrentPlayer(), mousePointingcolumn);
				boardPanel.updateUI();
			}
		}
		
		private int translateMouse(int x, int boardWidth) {
			return (int) Math.floor(x/(boardWidth/7));
		}
	}
		
}

