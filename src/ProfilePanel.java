import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Panel used to display profile information to the sides of a game board
 * @author patrickgilfillan
 *
 */
@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {
	private static boolean playerOne = true;
	private ImageIcon avatar;
	private JLabel name;
	private JLabel gamesPlayed;
	private JLabel wlRatioH;
	private JLabel wlRatioAIExperienced;
	private JLabel wlRatioAINovice;
	private JToggleButton stats;
	
	/**
	 * Create a new panel which stores human profile information
	 * @param p Profile to display
	 */
	public ProfilePanel(Profile p) {
		initUI();
		setProfile(p);
		addComponents();
	}
	
	/**
	 * Create a new panel which represents an AI opponent
	 * @param aiType Name of AI
	 */
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
		name.setFont(name.getFont().deriveFont(Font.BOLD, 24));
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		//Roger: This line is used for general purpose for setting
		if (playerOne) {
			avatar = new ImageIcon(getClass().getResource("/Circle_Red.png"));
			playerOne = false;
		} else {
			avatar = new ImageIcon(getClass().getResource("/Circle_Green.png"));
			playerOne = true;
		}
		name.setIcon(avatar);
		
		//All the stats related to 
		gamesPlayed = new JLabel();
		gamesPlayed.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioH = new JLabel();
		wlRatioH.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIExperienced = new JLabel();
		wlRatioAIExperienced.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAINovice = new JLabel();
		wlRatioAINovice.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	/**
	 * Set or update the profile displaying on this panel
	 * @param p Profile to display on the profile
	 */
	public void setProfile(Profile p) {
		name.setText(p.getName());
		float WLRatioH = p.getWLRatioH() * 100;
		float WLRatioE = p.getWLRatioAIExperienced() * 100;
		float WLRatioN = p.getWLRatioAINovice() * 100;
		gamesPlayed.setText(String.format("Games played: " + p.getNumGamesPlayed()));
		wlRatioH.setText(String.format("VS Humans: %.2f%%", WLRatioH));
		wlRatioAIExperienced.setText(String.format("VS Experienced AI: %.2f%%",  WLRatioE));
		wlRatioAINovice.setText(String.format("VS Novice AI: %.2f%%", WLRatioN));
		if (p.getName().equals("Guest")) {
			stats.setVisible(false);
		} else {
			stats.setSelected(false);
			stats.setVisible(true);
		}	
	}
	
	/**
	 * Change this panel to represent an AI
	 * @param aiName
	 */
	public void changeToAIPanel(String aiName) {
		stats.setVisible(false);
		gamesPlayed.setVisible(false);
		wlRatioH.setVisible(false);
		wlRatioAIExperienced.setVisible(false);
		wlRatioAINovice.setVisible(false);
		this.name.setText(aiName);
	}
	
	/**
	 * Skips setting this as an AI panel
	 */
	public void skipSettingAIProfile() {
		playerOne = true;
	}
	
	private class StatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (gamesPlayed.isVisible() == false) {
				gamesPlayed.setVisible(true);
				wlRatioH.setVisible(true);
				wlRatioAIExperienced.setVisible(true);
				wlRatioAINovice.setVisible(true);
			} else {
				gamesPlayed.setVisible(false);
				wlRatioH.setVisible(false);
				wlRatioAIExperienced.setVisible(false);
				wlRatioAINovice.setVisible(false);
			}
		}
		
	}
}
