package ravens_roost_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class SelectingSpecificGameWinnerDialog extends JDialog{
	private JLabel selectGameLabel;
	private JComboBox selectGameComboBox;
	private JButton cancelButton;
	private JButton enterGameButton;

	DialogClient theDialogClient;
	Student theWinner = new Student(0001, "Nobody", "Won");
	
	public JComboBox getSelectGameComboBox(){
		return selectGameComboBox;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public JButton getEnterGameButton(){
		return enterGameButton;
	}
	
	public JLabel getSelectGameLabel(){
		return selectGameLabel;
	}
	
	private void buildDialogWindow(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		selectGameLabel = new JLabel("SELECT GAME:");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(selectGameLabel, layoutConstraints);
		getContentPane().add(selectGameLabel);
		
		selectGameComboBox = new JComboBox();
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(selectGameComboBox, layoutConstraints);
		getContentPane().add(selectGameComboBox);
		
		enterGameButton = new JButton("ENTER");
		layoutConstraints.gridx = 0; 
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 0.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(enterGameButton, layoutConstraints);
        getContentPane().add(enterGameButton);
        
        cancelButton = new JButton("BACK");
		layoutConstraints.gridx = 2; 
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 1; 
		layoutConstraints.gridheight = 1;
		layoutConstraints.weightx = 0.0; 
		layoutConstraints.weighty = 0.0;
        layout.setConstraints(cancelButton, layoutConstraints);
        getContentPane().add(cancelButton);
        
        
        
	}
	
	public SelectingSpecificGameWinnerDialog(Frame owner, DialogClient aClient, String title, boolean modal){
		super(owner,title,modal);
		theDialogClient = aClient;
		
		buildDialogWindow();
		selectGameComboBox.removeAllItems();
		try {
			String sqlQueryString = "select gameName from games;";
			Connection 	database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
			Statement stmt = database.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			String name;
			while(rs.next()){
				name = rs.getString("gameName");
					if(!name.equals("")){
						selectGameComboBox.addItem(name);
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		enterGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				enterGameButtonClicked();
				GameWinnerDialog winner = new GameWinnerDialog(owner, theDialogClient, "WINNER", modal, theWinner);
				winner.setVisible(true);
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
	
	private void enterGameButtonClicked(){
		try {
			if(((String) selectGameComboBox.getSelectedItem()).equals("")){
				System.out.println("Empty field");
			} 
			else{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				String sqlQueryString = "select fName, lName, student_number from students where student_number in (select student_number from outcomes where game_result = ? and gameName = ? group by student_number order by count(*) desc limit 1);";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, "WIN");
				rs.setString(2, (String) selectGameComboBox.getSelectedItem());
				ResultSet s = rs.executeQuery();
				while (s.next()) {
		            Student winnerStudent = new Student(s.getInt("student_number"), s.getString("fName"), s.getString("lName"));
		            theWinner = winnerStudent;
		        }
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


