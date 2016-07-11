package ravens_roost_application;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AllStudentsResultPanel extends JPanel {
	private JList studentList;
	private JButton searchStudentButton;
	private JButton cancelButton;
	private JTextField searchBox;
	
	private Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	public JList getStudentsList(){
		return studentList;
	}

	
	public JButton getSearchButton(){
		return searchStudentButton;
	}
	
	public JButton getCancelButton(){
		return cancelButton;
	}
	
	public JTextField getSearchBox(){
		return searchBox;
	}
	
	public AllStudentsResultPanel(){
		super();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);
		
		studentList = new JList();
		studentList.setFont(UIFont);
		studentList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( studentList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 5;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

		// Add the Add button
		searchBox = new JTextField("");
		searchBox.setFont(UIFont);

		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchBox, layoutConstraints);
		add(searchBox);

		// Add the Remove button
		searchStudentButton = new JButton("Search");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchStudentButton, layoutConstraints);
		add(searchStudentButton);
		
		cancelButton = new JButton("Cancel");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(cancelButton, layoutConstraints);
		add(cancelButton);

	}
}
