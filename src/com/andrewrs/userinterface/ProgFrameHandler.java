package com.andrewrs.userinterface;

import java.util.Map;

public class ProgFrameHandler 
{
	private boolean isRunning=true;
	
	public ProgFrameHandler()
	{
		ProgramState.freezeState();	
	}
	public void update()
	{
		boolean running = false;
		
		ProgFrame currentFrame = ProgramState.FRAMES.get(ProgramState.getState());
			if(!ProgramState.getLastState().equals(ProgramState.getState()))
			{
				if(ProgramState.getLastState().length()>0)
				{
					ProgFrame lastFrame = ProgramState.FRAMES.get(ProgramState.getLastState());
					lastFrame.setVisible(false);
					lastFrame.onClose();
				}
				currentFrame.onLoad();
				currentFrame.setVisible(true);
			}

			if(currentFrame.isVisible())
				running = true;
			
			currentFrame.whileRunning();
			
		
		this.isRunning = running;
		ProgramState.freezeState();
	}
	public void close()
	{
		for(Map.Entry<String, ProgFrame> entry : ProgramState.FRAMES.entrySet())
			entry.getValue().dispose();
	}
	public boolean isRunning() 
	{
		//Turning off program is a matter of setting the frame 
		//of the current state invisible
		return isRunning;
	}
}
