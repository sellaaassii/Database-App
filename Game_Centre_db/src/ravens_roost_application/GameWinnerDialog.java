package ravens_roost_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GameWinnerDialog extends JDialog{

	private JLabel nameLabel;
	private JLabel studentNumberLabel;
	private JButton cancelButton;
	
	Student theWinner;

	DialogClient theDialogClient;

	
	public JLabel getNameLabel(){
		return nameLabel;
	}
	
	public JLabel getStudentNumberLabel(){
		return studentNumberLabel;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	private void buildDialogWindow(){
			
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		nameLabel = new JLabel("Name");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(nameLabel, layoutConstraints);
		getContentPane().add(nameLabel);
		
		nameLabel = new JLabel("" + theWinner.getStudentFirstName() + " " + theWinner.getStudentLastName() + "");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(nameLabel, layoutConstraints);
		getContentPane().add(nameLabel);
		
		studentNumberLabel = new JLabel("Student Number");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(studentNumberLabel, layoutConstraints);
		getContentPane().add(studentNumberLabel);
		
		studentNumberLabel = new JLabel("" + theWinner.getStudentId());
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(studentNumberLabel, layoutConstraints);
		getContentPane().add(studentNumberLabel);
		
		cancelButton = new JButton("Back");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 5;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(cancelButton, layoutConstraints);
		getContentPane().add(cancelButton);
	}
	
	public GameWinnerDialog(Frame owner, DialogClient aClient, String title, boolean modal, Student aWinner){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		
		theWinner = aWinner;
		
		buildDialogWindow();
		
		// Add listeners for the Ok and Cancel buttons as well as window closing
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

}


