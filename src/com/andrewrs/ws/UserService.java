package com.andrewrs.ws;

import java.math.BigInteger;
import java.util.ArrayList;
import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.jsonparser.JsonObjectification;
import com.andrewrs.main.AdminDirectoryMain;
import com.andrewrs.userinterface.LoginForm;
import com.andrewrs.userinterface.UserData;

public class UserService 
{

	private UserData user;
	public UserService(UserData user)
	{
		this.user=user;
	}
	public void postUser()
	{
		ArrayList<StringKeyPairs> map=new ArrayList<StringKeyPairs>();
		String userId=new BigInteger(LoginForm.hashPass(user.getUserName().toCharArray()).toString(),10).toString(16);
		map.add(new StringKeyPairs("_id", userId,StringKeyPairs.STRING));
		map.add(new StringKeyPairs("username", user.getUserName(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("password", user.getHashedPass().toString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("__v", Integer.toString(user.isAdmin()?1:0),StringKeyPairs.INT));
		
		System.out.println(jsonBuilder(map));
        try {
        		AdminDirectoryMain.HTTP.postJsonString("users", jsonBuilder(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void putUser()
	{
		ArrayList<StringKeyPairs> map=new ArrayList<StringKeyPairs>();
		String userId=new BigInteger(LoginForm.hashPass(user.getUserName().toCharArray()).toString(),10).toString(16);
		//map.add(new StringKeyPairs("_id", userId,StringKeyPairs.STRING));
		map.add(new StringKeyPairs("username", user.getUserName(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("password", user.getHashedPass().toString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("__v", Integer.toString(user.isAdmin()?1:0),StringKeyPairs.INT));
		
		System.out.println(jsonBuilder(map));
        try {
        		AdminDirectoryMain.HTTP.putJsonString("users/"+userId, jsonBuilder(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public UserData getUserRecordById(String id)
	{
		UserData record=new UserData();
		try{
        String bodyResp=AdminDirectoryMain.HTTP.getDataById("users", id);
        JsonObjectification data = new JsonObjectification(bodyResp);
        record.setUserName(data.jsonObject.getChild("userName").getData());
        record.setHashedPass(new BigInteger(data.jsonObject.getChild("password").getData()));
        record.setAdmin(data.jsonObject.getChild("__v").getData().equals("1"));
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	public ArrayList<UserData> getAllUserRecords()
	{
		ArrayList<UserData> users=new ArrayList<UserData>();
		try{
		
        String bodyResp=AdminDirectoryMain.HTTP.getData("users");
        JsonObjectification data = new JsonObjectification(bodyResp);
        for(JsonObject child : data.jsonObject.children)
        {
        	users.add(new UserData(child.getChild("username").getData(),
        			child.getChild("password").getData(),
        			child.getChild("__v").getData().equals("1")));
        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	public void updateLocalUserById(String id)
	{
		try {

			
            String bodyResp=AdminDirectoryMain.HTTP.getDataById("users", id);
            JsonObjectification data = new JsonObjectification(bodyResp);
            user.setHashedPass(new BigInteger(data.jsonObject.getChild("password").getData()));
            user.setAdmin(data.jsonObject.getChild("__v").getData().equals("1"));

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	private String jsonBuilder(ArrayList<StringKeyPairs> data)
	{
		StringBuilder appender=new StringBuilder("{");
		for(StringKeyPairs entry:data)
		{
			appender.append("\"");
			appender.append(entry.getKey());
			appender.append("\":");
			if(entry.getDataType()==StringKeyPairs.STRING)
			{
				appender.append("\"");
				appender.append(entry.getValue());
				appender.append("\"");
			}
			else
			{
				appender.append(entry.getValue());
			}

			appender.append(",");
		}
		appender.deleteCharAt(appender.length()-1);
		appender.append('}');
		return appender.toString();
	}
	public void deleteUser(String userId) 
	{
        try {
        	AdminDirectoryMain.HTTP.deleteById("users", userId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
