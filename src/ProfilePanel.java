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
	private JLabel wlRatioAIHard;
	private JLabel wlRatioAIEasy;
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
		wlRatioAIHard =  new JLabel();
		wlRatioAIHard.setText("Ratio against Hard AI: " + p.getWLRatioAIHard());
		wlRatioAIHard.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIEasy = new JLabel();
		wlRatioAIEasy.setText("Ratio against Easy AI: " + p.getWLRatioAIEasy());
		wlRatioAIEasy.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		gamesPlayed.setVisible(false);
		wlRatioH.setVisible(false);
		wlRatioAIHard.setVisible(false);
		wlRatioAIEasy.setVisible(false);
		add(name);
		add(stats);
		add(gamesPlayed);
		add(wlRatioH);
		add(wlRatioAIHard);
		add(wlRatioAIEasy);
	}

	public void setProfile(Profile p) {
		name.setText(p.getName());
		gamesPlayed.setText("" + p.getNumGamesPlayed());
		wlRatioH.setText("" + p.getWLRatioH());
		wlRatioAIHard.setText("" + p.getWLRatioAIHard());
		wlRatioAIEasy.setText("" + p.getWLRatioAIEasy());
	}
	
	public class StatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (gamesPlayed.isVisible() == false) {
				gamesPlayed.setVisible(true);
				wlRatioH.setVisible(true);
				wlRatioAIHard.setVisible(true);
				wlRatioAIEasy.setVisible(true);
			} else {
				gamesPlayed.setVisible(false);
				wlRatioH.setVisible(false);
				wlRatioAIHard.setVisible(false);
				wlRatioAIEasy.setVisible(false);
			}
		}
		
	}
}
