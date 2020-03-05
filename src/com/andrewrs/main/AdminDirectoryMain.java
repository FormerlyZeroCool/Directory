package com.andrewrs.main;

import java.net.MalformedURLException;

import com.andrewrs.userinterface.LocationManagerForm;
import com.andrewrs.userinterface.LoginForm;
import com.andrewrs.userinterface.NewUserForm;
import com.andrewrs.userinterface.OperationsManagerForm;
import com.andrewrs.userinterface.ProgFrameHandler;
import com.andrewrs.userinterface.ProgramState;
import com.andrewrs.userinterface.UserManagerForm;
import com.andrewrs.ws.HTTPHandler;

public class AdminDirectoryMain 
{

	public static HTTPHandler HTTP;
	public static OperationsManagerForm OPERATIONSMANAGERFORM = new OperationsManagerForm();
	
	public static void main(String args[])
	{
		try {
			HTTP=new HTTPHandler(remoteUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		LoginForm login = new LoginForm("Login");

		while(!login.isLoggedIn())
		{
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//login.currentUser.setAdmin(false);
		login.setVisible(false);
		ProgFrameHandler frame = new ProgFrameHandler();
		try {
			ProgramState.addFrame(OPERATIONSMANAGERFORM);
			LocationManagerForm locationManager = new LocationManagerForm(login.currentUser);
			ProgramState.addFrame(locationManager);
			ProgramState.addFrame(new UserManagerForm(login.currentUser));
			ProgramState.addFrame(new NewUserForm(login.currentUser));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ProgramState.setState("LocationManager");
		while(frame.isRunning())
		{
			try {
			frame.update();
			
				Thread.sleep(50);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		frame.close();
	}
}
