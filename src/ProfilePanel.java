import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {
	private static boolean playerOne = true;
	private ImageIcon avatar;
	private JLabel name;
	private JLabel gamesPlayed;
	private JLabel ratioLabel;
	private JLabel wlRatioH;
	private JLabel wlRatioAIExperienced;
	private JLabel wlRatioAINovice;
	private JLabel spacer;
	private JToggleButton stats;
	
	public ProfilePanel(Profile p) {
		initUI();
		setProfile(p);
		addComponents();
	}
	
	public ProfilePanel(String aiType) {
		initUI();
		stats.setVisible(false);
		name.setText(aiType);
		addComponents();
	}
	
	private void addComponents() {
		gamesPlayed.setVisible(false);
		wlRatioH.setVisible(false);
		wlRatioAIExperienced.setVisible(false);
		wlRatioAINovice.setVisible(false);
		add(name);
		add(stats);
		add(gamesPlayed);
		add(wlRatioH);
		add(wlRatioAIExperienced);
		add(wlRatioAINovice);
	}
	
	private void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		stats = new JToggleButton("Show/Hide Stats");
		stats.addActionListener(new StatButtonListener());
		stats.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Name of profile
		name = new JLabel();
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		//Roger: This line is used for general purpose for setting
		if (playerOne) {
			avatar = new ImageIcon("res/Circle_Red.png");
			playerOne = false;
		} else {
			avatar = new ImageIcon("res/Circle_Green.png");
			playerOne = true;
		}
		name.setIcon(avatar);
		
		//All the stats related to 
		gamesPlayed = new JLabel();
		//gamesPlayed.setText("No. Games Played: " + p.getNumGamesPlayed());
		//gamesPlayed.setAlignmentX(Component.CENTER_ALIGNMENT);
		ratioLabel = new JLabel();
		//ratioLabel.setText("Ratios:");
		//ratioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		spacer = new JLabel();
		//spacer.setText(" ");
		//spacer.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioH = new JLabel();
		//wlRatioH.setText("Against Humans: " + String.format("%.2f",p.getWLRatioH()));
		//wlRatioH.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIExperienced =  new JLabel();
		//wlRatioAIExperienced.setText("Against Experienced AI: " + String.format("%.2f",p.getWLRatioAIExperienced()));
		//wlRatioAIExperienced.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAINovice = new JLabel();
		//wlRatioAINovice.setText("Against Novice AI: " + String.format("%.2f",p.getWLRatioAINovice()));
		//wlRatioAINovice.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		gamesPlayed.setVisible(false);
		ratioLabel.setVisible(false);
		wlRatioH.setVisible(false);
		wlRatioAIExperienced.setVisible(false);
		wlRatioAINovice.setVisible(false);
		spacer.setVisible(false);
		add(name);
		add(stats);
		add(gamesPlayed);
		add(spacer);
		add(ratioLabel);
		add(wlRatioH);
		add(wlRatioAIExperienced);
		add(wlRatioAINovice);
	}
	
	public void setProfile(Profile p) {
		name.setText(p.getName());
		gamesPlayed.setText("Number of games played: " + p.getNumGamesPlayed());
		wlRatioH.setText("Ratio against Humans: " + p.getWLRatioH());
		wlRatioAIExperienced.setText("Ratio against Experienced AI: " + p.getWLRatioAIExperienced());
		wlRatioAINovice.setText("Ratio against Novice AI: " + p.getWLRatioAINovice());
		if (!stats.isVisible()) stats.setVisible(true); 
	}
	
	public void changeToAIPanel(String aiName) {
		stats.setVisible(false);
		this.name.setText(aiName);
	}
	
	public void update(Profile p){
		this.removeAll();
		this.initUI();
		this.setProfile(p);
		this.updateUI();
	}
	
	public class StatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (gamesPlayed.isVisible() == false) {
				gamesPlayed.setVisible(true);
				spacer.setVisible(true);
				ratioLabel.setVisible(true);
				wlRatioH.setVisible(true);
				wlRatioAIExperienced.setVisible(true);
				wlRatioAINovice.setVisible(true);
			} else {
				gamesPlayed.setVisible(false);
				spacer.setVisible(false);
				ratioLabel.setVisible(false);
				wlRatioH.setVisible(false);
				wlRatioAIExperienced.setVisible(false);
				wlRatioAINovice.setVisible(false);
			}
		}
		
	}
}
