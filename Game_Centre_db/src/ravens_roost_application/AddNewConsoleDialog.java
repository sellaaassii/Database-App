package ravens_roost_application;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddNewConsoleDialog extends JDialog{
	private JLabel consoleNameLabel;
	private JTextField consoleNameTextField;
	private JButton addButton;
	private JButton cancelButton;
	DialogClient theDialogClient;
	
	public JTextField getConsoleNameTextField(){
		return consoleNameTextField;
	}
	
	public JButton getAddButton(){
		return addButton;
	}

	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public JLabel getConsoleNameLabel(){
		return consoleNameLabel;
	}
	
	private void buildDialogWindow(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		consoleNameLabel = new JLabel("CONSOLE NAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(consoleNameLabel, layoutConstraints);
		getContentPane().add(consoleNameLabel);
		
		consoleNameTextField = new JTextField();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(consoleNameTextField, layoutConstraints);
		getContentPane().add(consoleNameTextField);
		
		addButton = new JButton("ADD");
		layoutConstraints.gridx = 0; 
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 0.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(addButton, layoutConstraints);
        getContentPane().add(addButton);
		
        cancelButton = new JButton("CANCEL");
		layoutConstraints.gridx = 1; 
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 4.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(cancelButton, layoutConstraints);
        getContentPane().add(cancelButton);
	}
	
	public AddNewConsoleDialog(Frame owner, DialogClient aClient, String title, boolean modal){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		
		buildDialogWindow();

		// Add listeners for the Ok and Cancel buttons as well as window closing
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				addButtonButtonClicked();
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
	
	private void addButtonButtonClicked(){
		try {
			if(consoleNameTextField.getText() .equals("")){
				System.out.println("Empty Name");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				String sqlQueryString = "insert into consoles(console_name) values(?);";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, consoleNameTextField.getText());
				rs.addBatch();
				rs.executeBatch();
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

}
