package com.andrewrs.userinterface;

import java.util.HashMap;

public class ProgramState {
	private static String state = "";
	private static String lastState = state;

	public static HashMap<String,ProgFrame> FRAMES = new HashMap<String,ProgFrame>(8,0.75f);
	
	public static void addFrame(ProgFrame newFrame) throws Exception
	{
		if(ProgramState.FRAMES.get(newFrame.getName()) == null)
			ProgramState.FRAMES.put(newFrame.getName(),newFrame);
		else 
			throw new Exception("Duplicate entry added to frame map, please ensure the names of all frames are unique.");
	}
	public static String getLastState()
	{
		return ProgramState.lastState;
	}
	public static String getState()
	{
		return ProgramState.state;
	}
	public static void setState(String state)
	{
		ProgramState.lastState = ProgramState.state;
		ProgramState.state = state;
	}
	public static void freezeState()
	{
		ProgramState.lastState = ProgramState.state;
	}
}
