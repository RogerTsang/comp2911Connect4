import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {
	
	JLabel name;
	JLabel gamesPlayed;
	JLabel wlRatioH;
	JLabel wlRatioAIHard;
	JLabel wlRatioAIEasy;
	JToggleButton stats;
	
	public ProfilePanel(Profile p) {
		initUI(p);
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
		
		//All the stats related to 
		gamesPlayed = new JLabel();
		gamesPlayed.setText("" + (int) p.getNumGamesPlayed());
		gamesPlayed.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioH = new JLabel();
		wlRatioH.setText("" + p.getWLRatioH());
		wlRatioH.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIHard =  new JLabel();
		wlRatioAIHard.setText("" + p.getWLRatioAIHard());
		wlRatioAIHard.setAlignmentX(Component.CENTER_ALIGNMENT);
		wlRatioAIEasy = new JLabel();
		wlRatioAIEasy.setText("" + p.getWLRatioAIEasy());
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
