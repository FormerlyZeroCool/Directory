package com.andrewrs.userinterface;
import java.util.ArrayList;

import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.jsonparser.JsonObjectification;
import com.andrewrs.main.AdminDirectoryMain;
import com.andrewrs.ws.StringKeyPairs;

public class LocationData
{

	private String name="",room="",address="",contactEmail="",telephone="",id="";
	public OperationsData locationOperations;
	public LocationData(JsonObject data)//Should be an individual location's Data
	{
		id = data.getChild("_id").getData();
		
		if(data.getChild("Name")!=null)
			name = data.getChild("Name").getData();

		if(data.getChild("Room")!=null)
			room = data.getChild("Room").getData();

		if(data.getChild("Address")!=null)
			setAddress(data.getChild("Address").getData());

		if(data.getChild("Operations")!=null)
			locationOperations = new OperationsData(data.getChild("Operations"),this);

		if(data.getChild("Telephone")!=null)
			setAddress(data.getChild("Address").getData());

		if(data.getChild("ContactEmail")!=null)
			locationOperations = new OperationsData(data.getChild("Operations"),this);
	}
	public LocationData(String name,String room,String address)
	{
		this.name=name;
		this.room=room;
		this.address=address;
		JsonObjectification emptyOperations = 
				new JsonObjectification("{\"Operations\":{\"Monday\":[],\"Tuesday\":[],\"Wednesday\":[],\"Thursday\":[],\"Friday\":[],\"Saturday\":[],\"Sunday\":[]}}");
		locationOperations = new OperationsData(emptyOperations.jsonObject.getChild("Operations"),this);
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setRoom(String room)
	{
		this.room=room;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public void deleteOnWs()
	{
		try {
			AdminDirectoryMain.HTTP.deleteById("locations", this.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void save()
	{
		try {
				AdminDirectoryMain.HTTP.putJsonString("locations/"+id, this.jsonify());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String jsonify()
	{
		ArrayList<StringKeyPairs> data = new ArrayList<StringKeyPairs>();
		data.add(new StringKeyPairs("operations",locationOperations.jsonify(),StringKeyPairs.OBJECT));
		//if(id!="")
			//data.add(new StringKeyPairs("_id",id,StringKeyPairs.STRING));
		data.add(new StringKeyPairs("name",name,StringKeyPairs.STRING));
		data.add(new StringKeyPairs("room",room,StringKeyPairs.STRING));
		data.add(new StringKeyPairs("address",address,StringKeyPairs.STRING));
		data.add(new StringKeyPairs("telephone",this.telephone,StringKeyPairs.STRING));
		data.add(new StringKeyPairs("contactEmail",this.contactEmail,StringKeyPairs.STRING));
		return jsonBuilder(data);
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
			else if(entry.getDataType()==StringKeyPairs.STRINGARR)
			{
				appender.append("[");
				String entries[]=entry.getValue().split(",");
				for(int i=0;i<entries.length;i++)
				{
					appender.append("\"");
					appender.append(entries[i]);
					appender.append("\"");
					if(i!=entries.length-1)
					{
						appender.append(",");						
					}
				}
				appender.append("]");
			}
			else if(entry.getDataType()==StringKeyPairs.OBJECT)
			{
				appender.append("{");
					appender.append(entry.getValue());
				
				appender.append("}");
			}
			else
			{
				appender.append(entry.getValue());
			}

			if(data.indexOf(entry)!=data.size()-1)
			{
				appender.append(",");						
			}
		}
		appender.append('}');
		return appender.toString();
	}
	public String getId()
	{
		return id;
	}
	public String getName() {
		return name;
	}
	public String getRoom() {
		return room;
	}
	public String getAddress() {
		return address;
	}
	public void setOperations(OperationsData operations) 
	{
		locationOperations = operations;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getContactEmail() 
	{
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) 
	{
		this.contactEmail = contactEmail;
	}
	public String getTelephone() 
	{
		return telephone;
	}
	public void setTelephone(String telephone) 
	{
		this.telephone = telephone;
	}
	 
}
