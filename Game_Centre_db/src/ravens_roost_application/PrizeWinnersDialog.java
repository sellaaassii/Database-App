package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PrizeWinnersDialog extends JDialog{
	private JButton mostAppearancesPrizeButton;
	private JButton winnersOfOtherGamesButton;
	private JButton cancelButton;
	
	DialogClient theDialogClient;

	public JButton getAppearancesPrizeButton() { 
		return mostAppearancesPrizeButton; 
	}
	
	public JButton getOtherGamesWinnersButton() { 
		return winnersOfOtherGamesButton; 
	}
	
	private void buildDialogWindow(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		mostAppearancesPrizeButton = new JButton("Most Appearances");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(mostAppearancesPrizeButton, layoutConstraints);
		getContentPane().add(mostAppearancesPrizeButton);
		
		winnersOfOtherGamesButton = new JButton("Winners of Other Games");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(winnersOfOtherGamesButton, layoutConstraints);
		getContentPane().add(winnersOfOtherGamesButton);
		
		cancelButton = new JButton("Cancel");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 4;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(cancelButton, layoutConstraints);
		getContentPane().add(cancelButton);
	}
		
	public PrizeWinnersDialog(Frame owner, DialogClient aClient, String title, boolean modal){
		super(owner,title,modal);
		
		theDialogClient = aClient;
		buildDialogWindow();

		mostAppearancesPrizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				MostAppearancesPrizeDialog appearancesViewDialog = new MostAppearancesPrizeDialog(owner, aClient, "WINNER", modal);
				appearancesViewDialog.setVisible(true);
			}});

		winnersOfOtherGamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				winnersOfOtherGamesButtonClicked();
				SelectingSpecificGameWinnerDialog selection = new SelectingSpecificGameWinnerDialog(owner, aClient, "Select Game", modal);
				selection.setVisible(true);
			}});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});

		setSize(400, 250);
	}
	
	private void cancelButtonClicked(){
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogCancelled();
		//Make the dialog go away
		dispose();
	}
	
	
	private void winnersOfOtherGamesButtonClicked(){
		
	}
	

}
