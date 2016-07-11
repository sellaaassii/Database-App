package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;

import javax.swing.*;

public class ListPanel extends JPanel {
	private JLabel dateLabel;
	private JLabel welcomeLabel;
	private JButton prizesButton;
	private JButton studentAddLogin;
	private JButton studentWinsOrLosesGame;
	private JButton checkGamesList;
	private JButton consoleListButton;
	private DateFormat formatForDate;
	private Date todaysDate;
	
	private Font welcomeFont = new Font("Courier New", Font.BOLD, 20);
	private Font dateFont = new Font("Courier New", Font.BOLD, 16);
	
	public JButton getPrizeButton() { 
		return prizesButton; 
	}
	
	public JButton getAddButton() { 
		return studentAddLogin; 
	}
	
	public JButton getWinButton() { 
		return studentWinsOrLosesGame; 
	}
	
	public JButton getGamesButton() { 
		return checkGamesList; 
	}
	
	public JButton getConsolesButton() { 
		return consoleListButton; 
	}
	
	public JLabel getDate() { 
		return dateLabel; 
	}

	public ListPanel(){
		super();
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setFont(welcomeFont);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		welcomeLabel.setFont(welcomeFont);
		layout.setConstraints(welcomeLabel, layoutConstraints);
		add(welcomeLabel);
		
		formatForDate = new SimpleDateFormat("dd/MM/yyyy");
		todaysDate = new Date(); 
		dateLabel = new JLabel(formatForDate.format(todaysDate));
		dateLabel.setFont(dateFont); 
		layoutConstraints.gridx = 6;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHEAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(dateLabel, layoutConstraints);
		add(dateLabel);
		
		studentAddLogin = new JButton("Add new Student");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(studentAddLogin, layoutConstraints);
		add(studentAddLogin);
		
		studentWinsOrLosesGame = new JButton("Enter outcome of Student Game");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 4;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(studentWinsOrLosesGame, layoutConstraints);
		add(studentWinsOrLosesGame);
		
		checkGamesList = new JButton("Open Games List");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 6;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(checkGamesList, layoutConstraints);
		add(checkGamesList);
		
		consoleListButton = new JButton("Open Consoles List");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 9;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(consoleListButton, layoutConstraints);
		add(consoleListButton);
		
		prizesButton = new JButton("Prize Winners");
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 12;
		layoutConstraints.gridwidth = 5;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(prizesButton, layoutConstraints);
		add(prizesButton);
	}
	
}