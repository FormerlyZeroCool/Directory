package com.andrewrs.userinterface;

import java.math.BigInteger;
import java.util.ArrayList;
import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.jsonparser.JsonObjectification;
import com.andrewrs.main.AdminDirectoryMain;

public class UserData 
{
	private String userName;
	private boolean isAdmin;
	private BigInteger hashedPass;
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
	private ArrayList<UserData> getAllUserRecords()
	{
		ArrayList<UserData> users=new ArrayList<UserData>();
		try{
		
        String bodyResp=AdminDirectoryMain.HTTP.getData("users");
        JsonObjectification data = new JsonObjectification(bodyResp);
        for(JsonObject child : data.jsonObject.children)
        {
        	users.add(
        			new UserData(child.getChild("userName")!=null?child.getChild("userName").getData():"",
        			child.getChild("password")!=null?child.getChild("password").getData():"",
        			child.getChild("isAdmin")!=null?child.getChild("isAdmin").getData().equals("true"):false)
        			);
        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public ArrayList<UserData> getAllUsersData()
	{
		if(isAdmin)
			return getAllUserRecords();
		else 
			return new ArrayList<UserData>();
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
		 try {
	            String bodyResp=AdminDirectoryMain.HTTP.getData("users?userNameLower="+userName.toLowerCase().replace("@", "%40").replace(".","%2E"));
	            JsonObjectification data = new JsonObjectification(bodyResp);
	            setHashedPass(new BigInteger(data.jsonObject.getChild(0)!=null?data.jsonObject.getChild(0).getChild("password").getData():"0"));
	            setAdmin(data.jsonObject.getChild(0).getChild("isAdmin")!= null?data.jsonObject.getChild(0).getChild("isAdmin").getData().equals("true"):false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getUserId()
	{
		return new BigInteger(LoginForm.hashPass(userName.toCharArray()).toString(),10).toString(16);
	}
	public void postUser()
	{
		String data = this.jsonify();
		try {
    		AdminDirectoryMain.HTTP.postJsonString("users", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String jsonify()
	{
		StringBuilder string=new StringBuilder();
		string.append('{');
		string.append("\"userName\":\"");
		string.append(userName.toLowerCase());
		string.append("\",");
		string.append("\"password\":\"");
		string.append(hashedPass);
		string.append("\",");
		string.append("\"isAdmin\":");
		string.append(isAdmin);
		string.append("}");
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
        try {
        	System.out.println("Attempting to save put to user id:");
        	String id = this.getUserId();
        	String data = this.jsonify();
        	System.out.println();
    		AdminDirectoryMain.HTTP.putJsonString("users:"+id, data);
        } catch (Exception e) {
        	e.printStackTrace();
        }

	}
	public void deleteOnWS() 
	{
		System.out.print("Attemping to Delete User:");
		System.out.println(getUserId());

        try {
        	AdminDirectoryMain.HTTP.deleteByAttribute("users", jsonify());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
