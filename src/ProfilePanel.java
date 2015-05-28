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
	private JLabel wlRatioH;
	private JLabel wlRatioAIExperienced;
	private JLabel wlRatioAINovice;
	private JToggleButton stats;
	
	public ProfilePanel(Profile p) {
		initUI(p);
	}
	
	public ProfilePanel(String aiType) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		name = new JLabel(aiType);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(name);
	}
	
	private void initUI(Profile p) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JToggleButton stats = new JToggleButton("Show/Hide Stats");
		stats.addActionListener(new StatButtonListener());
		stats.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Name of profile
		name = new JLabel();
		name.setText(p.getName());
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
		gamesPlayed.setText("Number of games played: " + (int) p.getNumGamesPlayed());
		gamesPlayed.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioH = new JLabel();
		wlRatioH.setText("Ratio against Humans: " + p.getWLRatioH());
		wlRatioH.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIExperienced =  new JLabel();
		wlRatioAIExperienced.setText("Ratio against Hard AI: " + p.getWLRatioAIExperienced());
		wlRatioAIExperienced.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAINovice = new JLabel();
		wlRatioAINovice.setText("Ratio against Easy AI: " + p.getWLRatioAINovice());
		wlRatioAINovice.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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

	public void setProfile(Profile p) {
		name.setText(p.getName());
		gamesPlayed.setText("" + p.getNumGamesPlayed());
		wlRatioH.setText("" + p.getWLRatioH());
		wlRatioAIExperienced.setText("" + p.getWLRatioAIExperienced());
		wlRatioAINovice.setText("" + p.getWLRatioAINovice());
	}
	
	public class StatButtonListener implements ActionListener {
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
