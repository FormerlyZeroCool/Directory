package com.andrewrs.userinterface;

import javax.swing.JFrame;

public abstract class ProgFrame extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	public ProgFrame(String name)
	{
		this.name = name;
	}

	
	public abstract void onLoad();
	public abstract void onClose();
	public abstract void whileRunning();
	
	public String getName()
	{
		return this.name;
	}

}
