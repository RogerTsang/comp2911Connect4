import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {
	
	JLabel name;
	JLabel gamesPlayed;
	JLabel wlRatioH;
	JLabel wlRatioAIHard;
	JLabel wlRatioAIEasy;
	
	public ProfilePanel(Profile p) {
		initUI(p);
	}
	
	private void initUI(Profile p) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		name = new JLabel();
		name.setText(p.getName());
		gamesPlayed = new JLabel();
		gamesPlayed.setText("" + p.getNumGamesPlayed());
		wlRatioH = new JLabel();
		wlRatioH.setText("" + p.getWLRatioH());
		wlRatioAIHard =  new JLabel();
		wlRatioAIHard.setText("" + p.getWLRatioAIHard());
		wlRatioAIEasy = new JLabel();
		wlRatioAIEasy.setText("" + p.getWLRatioAIEasy());
		
		add(name);
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
}
