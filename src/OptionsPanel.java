import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	
    private IGameOptions gameOptions;
    private ArrayList<String> nameList;
	private JComboBox<String> P1ComboBox;
	private JComboBox<String> P2ComboBox;
	private JComboBox<String> opponentOptionsList;
	private JComboBox<String> deleteComboBox;
	private JLabel changesText;
	private BufferedImage titleLogo;
	private JPanel titlePanel;
	private JPanel P1OptionsPanel;
    private JPanel P2OptionsPanel;
	private JPanel PVPPanel;
	
	private DefaultComboBoxModel<String> P1ComboModel;
	private DefaultComboBoxModel<String> P2ComboModel;
	private DefaultComboBoxModel<String> DeleteComboModel;
	
	/**
	 * Create a new options panel
	 * @param gameController Options interface for managing profiles
	 * @param list List of the names of the profiles
	 */
	public OptionsPanel(IGameOptions gameOptions, ArrayList<String> list) {
		this.gameOptions = gameOptions;
		nameList = list;				
		this.initialiseGUI();
	}
	
	@SuppressWarnings("unchecked")
	private void initialiseGUI() {
	    
	    // Change the background colour
	    UIManager.put("OptionPane.background",new ColorUIResource(195,220,250));
	    UIManager.put("Panel.background",new ColorUIResource(195,220,250));

	    // Add the title image
	    try {
            titleLogo = ImageIO.read(new File("res/connect4Logo.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
	    titlePanel = new JPanel();
	    titlePanel.setBackground(new ColorUIResource(195,220,250));
	    titlePanel.setAlignmentX(CENTER_ALIGNMENT);
	    JLabel titleLabel = new JLabel(new ImageIcon(titleLogo));
	    titlePanel.add(titleLabel);
	    
	    // Create the panel for Player P1 options
        P1OptionsPanel = new JPanel();
        P1OptionsPanel.setLayout(new BoxLayout(P1OptionsPanel, BoxLayout.Y_AXIS));
        P1OptionsPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
        P1OptionsPanel.setBackground(new ColorUIResource(195,220,250));
        // Create a panel for the instruction label
        JPanel P1InstrutionLabelPanel = new JPanel();
        P1InstrutionLabelPanel.setAlignmentX(CENTER_ALIGNMENT);
        P1InstrutionLabelPanel.setBackground(Color.WHITE);
        JLabel P1InstructionLabel = new JLabel("Select Player 1");
        P1InstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        P1InstrutionLabelPanel.add(P1InstructionLabel);
        P1OptionsPanel.add(P1InstrutionLabelPanel);
        
        // Fill the P1 and Delete combo box box with profiles and subsequently add to the
        // P2 combo box all profiles except that selected in P1
        String[] names = nameList.toArray(new String[nameList.size()]);
        ArrayList<String> listCopy = (ArrayList<String>) nameList.clone();
        P1ComboModel = new DefaultComboBoxModel<String>(names);     
        if (nameList.size() > 1) P1ComboModel.setSelectedItem(nameList.get(1));
        if(!P1ComboModel.getSelectedItem().equals("Guest")) {
            listCopy.remove(P1ComboModel.getSelectedItem());
        }
        P2ComboModel = new DefaultComboBoxModel<String>(listCopy.toArray(new String[listCopy.size()]));
        
        // Create the actual combo boxes from the models
        DeleteComboModel = new DefaultComboBoxModel<String>(names);
        P1ComboBox = new JComboBox<String>(P1ComboModel);
        P1ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        P2ComboBox = new JComboBox<String>(P2ComboModel);
        P2ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        P1ComboBox.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent f){
                P2ComboModel.removeAllElements();
                ArrayList<String> listCopy = (ArrayList<String>) nameList.clone();
                if (!f.getItem().toString().equals("Guest")) {
                    listCopy.remove(f.getItem().toString());
                }
                String[] p2Names = listCopy.toArray(new String[listCopy.size()]);
                for (String name : p2Names) {
                    if(gameOptions.getProfile(name) != null){
                        P2ComboModel.addElement(name);
                    }
                }
                P2ComboBox = new JComboBox<String>(P2ComboModel);
            }
        });
        
        // Add the P1ComboBox to the P1Options panel
        ((JLabel)P1ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        P1OptionsPanel.add(P1ComboBox);
        
        // Create the panel for Player P2 options
        P2OptionsPanel = new JPanel();
        P2OptionsPanel.setLayout(new BoxLayout(P2OptionsPanel, BoxLayout.Y_AXIS));
        P2OptionsPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
        P2OptionsPanel.setBackground(new ColorUIResource(195,220,250));
        // Create the panel for game mode
        JPanel P2InstrutionLabelPanel = new JPanel();
        P2InstrutionLabelPanel.setAlignmentX(CENTER_ALIGNMENT);
        P2InstrutionLabelPanel.setBackground(Color.WHITE);
        JLabel P2InstructionLabel = new JLabel("Select Opponent");
        P2InstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        P2InstrutionLabelPanel.add(P2InstructionLabel);
        P2OptionsPanel.add(P2InstrutionLabelPanel);
        // Create the combo box for game modes
        String[] player2Options = {"Human Opponent", "Novice Computer", "Experienced Computer"};
        opponentOptionsList = new JComboBox<String>(player2Options);
        opponentOptionsList.setAlignmentX(Component.CENTER_ALIGNMENT);
        ((JLabel)opponentOptionsList.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        P2OptionsPanel.add(opponentOptionsList);
        opponentOptionsList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().toString() == "Human Opponent") {
                    P2ComboBox.setEnabled(true);
                    PVPPanel.setVisible(true);
                } else {
                    P2ComboBox.setEnabled(false);
                    PVPPanel.setVisible(false);
                };
            } 
        });
        // Create the panel for P.V.P mode
        PVPPanel = new JPanel();
        PVPPanel.setLayout(new BoxLayout(PVPPanel, BoxLayout.Y_AXIS));
        PVPPanel.setAlignmentX(CENTER_ALIGNMENT);
        PVPPanel.setBackground(Color.WHITE);
        JPanel PVPSelectLabelPanel = new JPanel();
        PVPSelectLabelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PVPSelectLabelPanel.setBackground(Color.WHITE);
        JLabel PVPSelectLabel = new JLabel("Select Player 2");
        PVPSelectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PVPSelectLabelPanel.add(PVPSelectLabel);
        PVPPanel.add(PVPSelectLabelPanel);
        ((JLabel)P2ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        PVPPanel.add(P2ComboBox);
        P2OptionsPanel.add(PVPPanel);
		
        // Create the panel for creating players
        JPanel createProfilesPanel = new JPanel();
        createProfilesPanel.setLayout(new BoxLayout(createProfilesPanel,BoxLayout.Y_AXIS));
        createProfilesPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
        createProfilesPanel.setBackground(new ColorUIResource(195,220,250));
        JPanel createLabelPanel = new JPanel();
        createLabelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createLabelPanel.setBackground(Color.WHITE);
        JLabel createLabel = new JLabel("Type new name below, with no spaces");
        createLabelPanel.add(createLabel);
        createProfilesPanel.add(createLabelPanel);
        final JTextField createProfile = new JTextField(20);
        createProfile.setHorizontalAlignment(JTextField.CENTER);
        JButton createButton = new JButton("Create New Player");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!createProfile.getText().contains(" ") &&
                    createProfile.getText().length() > 0 && 
                    !nameList.contains(createProfile.getText())) {
                    Profile newProfile = new Profile(createProfile.getText());
                    gameOptions.saveProfile(newProfile);
                    P1ComboModel.addElement(createProfile.getText());
                    P2ComboModel.addElement(createProfile.getText());
                    DeleteComboModel.addElement(createProfile.getText());
                    changesText.setText("Player added!");
                    nameList.add(createProfile.getText());
                } else {
                    changesText.setText("Name not valid");
                }
                createProfile.setText("");
            }
        });
        JPanel createInput = new JPanel();
        createInput.setAlignmentX(CENTER_ALIGNMENT);
        createInput.setBackground(Color.WHITE);
        createProfile.setPreferredSize(new Dimension(250,26));
        createInput.add(createProfile);
        createInput.add(createButton);
        createProfilesPanel.add(createInput);
       
		// Create the panel for Delete options
        JPanel deleteProfilesPanel = new JPanel();
        deleteProfilesPanel.setLayout(new BoxLayout(deleteProfilesPanel,BoxLayout.Y_AXIS));
        deleteProfilesPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK)));
        deleteProfilesPanel.setBackground(new ColorUIResource(195,220,250));
        JPanel deleteLabelPanel = new JPanel();
        deleteLabelPanel.setAlignmentX(CENTER_ALIGNMENT);
        deleteLabelPanel.setBackground(Color.WHITE);
        JLabel deleteLabel = new JLabel("Choose profile to delete");
        deleteLabelPanel.add(deleteLabel);
        deleteProfilesPanel.add(deleteLabelPanel);
        JPanel deleteInput = new JPanel();
        deleteInput.setAlignmentX(CENTER_ALIGNMENT);
        deleteInput.setBackground(Color.WHITE);
		deleteComboBox = new JComboBox<String>(DeleteComboModel);
		final JButton deleteButton = new JButton("    Delete Player    ");
		if(deleteComboBox.getSelectedItem().equals("Guest")){
			deleteButton.setEnabled(false);
		}
		deleteComboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(deleteComboBox.getSelectedItem().equals("Guest")){
					deleteButton.setEnabled(false);
				} else {
					deleteButton.setEnabled(true);
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDelete = JOptionPane.showConfirmDialog(null,
				        "Are you sure you want to delete this player?",
				        "Confirm delete", JOptionPane.YES_NO_OPTION);
				if (confirmDelete == JOptionPane.OK_OPTION) {
					gameOptions.deleteProfile((String) deleteComboBox.getSelectedItem());
					nameList.remove(deleteComboBox.getSelectedItem());
					P1ComboModel.removeElement(deleteComboBox.getSelectedItem());
					P2ComboModel.removeElement(deleteComboBox.getSelectedItem());
					DeleteComboModel.removeElement(deleteComboBox.getSelectedItem());
					changesText.setText("Player deleted");
				}
			}
		});
		deleteComboBox.setPreferredSize(new Dimension(225,26));
		((JLabel)deleteComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		deleteInput.add(deleteComboBox);
		deleteInput.add(deleteButton);
		deleteProfilesPanel.add(deleteInput);
		
		// Changes info panel
		JPanel infoPanel = new JPanel();
		//infoPanel.setLayout(new BoxLayout(deleteProfilesPanel,BoxLayout.Y_AXIS));
		infoPanel.setBackground(new ColorUIResource(195,220,250));
        changesText = new JLabel();
        changesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(changesText);
        JLabel infoText = new JLabel("Your changes will be applied next game");
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(infoText);
		
        // Add panels to options panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(titlePanel);
		add(P1OptionsPanel);
		add(P2OptionsPanel);
		add(createProfilesPanel);
		add(deleteProfilesPanel);
		add(infoPanel);
	}
	
	/**
	 * Get the name of the selected player 1
	 * @return Name of player 1
	 */
	public String getPlayer1Name() {
		if (P1ComboBox.getSelectedItem() == null) return null;
		return P1ComboBox.getSelectedItem().toString();
	}
	
	/**
	 * Get the name of the selected player 2
	 * @return Name of player 2
	 */
	public String getPlayer2Name() {
		if (opponentOptionsList.getSelectedItem().toString() == "Human Opponent") {
			if (P1ComboBox.getSelectedItem() == null) return null;
			return P2ComboBox.getSelectedItem().toString();
		} else if (opponentOptionsList.getSelectedItem().toString() == "Novice Computer") {
			return "Novice";
		} else {
			return "Experienced";
		}
	}
}