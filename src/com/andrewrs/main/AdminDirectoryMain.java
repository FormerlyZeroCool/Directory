package com.andrewrs.main;

import java.net.MalformedURLException;
import com.andrewrs.userinterface.LoginForm;
import com.andrewrs.userinterface.ProgFrameHandler;
import com.andrewrs.ws.HTTPHandler;

public class AdminDirectoryMain 
{

	public static HTTPHandler HTTP;
	public static void main(String args[])
	{
		try {
			HTTP=new HTTPHandler("https://qj7reyhuy0.execute-api.us-east-1.amazonaws.com/dev/api");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		LoginForm login = new LoginForm("Login");
		
		while(!login.isLoggedIn())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		login.setVisible(false);
		ProgFrameHandler frame = new ProgFrameHandler(login.currentUser);
		while(frame.isRunning())
		{
			frame.update();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
