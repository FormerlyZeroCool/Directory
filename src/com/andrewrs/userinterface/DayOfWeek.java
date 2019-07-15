package com.andrewrs.userinterface;

import java.awt.Color;
import java.awt.Graphics;

public class DayOfWeek 
{

	private int dayOfWeek;
	private int x,y,width,height;
	private boolean isSelected=false;
	public boolean isSelected() 
	{
		return isSelected;
	}
	public void setSelected(boolean isSelected) 
	{
		this.isSelected = isSelected;
	}
	public void onClick(int x,int y)
	{
		if(this.x<x && this.x+this.width>x && this.y<y && this.y+this.height>y)
		{
			isSelected=!isSelected;
		}
	}
	private Color black=new Color(0,0,0),white=new Color(255,255,255);
	public DayOfWeek(int x,int y,int width, int height,int dayOfWeek)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.dayOfWeek=dayOfWeek;
	}
	public void draw(Graphics g)
	{
		//System.out.println(x+" "+y+" "+width+" "+height);
		if(isSelected)
			g.setColor(new Color(155,155,155));
		else
			g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		int j=2;
		for(int i=2;i<10;i+=j)
		{
			g.drawLine(x, y+j, x+width-j, y+j);
			g.drawLine(x, y+height-j, x+width-j, y+height-j);
			//g.drawLine(x+width-j, y, x+width-j, y+height-j);
			j++;
		}
		int sy=y+25;
		int sx=x+5;
		switch(dayOfWeek)
		{
		case 0:
			g.drawString("Sunday",sx, sy);
			break;
		case 1:
			g.drawString("Monday",sx, sy);
			break;
		case 2:
			g.drawString("Tuesday",sx, sy);
			break;
		case 3:
			g.drawString("Wednesday",sx, sy);
			break;
		case 4:
			g.drawString("Thursday",sx, sy);
			break;
		case 5:
			g.drawString("Friday",sx, sy);
			break;
		case 6:
			g.drawString("Saturday",sx, sy);
			break;
		}
		
	}
	public String getDayName()
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
	public void setWidth(int width) 
	{
		this.width = width;
		
	}
	public void setHeight(int height) 
	{
		this.height = height;
		
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getDayIndex() {
		return dayOfWeek;
	}
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}
