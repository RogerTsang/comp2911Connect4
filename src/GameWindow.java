import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener,MouseListener,MouseMotionListener{
	
	//Game g;
	private GameBoardPanel boardPanel;

	//Pass controller to GUI
	private IController gameController;
	
	//label for print information
	private JLabel label;
	
	//current column mouse point at
	private int col;
	
	public GameWindow(IController g) {
		gameController = g;
		initBoardPanel();
		initResize();
		initMouse();
		initUI();
	}
	
	private void initBoardPanel() {
		//Create panel where board will go
		boardPanel = new GameBoardPanel();
	}
	
	private void initResize() {
		this.setMinimumSize(new Dimension(300, 300));
		this.setResizable(true);
	}
	
	private void initMouse() {
		//This line is just for fun.
		//Your mouse indicates it is busy.
		//But it is not of course.
		this.setCursor(new Cursor(3));
		//Add Mouse Listener
		boardPanel.addMouseListener(this);
		boardPanel.addMouseMotionListener(this);
	}
	
	private void initUI() {
		this.col = -1;
		
		//Create panel where game options will go
		JPanel optionsPanel = new JPanel();
		GridLayout gl = new GridLayout(2,4,0,0);
		optionsPanel.setLayout(gl);
		
		//quit button
		JButton quitButton = new JButton("Quit");
		quitButton.setSize(50, 25);
		quitButton.addActionListener(this);
		//restart button
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(this);
		//restart with AI button
		JButton restartAIButton = new JButton("EnableAI");
		restartAIButton.addActionListener(this);
		//undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(this);
		//redo button
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(this);
		//Getscore button
		JButton scoreButton = new JButton("Score");
		scoreButton.addActionListener(this);
		//Request AI move button
		JButton AIMove = new JButton("AIMove");
		AIMove.addActionListener(this);
		
		optionsPanel.add(quitButton);
		optionsPanel.add(restartButton);
		optionsPanel.add(restartAIButton);
		optionsPanel.add(AIMove);
		optionsPanel.add(undoButton);
		optionsPanel.add(redoButton);
		optionsPanel.add(scoreButton);
		
		//JLable for show info
		JPanel labelPanel = new JPanel();
		label = new JLabel("info");
		labelPanel.add(label);
		
		//Add panels
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.SOUTH);
        
        //Set up window
		setTitle("First window");

		setSize(625, 625);
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
		  				   gameController.attachAI(new EasyAI(Player.P2));
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
		default:break;
		}
		//We need to update GUI when action is done
		//So I put the following two lines here
		boardPanel.update(gameController.getBoard());
		boardPanel.updateUI();
		
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
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		int boardWidth = boardPanel.getWidth();
		int x = e.getX();
		int buffer = 3;
		if (x%(boardWidth/7) < buffer || (x+buffer)%(boardWidth/7) < buffer) return;
		else {
			int col = (int) Math.floor(x/(boardWidth/7));
			if(col == this.col) return;
			else{
				boardPanel.highlightCol(gameController.getBoard(), gameController.getCurrentPlayer(), col);
				boardPanel.updateUI();
				this.col = col;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


		
		
}

