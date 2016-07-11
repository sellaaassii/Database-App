package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddNewGameDialog extends JDialog{
	private JLabel gameNameLabel;
	private JLabel consoleNameLabel;
	private JTextField gameNameTextField;
	private JComboBox consoleComboBox;
	private JButton addButton;
	private JButton cancelButton;
	private DateFormat formatForDate;
	private Date todaysDate;
	
	DialogClient theDialogClient;
	ArrayList<String> comboList;
	public JLabel getGameNameLabel(){
		return gameNameLabel;
	}

	public JLabel getConsoleNameLabel(){
		return consoleNameLabel;
	}
	
	public JTextField getGameNameTextField(){
		return gameNameTextField;
	}
	
	public JComboBox getConsoleComboBox(){
		return consoleComboBox;
	}
	
	public JButton getAddButton(){
		return addButton;
	}

	public JButton getCancelButton(){
		return cancelButton;
	}
	
	private void buildDialogWindow(){
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		gameNameLabel = new JLabel("GAME NAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(gameNameLabel, layoutConstraints);
		getContentPane().add(gameNameLabel);
		
		gameNameTextField = new JTextField();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(gameNameTextField, layoutConstraints);
		getContentPane().add(gameNameTextField);
		
		consoleNameLabel = new JLabel("CONSOLE NAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(consoleNameLabel, layoutConstraints);
		getContentPane().add(consoleNameLabel);
		
		consoleComboBox = new JComboBox();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(consoleComboBox, layoutConstraints);
		getContentPane().add(consoleComboBox);
		
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
	
	public AddNewGameDialog(Frame owner, DialogClient aClient, String title, boolean modal){
		super(owner,title,modal);
		theDialogClient = aClient;
		
		buildDialogWindow();
		comboList = new ArrayList<String>();
		consoleComboBox.removeAllItems();
		try {
			Connection theConnection = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
			PreparedStatement stmt = theConnection.prepareStatement("select console_name from consoles;");
			ResultSet rs = stmt.executeQuery();
			String name;
			while(rs.next()){
				 name = rs.getString("console_name");
					if(!name.equals("")){
						comboList.add(name);
						consoleComboBox.addItem(name);
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		formatForDate = new SimpleDateFormat("dd/MM/yyyy");
		todaysDate = new Date(); 
		formatForDate.format(todaysDate);
		try {
			if((gameNameTextField.getText() .equals(""))||(((String) consoleComboBox.getSelectedItem()).equals(""))){
				System.out.println("Empty Name or Console");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db");
				String sqlQueryString = "insert into games(date_added, consoleID, gameName) values(?, (select consoleID from consoles where console_name = ?), ?);";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, formatForDate.format(todaysDate));
				rs.setString(2, (String) consoleComboBox.getSelectedItem());
				rs.setString(3, gameNameTextField.getText());
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
