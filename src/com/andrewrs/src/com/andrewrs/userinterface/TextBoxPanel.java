package com.andrewrs.userinterface;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class TextBoxPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5632759221613115724L;


	public JTextField textField;
	public JLabel label;
	
	public TextBoxPanel(String label) 
	{
		super();

		setLayout(new GridLayout(2, 1, 0, 0));
		
		this.label = new JLabel(label);
		this.add(this.label);
		
		textField = new JTextField();
		this.add(textField);
		textField.setColumns(12);
		
	}

}
