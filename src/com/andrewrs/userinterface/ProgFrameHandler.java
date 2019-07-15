package com.andrewrs.userinterface;

import com.andrewrs.userinterface.NewUserForm;
public class ProgFrameHandler 
{
	private UserData currentUser;
	private LocationManagerForm locationManagerForm;
	private OperationsManagerForm operationsManagerForm;
	private NewUserForm newUserForm;
	private UserManagerForm userManager;
	private boolean isRunning=true;
	public ProgFrameHandler(UserData user)
	{

		newUserForm=new NewUserForm(user);
		locationManagerForm=new LocationManagerForm(user);
		userManager=new UserManagerForm(user);
		operationsManagerForm = new OperationsManagerForm();
		newUserForm.setVisible(false);
		locationManagerForm.setVisible(false);
		userManager.setVisible(false);
		this.currentUser=user;
		
		ProgramState.setState(ProgramState.MANAGE_LOCATIONS);
		locationManagerForm.setVisible(true);
		
			

		ProgramState.freezeState();	
		
	}
	public void setSomething()
	{
		locationManagerForm.setVisible(true);
		isRunning=true;
		ProgramState.freezeState();	
	}
	public void update()
	{

		if(!newUserForm.isVisible() && !locationManagerForm.isVisible() &&
				!userManager.isVisible() && !operationsManagerForm.isVisible())
			isRunning=false;
		else 
		if(ProgramState.getLastState()!=ProgramState.getState())//Section only runs when changing state
		{//next section allows you to enter subroutines to execute after switching pages like cleaning forms
			if(ProgramState.getLastState()==ProgramState.NEW_USER)
			{
				newUserForm.setVisible(false);
				newUserForm.clearForm();
			}
			else if(ProgramState.getLastState()==ProgramState.MANAGE_LOCATIONS)
			{
				locationManagerForm.setVisible(false);	
				if(locationManagerForm.getSelectedLocationIndex()>-1)
				{
					operationsManagerForm.setNewLocationPanesVisibility(false);
					operationsManagerForm.setData(
							locationManagerForm.getLocationOperationsData(
									locationManagerForm.getSelectedLocationIndex()));
				}
				else
				{
					operationsManagerForm.setNewLocationPanesVisibility(true);
				}
			}
			else if(ProgramState.getLastState()==ProgramState.MANAGE_USERS)
			{
				userManager.setVisible(false);
			}
			else if(ProgramState.getLastState()==ProgramState.MANAGE_OPERATIONS)
			{
				operationsManagerForm.setVisible(false);
			}
			//This section lets you do something while the page is loading like getting from data sources, 
			//and visualizing data
			if(ProgramState.getState()==ProgramState.NEW_USER)
			{
				newUserForm.setTitle("Add New User");
				newUserForm.setVisible(true);
			}
			else if(ProgramState.getState()==ProgramState.MANAGE_LOCATIONS)
			{
				locationManagerForm.setTitle("Locations Editor");
				locationManagerForm.refreshLocationsData();
				locationManagerForm.refreshTable();
				locationManagerForm.setVisible(true);
			}
			else if(ProgramState.getState()==ProgramState.MANAGE_OPERATIONS)
			{
				operationsManagerForm.setVisible(true);
				operationsManagerForm.repaint();
			}
			else if(ProgramState.getState()==ProgramState.MANAGE_USERS)
			{
				userManager.setUser(currentUser);
				userManager.refreshTable();
				userManager.setTitle("Manage Users");
				userManager.setVisible(true);
			}

			ProgramState.freezeState();
		}
		//runs every time frame is updated, but only executes on the current program states instructions should be simple
		//things that can be done many times a second, like drawing frames of a video
		//or updating sizing on elements
		if(ProgramState.getState()==ProgramState.NEW_USER)
		{
			newUserForm.setVisible(true);
		}
		else if(ProgramState.getState()==ProgramState.MANAGE_LOCATIONS)
		{
			locationManagerForm.setVisible(true);
		}
		else if(ProgramState.getState()==ProgramState.MANAGE_USERS)
		{
			userManager.setVisible(true);
			
		}
		else if(ProgramState.getState()==ProgramState.MANAGE_OPERATIONS)
		{
			operationsManagerForm.setVisible(true);
			operationsManagerForm.repaint();
		}
	}
	public void close()
	{
		locationManagerForm.dispose();
		operationsManagerForm.dispose();
		newUserForm.dispose();
		userManager.dispose();
	}
	public boolean isRunning() 
	{
		//all forms should be chained then turning off program is a matter of setting the frame 
		//of the current state invisible
		if(!newUserForm.isVisible() && !locationManagerForm.isVisible() && !userManager.isVisible() &&
				!operationsManagerForm.isVisible())
			isRunning=false;
		return isRunning;
	}
}
