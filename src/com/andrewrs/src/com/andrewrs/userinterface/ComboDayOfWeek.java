package com.andrewrs.userinterface;

import javax.swing.JComboBox;

public class ComboDayOfWeek  extends JComboBox<String>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComboDayOfWeek()
	{
		super();
		
		for(int i = 0;i < 7;i++)
		{
			this.addItem(getDayName(i));
		}
		this.setMaximumRowCount(7);
		this.setVisible(true);
	}
	
	public ComboDayOfWeek(String day)
	{
		super();
		
		for(int i = 0;i < 7;i++)
		{
			this.addItem(getDayName(i));
		}
		this.setMaximumRowCount(7);
		this.setVisible(true);
		this.setSelectedItem(day);
	}

	public String getDayName(int dayOfWeek)
	{
		switch(dayOfWeek)
		{
		case 0:
			return"Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
			
		}
		return "Invalid Day";
	}
}
