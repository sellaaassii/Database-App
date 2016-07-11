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

public class ConsoleDetailsDialog extends JDialog{
	private JLabel consoleNameLabel;
	private JTextField consoleNameTextField;
	private JButton updateButton;
	private JButton cancelButton;
	private JButton deleteButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	private Console theConsole;
	DialogClient theDialogClient;
	
	public ConsoleDetailsDialog(Frame owner, DialogClient aClient, String title, boolean modal, Console aConsole){
		super(owner,title,modal);
		
		theDialogClient = aClient;
		theConsole = aConsole;

		// Put all the components onto the window and given them initial values
		buildDialogWindow(theConsole);
		
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
	
	private void buildDialogWindow(Console aConsole) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        consoleNameLabel = new JLabel("Name");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(consoleNameLabel, lc);
        getContentPane().add(consoleNameLabel);

   		
   		// Add the name field
        consoleNameTextField = new JTextField(aConsole.getConsoleName());
        consoleNameTextField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(consoleNameTextField, lc);
   		getContentPane().add(consoleNameTextField);

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
			if(consoleNameTextField.getText() .equals("")){
				System.out.println("Empty Console Name");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				//Statement stat = database.createStatement();
				String sqlQueryString = "update consoles set console_name = ? where consoleID = ?;";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, consoleNameTextField.getText());
				rs.setInt(2, theConsole.getConsoleID());
				rs.addBatch();
				rs.executeBatch();
				theConsole.setName(consoleNameTextField.getText());
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
				Connection database = DriverManager.getConnection("jdbc:sqlite:db_3005fakebooks");
				if(!(consoleNameTextField.getText().equals(""))){
				sqlQueryString = "delete from consoles where console_name = ?;";
			 	PreparedStatement rs = database.prepareStatement(sqlQueryString);
			 	rs.setString(1, consoleNameTextField.getText());
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
	
