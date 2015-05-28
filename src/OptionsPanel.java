import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
	
    private IGameOptions gameController;
    private ArrayList<String> nameList;
	private JComboBox<String> profileP1;
	private JComboBox<String> profileP2;
	private JComboBox<String> opponentOptionsList;
	private JComboBox<String> deleteProfile;
	private JLabel changesText;
	
	private DefaultComboBoxModel<String> p1model;
	private DefaultComboBoxModel<String> p2model;
	private DefaultComboBoxModel<String> deleteModel;
	
	public OptionsPanel(IGameOptions gameController, ArrayList<String> list) {
		this.gameController = gameController;
		nameList = list;
		this.initUI();
	}
	
	@SuppressWarnings("unchecked")
	private void initUI() {
	    
		//Create all the components
		//Player 1 and 2 configuration components
		String[] names = nameList.toArray(new String[nameList.size()]);
		ArrayList<String> listCopy = (ArrayList<String>) nameList.clone();
		p1model = new DefaultComboBoxModel<String>(names);		
		
		String toRemove = null;
		if(listCopy.size() > 1){
			for(String l:listCopy){
				if(l!="Guest") toRemove = l;
				break;
			}
		}
		listCopy.remove(toRemove);
		p2model = new DefaultComboBoxModel<String>(listCopy.toArray(new String[listCopy.size()]));
		
		deleteModel = new DefaultComboBoxModel<String>(names);
		profileP1 = new JComboBox<String>(p1model);
		profileP2 = new JComboBox<String>(p2model);
		
		gameController.setProfile(1, profileP1.getSelectedItem().toString());
		gameController.setProfile(2, profileP2.getSelectedItem().toString());
		
		profileP1.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent f){
				p2model.removeAllElements();
				
				ArrayList<String> listCopy = (ArrayList<String>) nameList.clone();
				
				if(!f.getItem().toString().equals("Guest")) listCopy.remove(f.getItem().toString());
				
				String[] p2Names = listCopy.toArray(new String[listCopy.size()]);
				
				for(String n:p2Names){
					if(gameController.getProfile(n) != null){
						p2model.addElement(n);
					}
				}
				profileP2 = new JComboBox<String>(p2model);
				gameController.setProfile(1,f.getItem().toString());
			}
		});
		
		profileP2.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent g){
				gameController.setProfile(2,g.getItem().toString());
			}
		});
		
		String[] player2Options = {"Human Opponent", "Novice CMP Opponent", "Experienced CMP Opponent"};
		opponentOptionsList = new JComboBox<String>(player2Options);
		opponentOptionsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().toString() == "Human Opponent") {
				    profileP2.setEnabled(true);
				    profileP2.setVisible(true);
				    gameController.removeAI();
				} else {
				    profileP2.setEnabled(false);
					profileP2.setVisible(false);
					if (e.getItem().toString() == "Novice CMP Opponent") {
					    gameController.addAI(new NoviceAI(Player.P2));
					} else {
						gameController.addAI(new ExperiencedAI(Player.P2));
					}
					
				};
			} 
		});
		
		//Delete profile components
		deleteProfile = new JComboBox<String>(deleteModel);
		final JButton deleteProfButton = new JButton("Delete Player");
		
		if(deleteProfile.getSelectedItem().equals("Guest")){
			deleteProfButton.setEnabled(false);
		}
		
		deleteProfile.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(deleteProfile.getSelectedItem().equals("Guest")){
					deleteProfButton.setEnabled(false);
				} else {
					deleteProfButton.setEnabled(true);
				}
			}
		});
		
		deleteProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDelete = JOptionPane.showConfirmDialog(null,
				        "Are you sure you want to delete this player?",
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
		
		// Create profile components
		final JTextField createProfile = new JTextField(10);
		JButton createProfButton = new JButton("Create New Player");
		createProfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!createProfile.getText().contains(" ") &&
					createProfile.getText().length() > 0 && 
					!nameList.contains(createProfile.getText())) {
					Profile newProfile = new Profile(createProfile.getText());
					gameController.saveProfile(newProfile);
					p1model.addElement(createProfile.getText());
					p2model.addElement(createProfile.getText());
					deleteModel.addElement(createProfile.getText());
					changesText.setText("Player added!");
					nameList.add(createProfile.getText());
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
		profileP1.setAlignmentX(Component.CENTER_ALIGNMENT);
		player1Panel.add(p1Text);
		player1Panel.add(profileP1);
		
		//Panel where Player 2 options will go
		JPanel player2Panel = new JPanel();
		player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
		player2Panel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
		
		JLabel oppText = new JLabel("Select opponent");
		oppText.setAlignmentX(Component.CENTER_ALIGNMENT);
		opponentOptionsList.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel p2Text = new JLabel("Select Player 2");
		p2Text.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileP2.setAlignmentX(Component.CENTER_ALIGNMENT);
		player2Panel.add(oppText);
		player2Panel.add(opponentOptionsList);
		player2Panel.add(p2Text);
		player2Panel.add(profileP2);
		
		
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
		
		JPanel title = new JPanel();
		title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
	    title.setAlignmentX(CENTER_ALIGNMENT);
	    /*
        try {
            JLabel picLabel = new JLabel(new ImageIcon(getClass().getResource("/Connect-4-Logo.png")), JLabel.CENTER);
            title.add(picLabel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		*/
        // Add panels to options panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(title);
		add(player1Panel);
		add(player2Panel);
		add(createProfilesPanel);
		add(deleteProfilesPanel);
		changesText = new JLabel();
		changesText.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(changesText);
		JLabel infoText = new JLabel("Your changes will be applied next game");
		infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(infoText);
	}
	
	public String getPlayer1Name() {
		if (profileP1.getSelectedItem() == null) return null;
		return profileP1.getSelectedItem().toString();
	}
	
	public String getPlayer2Name() {
		if (opponentOptionsList.getSelectedItem().toString() == "Human Opponent") {
			if (profileP1.getSelectedItem() == null) return null;
			return profileP2.getSelectedItem().toString();
		} else if (opponentOptionsList.getSelectedItem().toString() == "Novice CMP Opponent") {
			return "Novice CMP Opponent";
		} else {
			return "Experienced CMP Opponent";
		}
	}
}
