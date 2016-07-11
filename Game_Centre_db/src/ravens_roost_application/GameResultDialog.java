package ravens_roost_application;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameResultDialog extends JDialog{
	private Student theStudent;
	
	DialogClient theDialogClient;
	
	String result;
	String theDate;

	// These are the components of the dialog box
	private JLabel					studentNameLabel;
	private JLabel 					studentNumberLabel;
	private JTextField				resultField;
	private JButton					winButton;
	private JButton					cancelButton;
	private JButton					loseButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	public GameResultDialog(Frame owner, DialogClient aClient, String title, boolean modal, String date, Student newStudent){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		theStudent = newStudent;
		theDate = date;
		System.out.println(theDate);

		// Put all the components onto the window and given them initial values
		buildDialogWindow();

		// Add listeners for the Ok and Cancel buttons as well as window closing
		winButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				winButtonClicked();
			}});
		
		loseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				loseButtonClicked();
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
	
	private void buildDialogWindow() {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        studentNameLabel = new JLabel("Name: ");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(studentNameLabel, lc);
        getContentPane().add(studentNameLabel);
        
        studentNameLabel = new JLabel("" + theStudent.getStudentFirstName() + " " + theStudent.getStudentLastName());
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(studentNameLabel, lc);
        getContentPane().add(studentNameLabel);
        
        studentNumberLabel = new JLabel("Student Number: ");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(studentNumberLabel, lc);
        getContentPane().add(studentNumberLabel);
        
        studentNumberLabel = new JLabel("" + theStudent.getStudentId());
        lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(studentNumberLabel, lc);
        getContentPane().add(studentNumberLabel);
        
        winButton = new JButton("WIN");
        lc.gridx = 0; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(winButton, lc);
        getContentPane().add(winButton);
        
        loseButton = new JButton("LOSE");
        lc.gridx = 1; lc.gridy = 3;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(loseButton, lc);
   		getContentPane().add(loseButton);
   		
   	    cancelButton = new JButton("CANCEL");
   	    lc.gridx = 2; lc.gridy = 3;
   	    lc.gridwidth = 3; lc.gridheight = 1;
   	    lc.fill = GridBagConstraints.BOTH;
   	    lc.weightx = 1.0; lc.weighty = 0.0;
   	    layout.setConstraints(cancelButton, lc);
		getContentPane().add(cancelButton);
	}

	private void winButtonClicked(){
		try{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				String sqlQueryString = "update outcomes set game_result = ? where day = ? and student_number = ? and game_result is NULL and updated is NULL;";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, "WIN");
				rs.setString(2, theDate);
				rs.setInt(3, theStudent.getStudentId()); 
				rs.addBatch();
				rs.executeBatch();
				rs.close();
				
				sqlQueryString = "update outcomes set updated = ? where day = ? and student_number = ? and game_result = ? and updated is NULL;";
				rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, "UPDATED");
				rs.setString(2, theDate);
				rs.setInt(3, theStudent.getStudentId()); 
				rs.setString(4, "WIN");
				rs.addBatch();
				rs.executeBatch();
				rs.close();
				database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATE);
		
		//Make the dialog go away
		
		dispose();
	}
	
	private void loseButtonClicked(){
		try{
				Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
				String sqlQueryString = "update outcomes set game_result = ? where day = ? and student_number = ? and game_result is NULL and updated is NULL;";
				PreparedStatement rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, "LOSE");
				rs.setString(2, theDate);
				rs.setInt(3, theStudent.getStudentId());
				rs.addBatch();
				rs.executeBatch();
				rs.close();
				
				sqlQueryString = "update outcomes set updated = ? where day = ? and student_number = ? and game_result = ? and updated is NULL;";
				rs = database.prepareStatement(sqlQueryString);
				rs.setString(1, "UPDATED");
				rs.setString(2, theDate);
				rs.setInt(3, theStudent.getStudentId()); 
				rs.setString(4, "LOSE");
				rs.addBatch();
				rs.executeBatch();
				rs.close();
				database.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATE);
		
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
