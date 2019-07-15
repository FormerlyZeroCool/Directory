package com.andrewrs.userinterface;

import javax.swing.JComboBox;

public class ComboTime extends JComboBox<String>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComboTime()
	{
		super();
		fillCombo(20);
		this.setMaximumRowCount(8);
		this.setVisible(true);
	}
	public ComboTime(String time)
	{
		super();
		fillCombo(20);
		this.setMaximumRowCount(8);
		this.setVisible(true);
		this.setSelectedItem(time);
	}
	public ComboTime(int rowCount)
	{
		super();
		fillCombo(20);
		this.setMaximumRowCount(rowCount);
		this.setVisible(true);
	}
	private void fillCombo(int startIndex) 
	{

		StringBuilder time = new StringBuilder();
		for(int i = startIndex;i<96;i++)
		{
			time.append(i/4<13?i/4:i/4-12);
			time.append(':');
			time.append((i%4)<1?"00":15*(i%4));
			if(i<48)
				time.append(" am");
			else
				time.append(" pm");
			this.addItem(time.toString());
			time.setLength(0);
		}

	}
}
