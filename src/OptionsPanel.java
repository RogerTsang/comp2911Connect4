import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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
	JComboBox<String> deleteProfile;
	JComboBox<String> player2OptionsList;
	IController gameController;
	JLabel changesText;
	ArrayList<String> nameList;
	
	DefaultComboBoxModel<String> p1model;
	DefaultComboBoxModel<String> p2model;
	DefaultComboBoxModel<String> deleteModel;
	
	public OptionsPanel(IController gameController, ArrayList<String> list) {
		this.gameController = gameController;
		nameList = list;
		
		initUI();
	}
	
	private void initUI() {
		//Create all the components
		//Player 1 and 2 configuration components
		String[] names = nameList.toArray(new String[nameList.size()]);
		p1model = new DefaultComboBoxModel<String>(names);
		names[0] = "";
		p2model = new DefaultComboBoxModel<String>(names);
		names = nameList.toArray(new String[nameList.size()]);
		deleteModel = new DefaultComboBoxModel<String>(names);
		profile1 = new JComboBox<String>(p1model);
		profile2 = new JComboBox<String>(p2model);
		profile1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent f){
				p2model.removeAllElements();
				nameList.remove(f.getItem().toString());
				String[] p2Names = nameList.toArray(new String[nameList.size()]);
				nameList.add(f.getItem().toString());
				for(String n:p2Names){
					p2model.addElement(n);
				}
				profile2 = new JComboBox<String>(p2model);
			}
		});
		
		String[] player2Options = {"Human opponent", "Easy Computer opponent", "Hard Computer opponent"};
		player2OptionsList = new JComboBox<String>(player2Options);
		player2OptionsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString() != "Human opponent") profile2.setEnabled(false);
				else{
					profile2.setEnabled(true);
					
				};
			} 
		});
		
		//Delete profile components
		deleteProfile = new JComboBox<String>(deleteModel);
		JButton deleteProfButton = new JButton("Delete Player");
		deleteProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this player?", 
						                                          "Confirm delete", JOptionPane.YES_NO_OPTION);
				if (confirmDelete == JOptionPane.OK_OPTION) {
					gameController.deleteProfile((String) deleteProfile.getSelectedItem());
					nameList.remove(deleteProfile.getSelectedItem());
					p1model.removeElement(deleteProfile.getSelectedItem());
					p2model.removeElement(deleteProfile.getSelectedItem());
					deleteModel.removeElement(deleteProfile.getSelectedItem());
					changesText.setText("Player deleted");
				}
			}
			
		});
		
		//Create profile components
		final JTextField createProfile = new JTextField(10);
		JButton createProfButton = new JButton("Create New Player");
		createProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!createProfile.getText().contains(" ") && !nameList.contains(createProfile.getText())) {
					Profile newProfile = new Profile(createProfile.getText());
					gameController.saveProfile(newProfile);
					p1model.addElement(createProfile.getText());
					p2model.addElement(createProfile.getText());
					deleteModel.addElement(createProfile.getText());
					changesText.setText("Player added!");
				} else {
					changesText.setText("Name not valid");
				}
				createProfile.setText("");
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
		changesText = new JLabel();
		changesText.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(changesText);
		JLabel infoText = new JLabel("Your changes will start next game");
		infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(infoText);
	}
	
	public String getPlayer1Name() {
		if (profile1.getSelectedItem() == null) return null;
		return profile1.getSelectedItem().toString();
	}
	
	public String getPlayer2Name() {
		if (player2OptionsList.getSelectedItem().toString() == "Human opponent") {
			if (profile1.getSelectedItem() == null) return null;
			return profile2.getSelectedItem().toString();
		} else if (player2OptionsList.getSelectedItem().toString() == "Easy Computer opponent") {
			return "Easy AI";
		} else {
			return "Hard AI";
		}
	}
}
