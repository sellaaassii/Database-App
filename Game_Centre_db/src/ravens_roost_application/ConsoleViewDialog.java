package ravens_roost_application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;


public class ConsoleViewDialog extends JDialog{
	private JList consoleList;
	private JTextField searchConsoleTextField;
	private JButton searchConsolesButton;
	private JButton addNewConsoleButton;
	private JButton cancelButton;
	
	DialogClient theDialogClient;
	Connection theConnection;
	Statement theStatement;
	Frame fakeOwnerFrame;
	int GUI_DISPLAY_LIMIT = 100;
	
	
	private Console selectedConsole;
	private Console consoleBeingEdited;
	
	private Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	ArrayList<Console> consoleArrayList = new ArrayList<Console>();
	
	public JList getConsoleList(){
		return consoleList;
	}
	
	public JTextField getSearchConsoleTextField(){
		return searchConsoleTextField;
	}
	
	public JButton getSearchConsolesButton(){
		return searchConsolesButton;
	}
	
	public JButton getAddNewConsoleButton(){
		return addNewConsoleButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public void buildDialogWindow(){
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		searchConsoleTextField = new JTextField("");
		searchConsoleTextField.setFont(UIFont);

		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchConsoleTextField, layoutConstraints);
		getContentPane().add(searchConsoleTextField);
		
		searchConsolesButton = new JButton("Search");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchConsolesButton, layoutConstraints);
		getContentPane().add(searchConsolesButton);
		
		addNewConsoleButton = new JButton("Add Console");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(addNewConsoleButton, layoutConstraints);
		getContentPane().add(addNewConsoleButton);
		
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
		
		consoleList = new JList();
		consoleList.setFont(UIFont);
		consoleList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( consoleList,
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
		getContentPane().add(scrollPane);
	}
	
	public ConsoleViewDialog(Frame owner, DialogClient aClient, String title, boolean modal, ArrayList<Console> aConsoleList){
		super(owner, title, modal);
		
		buildDialogWindow();
		
		theDialogClient = aClient;
		selectedConsole = null;
		fakeOwnerFrame = new Frame();
		consoleArrayList = aConsoleList;
		
		try {
			theConnection = DriverManager.getConnection("jdbc:sqlite:ravens_roost_db.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		searchConsolesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				searchConsolesButtonClicked();
			}});
		
		addNewConsoleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				addNewConsoleButtonClicked();
			}});


		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});
		
		consoleList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					JList theList = (JList) event.getSource();
					int index = theList.locationToIndex(event.getPoint());
					consoleBeingEdited = (Console) theList.getModel().getElementAt(index);
					System.out.println("Double Click on: " + consoleBeingEdited);
					
					
					ConsoleDetailsDialog dialog = new ConsoleDetailsDialog(fakeOwnerFrame, theDialogClient, "Console Details Dialog", true, consoleBeingEdited);         
					dialog.setVisible(true);

				} 
				
					
			}});
			
			searchConsoleTextField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
						
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {

					int keyChar = arg0.getKeyChar();

			        if (keyChar == KeyEvent.VK_ENTER)  searchConsolesButtonClicked();
				
				}});
			updateList();
			updateSearchButton();
			setSize(400, 250);
		
	}
		
	private void searchConsolesButtonClicked(){
		String searchPrototype = searchConsoleTextField.getText().trim();
		String sqlQueryString;
		
		if(selectedConsole == null){
			sqlQueryString = "select * from consoles where console_name like '%" + searchPrototype + "%'" + ";";
		}
		else{
			 sqlQueryString = "select * from consoles;";
		}
        //check some special cases
        if(searchPrototype.equals("*")) sqlQueryString = "select * from consoles" + ";";
        else if(searchPrototype.equals("%")) sqlQueryString = "select * from consoles" + ";";
        else if(searchPrototype.equals("")) sqlQueryString = "select * from consoles" + ";";

	    try {
	    	theStatement = theConnection.createStatement();
			ResultSet rs = theStatement.executeQuery(sqlQueryString);
			
            ArrayList<Console> consoleSearchResults = new ArrayList<Console>();

	        int count = 0;
	        while (rs.next() && count < GUI_DISPLAY_LIMIT){
	        	Console nConsole = new Console(
	        			rs.getString("console_name"),
	        			rs.getInt("consoleID")
	        			);
	        	consoleSearchResults.add(nConsole);
            count++;
	        }
	        
	        rs.close(); //close the query result table
	        Console consoleArray[] = new Console[1]; //just to establish array type
	        consoleArrayList = consoleSearchResults;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		System.out.println("Search clicked");
		updateSearchButton();
		updateList();
	}

	private void addNewConsoleButtonClicked(){
		AddNewConsoleDialog dialog = new AddNewConsoleDialog(fakeOwnerFrame, theDialogClient, "New Console", true);
		dialog.setVisible(true);
	}
	
	private void cancelButtonClicked(){
		if (theDialogClient != null){
			theDialogClient.dialogCancelled();
		}
		dispose();
	}
	
	private void updateList() {
	    Console consoleArray[] = new Console[1]; //just to establish array type
	    consoleList.setListData(((Console []) consoleArrayList.toArray(consoleArray)));

		if (selectedConsole != null)
			consoleList.setSelectedValue(selectedConsole, true);
	}
	
	private void updateSearchButton() {
		searchConsolesButton.setEnabled(true);
	}
		
}
