package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;


public class AddNewStudentPlayingDialog extends JDialog{
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel IDLabel;
	private JLabel gameLabel;
	private JTextField firstnameTextField;
	private JTextField lastNameTextField;
	private JTextField idTextField;
	private JComboBox gameComboBox;
	private JButton	  updateButton;
	private JButton	  cancelButton;
	
	
	ArrayList<String> comboList;
	DialogClient theDialogClient;
	private Game selectedGame = null;
	
	public JTextField getFirstNameTextField(){
		return firstnameTextField;
	}
	
	public JTextField getLastNameTextField(){
		return lastNameTextField;
	}
	
	public JComboBox getGameComboBox(){
		return gameComboBox;
	}
	
	public JTextField getIDTestField(){
		return idTextField;
	}
	
	public JButton getUpdateButton(){
		return updateButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	private void buildDialogWindow(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		getContentPane().setLayout(layout);
		 
		firstNameLabel = new JLabel("STUDENT FIRST NAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(firstNameLabel, layoutConstraints);
		getContentPane().add(firstNameLabel);
		
		firstnameTextField = new JTextField();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(firstnameTextField, layoutConstraints);
		getContentPane().add(firstnameTextField);

		lastNameLabel = new JLabel("STUDENT LAST NAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(lastNameLabel, layoutConstraints);
		getContentPane().add(lastNameLabel);
		
		lastNameTextField = new JTextField();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(lastNameTextField, layoutConstraints);
		getContentPane().add(lastNameTextField);
		
		
		IDLabel = new JLabel("STUDENT NUMBER:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(IDLabel, layoutConstraints);
		getContentPane().add(IDLabel);
		
		idTextField = new JTextField();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(idTextField, layoutConstraints);
		getContentPane().add(idTextField);
		
		gameLabel = new JLabel("GAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(gameLabel, layoutConstraints);
		getContentPane().add(gameLabel);
		
		gameComboBox = new JComboBox();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(gameComboBox, layoutConstraints);
		getContentPane().add(gameComboBox);
		
		updateButton = new JButton("UPDATE");
		layoutConstraints.gridx = 0; 
		layoutConstraints.gridy = 7;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 0.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(updateButton, layoutConstraints);
        getContentPane().add(updateButton);
		
        cancelButton = new JButton("CANCEL");
		layoutConstraints.gridx = 1; 
		layoutConstraints.gridy = 7;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 4.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(cancelButton, layoutConstraints);
        getContentPane().add(cancelButton);
	}
	
	public AddNewStudentPlayingDialog(Frame owner, DialogClient aClient, String title, boolean modal){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		//theSong = aSong;

		// Put all the components onto the window and given them initial values
		buildDialogWindow();
		String sqlQueryString = "select gameName from games;";
		Connection database;
		Statement stmt = null;
		try {
			database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
			stmt = database.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     
		comboList = new ArrayList<String>();
		gameComboBox.removeAllItems();
		try {
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			String name;
			while(rs.next()){
				name = rs.getString("gameName");
					if(!name.equals("")){
						comboList.add(name);
						gameComboBox.addItem(name);
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Add listeners for the Ok and Cancel buttons as well as window closing
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				updateButtonClicked();
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
	
	private void updateButtonClicked(){
		try {
			if((lastNameTextField.equals(""))||firstnameTextField.getText() .equals("") ||idTextField.getText().equals("")||((String) gameComboBox.getSelectedItem()).equals("")){
				System.out.println("Empty field");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				String sqlQueryString = "insert or ignore into students(student_number, number_of_appearances, fName, lName) values(?, ?, ?, ?);";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setInt(1, Integer.parseInt(idTextField.getText()));
				rs.setInt(2, 0);
				rs.setString(3, firstnameTextField.getText());
				rs.setString(4, lastNameTextField.getText());
				rs.addBatch();
				rs.executeBatch();
				rs.close();
				
				sqlQueryString = "update students set number_of_appearances = (number_of_appearances + 1) where student_number = ?;";
				rs = database.prepareStatement(sqlQueryString);
				rs.setInt(1, Integer.parseInt(idTextField.getText()));
				rs.addBatch();
				rs.executeBatch();
				rs.close();
	
				Calendar now = Calendar.getInstance();
				String date = ""+ now.get(Calendar.DAY_OF_MONTH) + " "+ now.get(Calendar.MONTH) + " " + now.get(Calendar.YEAR);
				sqlQueryString = "insert into outcomes(student_number, gameName, consoleID, day, game_result, updated) values(?, ?, (select consoleID from games where gameName = ?), ?, NULL, NULL);";
				rs = database.prepareStatement(sqlQueryString);
				rs.setInt(1, Integer.parseInt(idTextField.getText()));
				rs.setString(2, (String) gameComboBox.getSelectedItem());
				rs.setString(3, (String) gameComboBox.getSelectedItem());
				rs.setString(4, date);
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
