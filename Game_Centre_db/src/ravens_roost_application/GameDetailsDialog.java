package ravens_roost_application;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDetailsDialog extends JDialog{
	private JLabel gameNameLabel;
	private JTextField gameNameTextField;
	private JButton updateButton;
	private JButton cancelButton;
	private JButton deleteButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	private Game theGame;
	DialogClient theDialogClient;
	
	public GameDetailsDialog(Frame owner, DialogClient aClient, String title, boolean modal, Game aGame){
		super(owner,title,modal);
		
		theDialogClient = aClient;
		theGame = aGame;

		// Put all the components onto the window and given them initial values
		buildDialogWindow(theGame);
		
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				updateButtonClicked();
			}});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				deleteButtonClicked();
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
	
	private void buildDialogWindow(Game aGame) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        gameNameLabel = new JLabel("Name");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(gameNameLabel, lc);
        getContentPane().add(gameNameLabel);

   		
   		// Add the name field
        gameNameTextField = new JTextField(aGame.getGameName());
        gameNameTextField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(gameNameTextField, lc);
   		getContentPane().add(gameNameTextField);

		// Add the Update button
		updateButton = new JButton("UPDATE");

        lc.gridx = 1; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(updateButton, lc);
   		getContentPane().add(updateButton);
        
		// Add the Delete button
		deleteButton = new JButton("DELETE");

        lc.gridx = 2; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(deleteButton, lc);
   		getContentPane().add(deleteButton);

   		// Add the Cancel button
		cancelButton = new JButton("CANCEL");
        
        lc.gridx = 3; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(cancelButton, lc);
   		getContentPane().add(cancelButton);
	}

	private void updateButtonClicked(){
		try {
			if((gameNameTextField.getText() .equals(""))){
				System.out.println("Empty GAmee Name");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				//Statement stat = database.createStatement();
				String sqlQueryString = "update games set gameName = ? where consoleID = ?;";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, gameNameTextField.getText());
				rs.setInt(2, theGame.getGameConsoleID());
				rs.addBatch();
				rs.executeBatch();
				theGame.setGameName(gameNameTextField.getText());
				rs.close();
				database.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Inform the dialog client that the dialog finished
	
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATE);
	
		//Make the dialog go away
	
		dispose();
	}

		private void deleteButtonClicked(){
			String sqlQueryString;
			try {
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				if(!(gameNameTextField.getText().equals(""))){
					sqlQueryString = "delete from games where gameName = ? and consoleID = ?;";
					PreparedStatement rs = database.prepareStatement(sqlQueryString);
					rs.setString(1, gameNameTextField.getText());
					rs.setInt(2, theGame.getGameConsoleID());
					rs.addBatch();
					rs.executeBatch();
				}
				else{
					System.out.println("empty Name");
				}
				database.close();
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//Inform the dialog client that the dialog finished
	
			if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.DELETE);
	
			//Make the dialog go away
	
			dispose();

		}

	private void cancelButtonClicked(){
	
		//Inform the dialog client that the dialog finished
	
		if (theDialogClient != null) theDialogClient.dialogCancelled();

		//Make the dialog go away

		dispose();
	}
	
}
