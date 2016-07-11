package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

public class GamesListDialog extends JDialog{
	private JList gamesList;
	private JTextField searchGameTextField;
	private JButton searchGamesButton;
	private JButton addNewGameButton;
	private JButton cancelButton;
	
	DialogClient theDialogClient;
	Connection theConnection;
	Statement theStatement;
	Frame fakeOwnerFrame;
	
	public int DISPLAY_LIMIT = 100;
	private Game gameSelected;
	private Game gameBeingEdited;
	
	private Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	ArrayList<Game> gameArrayList = new ArrayList<Game>();

	public JList getGamesList(){
		return gamesList;
	}
	
	public JButton getSearchButton(){
		return searchGamesButton;
	}
	
	public JButton getAddNewGameButton(){
		return addNewGameButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public JTextField getSearchGameTextField(){
		return searchGameTextField;
	}
	
	private void buildDialogWindow(){
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		
		searchGameTextField = new JTextField("");
		searchGameTextField.setFont(UIFont);

		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchGameTextField, layoutConstraints);
		add(searchGameTextField);
		
		searchGamesButton = new JButton("Search");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchGamesButton, layoutConstraints);
		add(searchGamesButton);
		
		addNewGameButton = new JButton("Add Game");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(addNewGameButton, layoutConstraints);
		getContentPane().add(addNewGameButton);
		
		cancelButton = new JButton("Cancel");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(cancelButton, layoutConstraints);
		getContentPane().add(cancelButton);
		
		gamesList = new JList();
		gamesList.setFont(UIFont);
		gamesList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( gamesList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 4;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 2.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);
	}
	
	public GamesListDialog(Frame owner, DialogClient aClient, String title, boolean modal, 	ArrayList<Game> aGameList){

		super(owner, title, modal);
		
		buildDialogWindow();
		
		theDialogClient = aClient;
		gameSelected = null;
		fakeOwnerFrame = new Frame();
		gameArrayList = aGameList;
		
		try {
			theConnection = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchGamesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				searchGamesButtonClicked();
			}});
		
		addNewGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				addNewGameButtonClicked();
			}});


		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});

		gamesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					JList theList = (JList) event.getSource();
					int index = theList.locationToIndex(event.getPoint());
					gameBeingEdited = (Game) theList.getModel().getElementAt(index);
					System.out.println("Double Click on: " + gameBeingEdited);
					
					
					GameDetailsDialog dialog = new GameDetailsDialog(fakeOwnerFrame, theDialogClient, "Game Details Dialog", true, gameBeingEdited);         
					dialog.setVisible(true);

				} 
				
					
			}});
			
		searchGameTextField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
						
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {

					int keyChar = arg0.getKeyChar();

			        if (keyChar == KeyEvent.VK_ENTER)  searchGamesButtonClicked();
				
				}});
		updateList();
		updateSearchButton();
		setSize(400, 250);
		
	}
	
	private void searchGamesButtonClicked(){
		String searchPrototype = searchGameTextField.getText().trim();
		String sqlQueryString;
		
		if(gameSelected == null){
			sqlQueryString = "select * from games where gameName like '%" + searchPrototype + "%'" + ";";
		}
		else{
			 sqlQueryString = "select * from games;";
		}
        //check some special cases
        if(searchPrototype.equals("*")) sqlQueryString = "select * from games" + ";";
        else if(searchPrototype.equals("%")) sqlQueryString = "select * from games" + ";";
        else if(searchPrototype.equals("")) sqlQueryString = "select * from games" + ";";

	    try {
	    	theStatement = theConnection.createStatement();
			ResultSet rs = theStatement.executeQuery(sqlQueryString);
			
            ArrayList<Game> gameSearchResults = new ArrayList<Game>();

	        int count = 0;
	        while (rs.next() && count < DISPLAY_LIMIT){
	        	Game nGame = new Game(
	        			rs.getString("gameName"),
	        			rs.getInt("consoleID")
	        			);
	        	
	        	gameSearchResults.add(nGame);
            count++;
	        }
	        
	        rs.close(); //close the query result table
	        Game gameArray[] = new Game[1]; //just to establish array type
	        gameArrayList = gameSearchResults;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		System.out.println("Search clicked");
		updateSearchButton();
		updateList();
	}

	private void addNewGameButtonClicked(){
		AddNewGameDialog dialog = new AddNewGameDialog(fakeOwnerFrame, theDialogClient, "New Game", true);
		dialog.setVisible(true);
	}
	
	private void updateList() {
	    Game gameArray[] = new Game[1]; //just to establish array type
	    gamesList.setListData(((Game []) gameArrayList.toArray(gameArray)));

		if (gameSelected != null)
			gamesList.setSelectedValue(gameSelected, true);
	}
	
	private void updateSearchButton() {
		searchGamesButton.setEnabled(true);
	}
	
	private void cancelButtonClicked(){
		if (theDialogClient != null){
			theDialogClient.dialogCancelled();
		}
		dispose();
	}
		
}

	
