package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AllStudentsResultDialog extends JDialog {
	public int GUI_DISPLAY_LIMIT = 100;
	
	ArrayList<Student> studentList = new ArrayList<Student>();
	
	private Student    selectedStudent; //song currently selected in the GUI list
 //book currently selected in the GUI list
	
	DialogClient theClient;
	Frame fakeOwner;
	
	private Student studentBeingEdited; //song being edited in a dialog

	// Store the view that contains the components
	AllStudentsResultPanel 		view; //panel of GUI components for the main window
	
	ActionListener			theSearchButtonListener;
	ActionListener			theCancelButtonListener;
	ListSelectionListener	studentListSelectionListener;
	MouseListener			doubleClickSongListListener;
	KeyListener             keyListener;
	
	public AllStudentsResultDialog(Frame owner, DialogClient aClient, String title, boolean modal, ArrayList<Student> todaysStudents){
		super(owner,title,modal);
		fakeOwner = new Frame();
		
        studentList = todaysStudents;
        theClient = aClient;
        selectedStudent = null;
        
        add(view = new AllStudentsResultPanel());
        
        addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});
        
        theSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				search();
			}};
			
			theCancelButtonListener = new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					cancelButtonClicked();
				}};
			
			// Add a listener to allow selection of buddies from the list
				studentListSelectionListener = new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						selectStudent();
					}};

			// Add a listener to allow double click selections from the list for editing
					doubleClickSongListListener = new MouseAdapter() {
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 2) {
						JList theList = (JList) event.getSource();
						int index = theList.locationToIndex(event.getPoint());
						studentBeingEdited = (Student) theList.getModel().getElementAt(index);
						System.out.println("Double Click on: " + studentBeingEdited);
						
						Calendar now = Calendar.getInstance();
						String date = ""+ now.get(Calendar.DAY_OF_MONTH) + " "+ now.get(Calendar.MONTH) + " " + now.get(Calendar.YEAR);
						GameResultDialog dialog = new GameResultDialog(fakeOwner, theClient, "Game Result", true, date, selectedStudent);         
						dialog.setVisible(true);

					} 
						
				}};
				
			keyListener = new KeyListener() {

					@Override
					public void keyPressed(KeyEvent arg0) {
							
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						
					}

					@Override
					public void keyTyped(KeyEvent arg0) {

						int keyChar = arg0.getKeyChar();

				        if (keyChar == KeyEvent.VK_ENTER)  search();
					
					}};


			setSize(600,300);

			// Start off with everything updated properly to reflect the model state
			update();
		}
	private void enableListeners() {
		view.getSearchButton().addActionListener(theSearchButtonListener);
		view.getCancelButton().addActionListener(theCancelButtonListener);
		view.getStudentsList().addListSelectionListener(studentListSelectionListener);
		view.getStudentsList().addMouseListener(doubleClickSongListListener);
		view.getSearchBox().addKeyListener(keyListener);
	}

	// Disable all listeners
	private void disableListeners() {
		view.getSearchButton().removeActionListener(theSearchButtonListener);
		view.getCancelButton().removeActionListener(theCancelButtonListener);
		view.getStudentsList().removeListSelectionListener(studentListSelectionListener);
		view.getStudentsList().removeMouseListener(doubleClickSongListListener);
		view.getSearchBox().removeKeyListener(keyListener);
	}
	
	
	private void cancelButtonClicked(){
		
		//Inform the dialog client that the dialog finished
		
		if (theClient != null) theClient.dialogCancelled();

		//Make the dialog go away

		dispose();
	}
	
	
	
	
	private void search(){
		String searchPrototype = view.getSearchBox().getText().trim();
		String sqlQueryString = "select * from students where student_number in (select student_number from outcomes where student_number like '%" + searchPrototype + "%'" + " and updated is NULL);";
        //check some special cases
        if(searchPrototype.equals("*")) sqlQueryString = "select * from students where student_number in (select student_number from outcomes where updated is NULL and game_result is NULL)" + ";";
        else if(searchPrototype.equals("%")) sqlQueryString = "select * from students where student_number in (select student_number from outcomes where updated is NULL and game_result is NULL)" + ";";
        else if(searchPrototype.equals("")) sqlQueryString = "select * from students where student_number in (select student_number from outcomes where updated is NULL and game_result is NULL)" + ";";

	    try {
			Connection database = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
			Statement stat = database.createStatement();
			ResultSet rs = stat.executeQuery(sqlQueryString);
			
            ArrayList<Student> studentSearchResults = new ArrayList<Student>();

	        int count = 0;
	        while (rs.next() && count < GUI_DISPLAY_LIMIT){
	        	Student newGame = new Student(
	        			rs.getInt("student_number"),
	        			rs.getString("fName"), 
	        			rs.getString("lName")
	        			);
	        	studentSearchResults.add(newGame);
            count++;
	        }
	        rs.close(); //close the query result table
	        
	        Student songArray[] = new Student[1]; //just to establish array type
	        studentList = studentSearchResults;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Search clicked");
		update();
	}
	
	private void selectStudent() {
		selectedStudent = (Student)(view.getStudentsList().getSelectedValue());
		System.out.println("Student Selected: " + selectedStudent);
	
		update();
	}
	// This is called when the user selects a song from the lis


	// Update the remove button
	private void updateSearchButton() {
		view.getSearchButton().setEnabled(true);
	}

	// Update the list
	private void updateList() {
        Student studentArray[] = new Student[1]; //just to establish array type
	    view.getStudentsList().setListData(((Student []) studentList.toArray(studentArray)));

		if (selectedStudent != null)
			view.getStudentsList().setSelectedValue(selectedStudent, true);
	}

	// Update the components
	private void update() {
		disableListeners();
		updateList();
		updateSearchButton();
		enableListeners();
	}

	
	
}
