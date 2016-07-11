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

public class GUI extends JFrame implements DialogClient{
	public int GUI_DISPLAY_LIMIT = 100;
	
	Connection databaseConnection;
	Statement stat;
	
	DialogClient theClient;
	
	ArrayList<Student> studentList = new ArrayList<Student>();
	ArrayList<Game> gameList = new ArrayList<Game>();
	ArrayList<Console> consoleList = new ArrayList<Console>();

	
	ListPanel mainView;
	
	AddNewStudentPlayingDialog addStudentView;
	GamesListDialog listOfGamesView;
	ConsoleViewDialog listOfConsolesView;
	AllStudentsResultDialog outcomeOfStudentGameView;
	PrizeWinnersDialog thePrizeWinnersButtonsView;
	

	JFrame popupFrame;
	GUI thisframe;
	
	
	ActionListener			theAddStudentButtonListener;
	ActionListener			theGamesListButtonListener;
	ActionListener			theOutcomeOfStudentsGameButtonListener;
	ActionListener			thePrizeWinnersButtonListener;
	ActionListener			theConsolesListButtonListener;
	
	
	public GUI(String title, Connection aDB, Statement aStatement, ArrayList<Student> todaysStudents, ArrayList<Game> initialGames, ArrayList<Console> initialConsoles){
		super(title);
		
		thisframe = this;
		studentList = todaysStudents;
		gameList = initialGames;
		consoleList = initialConsoles;
		databaseConnection = aDB;
		
		addWindowListener(
				new WindowAdapter() {
	 				public void windowClosing(WindowEvent e) {
	 					try {
	 						System.out.println("Closing Database Connection");
							databaseConnection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
					}
				}
			);
		
		add(mainView = new ListPanel());
		addStudentView = new AddNewStudentPlayingDialog(thisframe, thisframe, "Students", true);
		theAddStudentButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addStudentView.setVisible(true);
		}};
		
		listOfGamesView = new GamesListDialog(thisframe, thisframe, "List", true, gameList);
		theGamesListButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				listOfGamesView.setVisible(true);
		}};
				
		outcomeOfStudentGameView = new AllStudentsResultDialog(thisframe, thisframe, "Outcome", true, studentList);
		theOutcomeOfStudentsGameButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				outcomeOfStudentGameView.setVisible(true);
		}};
		
		listOfConsolesView = new ConsoleViewDialog(thisframe, thisframe, "Students", true, consoleList);
		theConsolesListButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				listOfConsolesView.setVisible(true);
		}};
		
		thePrizeWinnersButtonsView = new PrizeWinnersDialog(thisframe, thisframe, "Prizes", true);
		thePrizeWinnersButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				thePrizeWinnersButtonsView.setVisible(true);
		}};
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,300);
		// Start off with everything updated properly to reflect the model state
		update();
	}
	
	private void updateButtons() {
		mainView.getAddButton().setEnabled(true);
		mainView.getWinButton().setEnabled(true);
		mainView.getGamesButton().setEnabled(true);
		mainView.getConsolesButton().setEnabled(true);
		mainView.getPrizeButton().setEnabled(true);
	}
	
	private void enableListeners() {
		mainView.getAddButton().addActionListener(theAddStudentButtonListener);
		mainView.getWinButton().addActionListener(theOutcomeOfStudentsGameButtonListener);
		mainView.getGamesButton().addActionListener(theGamesListButtonListener);
		mainView.getConsolesButton().addActionListener(theConsolesListButtonListener);
		mainView.getPrizeButton().addActionListener(thePrizeWinnersButtonListener);


	}
	
	private void disableListeners() {
		mainView.getAddButton().removeActionListener(theAddStudentButtonListener);
		mainView.getWinButton().removeActionListener(theOutcomeOfStudentsGameButtonListener);
		mainView.getGamesButton().removeActionListener(theGamesListButtonListener);
		mainView.getConsolesButton().removeActionListener(theConsolesListButtonListener);
		mainView.getPrizeButton().removeActionListener(thePrizeWinnersButtonListener);

	}
	
	private void update() {
		disableListeners();
		updateButtons();
		enableListeners();
	}

	@Override
	public void dialogFinished(operation anOperation) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void dialogCancelled() {
		// TODO Auto-generated method stub
		update();
	}
	
	
}
