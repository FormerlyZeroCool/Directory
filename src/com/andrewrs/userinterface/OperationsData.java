package com.andrewrs.userinterface;

import java.util.ArrayList;

import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.ws.StringKeyPairs;

public class OperationsData extends ArrayList<DailyTimes>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5160275643367775784L;
	private DailyTimes mon,tue,wed,thu,fri,sat,sun;
	public LocationData location;
	public String jsonify()
	{
		ArrayList<StringKeyPairs> data =new ArrayList<StringKeyPairs>();
		data.add(new StringKeyPairs("monday",mon.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("tuesday",tue.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("wednesday",wed.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("thursday",thu.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("friday",fri.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("saturday",sat.jsonify(),StringKeyPairs.ARRAY));
		data.add(new StringKeyPairs("sunday",sun.jsonify(),StringKeyPairs.ARRAY));
		return jsonBuilder(data);
	}
	
	public OperationsData(JsonObject data,LocationData parent)//JsonObject should be the Operations object already
	{
		this.location=parent;
		
			mon=new DailyTimes(this,"Monday");
			tue=new DailyTimes(this,"Tuesday");
			wed=new DailyTimes(this,"Wednesday");
			thu=new DailyTimes(this,"Thursday");
			fri=new DailyTimes(this,"Friday");
			sat=new DailyTimes(this,"Saturday");
			sun=new DailyTimes(this,"Sunday");
			
			this.add(mon);
			this.add(tue);
			this.add(wed);
			this.add(thu);
			this.add(fri);
			this.add(sat);
			this.add(sun);
			
			JsonObject monday=data.getChild("Monday");
			if(monday!=null)
			for(JsonObject timeBlock : monday.children)
			{
				mon.add(buildTimeBlock(mon,timeBlock));	
			}	
		
			JsonObject tuesday=data.getChild("Tuesday");
			if(tuesday!=null)
			for(JsonObject timeBlock : tuesday.children)
			{
				tue.add(buildTimeBlock(tue,timeBlock));
			}
		
			JsonObject wednesday=data.getChild("Wednesday");
			if(wednesday!=null)
			for(JsonObject timeBlock : wednesday.children)
			{
				wed.add(buildTimeBlock(wed,timeBlock));
			}
		
			JsonObject thursday=data.getChild("Thursday");
			if(thursday!=null)
			for(JsonObject timeBlock : thursday.children)
			{
				thu.add(buildTimeBlock(thu,timeBlock));
			}
		
			JsonObject friday=data.getChild("Friday");
			if(friday!=null)
			for(JsonObject timeBlock : friday.children)
			{
				fri.add(buildTimeBlock(fri,timeBlock));
			}
		
			JsonObject saturday=data.getChild("Saturday");
			if(saturday!=null)
			for(JsonObject timeBlock:saturday.children)
			{
				sat.add(buildTimeBlock(sat,timeBlock));
			}
		
			JsonObject sunday=data.getChild("Sunday");
			if(sunday!=null)
			for(JsonObject timeBlock:sunday.children)
			{
				sun.add(buildTimeBlock(sun,timeBlock));
			}
			
	}

	public TimeBlock findTimeBlockById(String id)
	{
		TimeBlock data=null;
		for(DailyTimes day : this)
		{
			for(TimeBlock time: day)
			{
				if(time.getId().equals(id))
				{
					data = time;
				}
			}
		}
		return data;
	}
	public TimeBlock buildTimeBlock(DailyTimes parent,JsonObject timeBlock)
	{

		int endHour = 0,
		startHour = 0,
		endMinute = 0,
		startMinute = 0;
		String id=timeBlock.getChild("_id").getData();
		String dayOfWeek = timeBlock.getParent().getName();
		//System.out.println("day of week from build time block operations data: "+dayOfWeek);
		boolean 
		isStartAm = timeBlock.getChild("isStartAm").getData().equals("true")?true:false,
		isEndAm = timeBlock.getChild("isEndAm").getData().equals("true")?true:false;
		
		
		if(timeBlock.getChild("startHour").getData().length()>0)
			startHour = Integer.parseInt(timeBlock.getChild("startHour").getData());
		
		if(timeBlock.getChild("startMinute").getData().length()>0)
			startMinute = Integer.parseInt(timeBlock.getChild("startMinute").getData());

		if(timeBlock.getChild("endHour").getData().length()>0)
			endHour = Integer.parseInt(timeBlock.getChild("endHour").getData());
		
		if(timeBlock.getChild("endMinute").getData().length()>0)
			endMinute = Integer.parseInt(timeBlock.getChild("endMinute").getData());
		
		
		return new TimeBlock(parent,id,dayOfWeek,startHour,startMinute,isStartAm,endHour,endMinute,isEndAm);
	}
	private String jsonBuilder(ArrayList<StringKeyPairs> data)
	{
		StringBuilder appender=new StringBuilder("");
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
			else if(entry.getDataType()==StringKeyPairs.ARRAY)
			{
				appender.append("[");
				appender.append(entry.getValue());
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
		return appender.toString();
	}

	public void addTimeBlock(String dayName, String startHour, String startMinute, boolean isStartAm, String endHour,
			String endMinute, boolean isEndAm) 
	{
		this.get(getDayIndex(dayName)).add(new TimeBlock(this.get(getDayIndex(dayName)),"",dayName,Integer.parseInt(startHour),
				Integer.parseInt(startMinute),
				isStartAm,
				Integer.parseInt(endHour),
				Integer.parseInt(endMinute),
				isEndAm));
	}

	private int getDayIndex(String name) 
	{
		int index = -1;
		
		for(int i = 0;i<this.size() && index==-1;i++)
		{
			if(this.get(i).getName().toLowerCase().equals(name.toLowerCase()))
			{
				index=i;
			}
		}
		return index;
	}

	public int getTimeCount()
	{
		return	this.mon.size()+
				this.tue.size()+
				this.wed.size()+
				this.thu.size()+
				this.fri.size()+
				this.sat.size()+
				this.sun.size();
	}
	public LocationData getLocation() 
	{
		return location;
	}
}
