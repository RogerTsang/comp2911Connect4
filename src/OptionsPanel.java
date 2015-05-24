import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	
	JComboBox<String> profile1;
	JComboBox<String> profile2;
	JComboBox<String> player2OptionsList;
	
	public OptionsPanel() {
		initUI();
	}
	
	private void initUI() {
		//Create all the components
		String[] names = {"TEST_NAME_1", "TEST_NAME_2"};
		profile1 = new JComboBox<String>(names);
		profile2 = new JComboBox<String>(names);
		String[] player2Options = {"Human opponent", "Easy Computer opponent", "Hard Computer opponent"};
		player2OptionsList = new JComboBox<String>(player2Options);
		player2OptionsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString() != "Human opponent") profile2.setEnabled(false);
				else profile2.setEnabled(true);
			}
			
		});
		final JTextField createProfile = new JTextField(10);
		JButton createProfButton = new JButton("Create new profile");
		createProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!createProfile.getText().contains(" ")) {
					System.out.println(createProfile.getText());
					createProfile.setText("Profile added!");
				}
			}
		});
		
		final JComboBox<String> deleteProfile = new JComboBox<String>(names);
		JButton deleteProfButton = new JButton("Delete Profile");
		deleteProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this player?", 
						                                          "Confirm delete", JOptionPane.YES_NO_OPTION);
				if (confirmDelete == JOptionPane.OK_OPTION) System.out.println("Will delete");
			}
			
		});
		
		//Panel where Player 1 options will go
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
		player1Panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
		
		JLabel p1Text = new JLabel("Select Player 1");
		p1Text.setAlignmentX(Component.CENTER_ALIGNMENT);
		profile1.setAlignmentX(Component.CENTER_ALIGNMENT);
		player1Panel.add(p1Text);
		player1Panel.add(profile1);
		
		//Panel where Player 2 options will go
		JPanel player2Panel = new JPanel();
		player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
		player2Panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
		
		JLabel oppText = new JLabel("Select opponent");
		oppText.setAlignmentX(Component.CENTER_ALIGNMENT);
		player2OptionsList.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel p2Text = new JLabel("Select Player 2 (if opponent is human)");
		p2Text.setAlignmentX(Component.CENTER_ALIGNMENT);
		profile2.setAlignmentX(Component.CENTER_ALIGNMENT);
		player2Panel.add(oppText);
		player2Panel.add(player2OptionsList);
		player2Panel.add(p2Text);
		player2Panel.add(profile2);
		
		
		//Panel for creating profiles
		JPanel createProfilesPanel = new JPanel();
		createProfilesPanel.setLayout(new GridBagLayout());
		createProfilesPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
	
		createProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
		createProfButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel createProfText = new JLabel("Type new name below, with no spaces");
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
		createProfilesPanel.add(createProfText, c);
		c.gridy = 1; c.gridwidth = 1;
		createProfilesPanel.add(createProfile, c);
		c.gridx = 1; c.gridwidth = 1;
		createProfilesPanel.add(createProfButton, c);
		
		//Panel for deleting profiles
		JPanel deleteProfilesPanel = new JPanel();
		deleteProfilesPanel.setLayout(new GridBagLayout());
		deleteProfilesPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
	
		createProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
		createProfButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel deleteProfText = new JLabel("Choose profile to delete");
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.BOTH;
		c2.gridx = 0; c.gridy = 0; c.gridwidth = 2;
		deleteProfilesPanel.add(deleteProfText, c2);
		c2.gridy = 1; c.gridwidth = 1;
		deleteProfilesPanel.add(deleteProfile, c2);
		c2.gridx = 1; c.gridwidth = 1;
		deleteProfilesPanel.add(deleteProfButton, c2);
		
		//Add panels to options panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(player1Panel);
		add(player2Panel);
		add(createProfilesPanel);
		add(deleteProfilesPanel);
		JLabel infoText = new JLabel("Your changes will start next game");
		infoText.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(infoText);
	}
	
	public String getPlayer1Name() {
		return profile1.getSelectedItem().toString();
	}
	
	public String getPlayer2Name() {
		if (player2OptionsList.getSelectedItem().toString() == "Human opponent") {
			return profile2.getSelectedItem().toString();
		} else if (player2OptionsList.getSelectedItem().toString() == "Easy Computer opponent") {
			return "Easy AI";
		} else {
			return "Hard AI";
		}
	}
}
