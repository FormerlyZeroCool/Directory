package com.andrewrs.userinterface;

import java.math.BigInteger;
import java.util.ArrayList;

import com.andrewrs.ws.UserService;

public class UserData 
{
	private String userName;
	private boolean isAdmin;
	private BigInteger hashedPass;
	private final UserService webService=new UserService(this);
	private boolean isLoggedIn=false;
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public UserData(String userName,boolean isAdmin)
	{
		this.userName=userName;
		this.isAdmin=isAdmin;			
	}
	public UserData(String userName,String pass,boolean isAdmin)
	{
		this.userName=userName;
		this.isAdmin=isAdmin;
		this.hashedPass=new BigInteger(pass);
	}
	public ArrayList<UserData> getAllUsersData()
	{
		if(isAdmin)
			return webService.getAllUserRecords();
		else 
		{
			ArrayList<UserData> dummy=new ArrayList<UserData>();
			return dummy;
		}
	}
	public UserData(String userName)
	{
		this.userName=userName;
	}
	public UserData() 
	{
		this.isLoggedIn=false;
		this.isAdmin=false;
		this.isLoggedIn=false;
	}
	public void getPassFromWeb()
	{
		 BigInteger hexFormat=new BigInteger(LoginForm.hashPass(userName.toCharArray()).toString(),10);
		 webService.updateLocalUserById(hexFormat.toString(16));
	}
	public String getUserId()
	{
		return new BigInteger(LoginForm.hashPass(userName.toCharArray()).toString(),10).toString(16);
	}
	public void postUser()
	{
		webService.postUser();
	}
	public String toString()
	{
		StringBuilder string=new StringBuilder();
		string.append("userName:");
		string.append(userName);
		string.append("\n");
		string.append("hashedPass:");
		string.append(hashedPass);
		string.append("\n");
		string.append("isAdmin:");
		string.append(isAdmin);
		string.append("\n");
		return string.toString();
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public BigInteger getHashedPass() {
		return hashedPass;
	}

	public void setHashedPass(BigInteger hashedPass) {
		this.hashedPass = hashedPass;
	}
	public void closeLoginSession() 
	{
		this.hashedPass=BigInteger.valueOf(-1);
		this.isAdmin=false;
		this.userName="";
		this.isLoggedIn=false;
		
	}
	public void save()
	{
		webService.putUser();
	}
	public void deleteOnWS() 
	{

		System.out.println(getUserId());
		webService.deleteUser(getUserId());
	}
}
