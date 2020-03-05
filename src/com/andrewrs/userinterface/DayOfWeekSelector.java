package com.andrewrs.userinterface;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DayOfWeekSelector extends JPanel
{
	/**
	 * A Graphical Element that displays the days of the week, and saves the state of which days have been selected
	 */
	private static final long serialVersionUID = 1L;
	public static final int DAY_COUNT = 7;
	private ArrayList<DayOfWeek> days;
	
	public DayOfWeekSelector(int x,int y,int width, int height)
	{
		days=new ArrayList<DayOfWeek>();
		this.setBounds(x, y, width, height);
		{//rolled out for loop for window builder parsing
			int i=0;
			int dWidth=width/7;
			y=0;
			
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				for(int i=0;i<days.size();i++)
				{
					days.get(i).onClick(x, y);
				}

				repaint();
			}});
	}
	public DayOfWeekSelector(Rectangle bounds)
	{
		days=new ArrayList<DayOfWeek>();
		this.setBounds(bounds);
		
		{//rolled out for loop for window builder parsing
			int x = bounds.x;
			int y = bounds.y;
			int width = bounds.width;
			int height = bounds.height;
			int i = 0;
			int dWidth = width/7;
			y = 0;
			
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				for(int i=0;i<days.size();i++)
				{
					days.get(i).onClick(x, y);
				}
				repaint();
			}});
	}
	public void setSize(Rectangle bounds)
	{
			int x = bounds.x;
			int y = bounds.y;
			int width = bounds.width;
			int height = bounds.height;
			int dWidth = width/7;
			y = 0;
			for(int i = 0;i<days.size();i++)
			{
				days.get(i).setX(x+i*dWidth);
				days.get(i).setY(y);
				days.get(i).setWidth(dWidth);
				days.get(i).setHeight(height);
			}
			
	}
	@Override
	public void paintComponent(Graphics g)
	{
		for(DayOfWeek d:days)
		{
			d.draw(g);
		}
	}
	public void clear()
	{
		for(DayOfWeek d:days)
		{
			d.setSelected(false);
		}
	}
	public String getDayName(int i)
	{
		return days.get(i).getDayName();
	}
	public boolean isSelectedByIndex(int i)
	{
		return days.get(i).isSelected();
	}
}
