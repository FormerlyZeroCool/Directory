package com.andrewrs.userinterface;

import java.util.ArrayList;

import com.andrewrs.main.AdminDirectoryMain;
import com.andrewrs.ws.StringKeyPairs;

public class TimeBlock
{
	private String id,dayOfWeek;
	private int endHour,startHour,endMinute,startMinute;
	private boolean isStartAm,isEndAm;
	private DailyTimes parent;
	public TimeBlock(DailyTimes parent, String id,String dayOfWeek, int startHour,int startMinute,boolean isStartAm,int endHour,int endMinute,boolean isEndAm)
	{
		this.setParent(parent);
		this.setId(id);
		this.setDayOfWeek(dayOfWeek);
		this.setStartHour(startHour);
		this.setEndHour(endHour);
		this.setStartMinute(startMinute);
		this.setEndMinute(endMinute);
		this.setStartAm(isStartAm);
		this.setEndAm(isEndAm);
	}
	public String jsonify()
	{
		ArrayList<StringKeyPairs> data = new ArrayList<StringKeyPairs>();
		data.add(new StringKeyPairs("startHour",intToString(startHour),StringKeyPairs.STRING));
		data.add(new StringKeyPairs("startMinute",intToString(startMinute),StringKeyPairs.STRING));
		data.add(new StringKeyPairs("isStartAm",isStartAm?"1":"0",StringKeyPairs.STRING));
		data.add(new StringKeyPairs("endHour",intToString(endHour),StringKeyPairs.STRING));
		data.add(new StringKeyPairs("endMinute",intToString(endMinute),StringKeyPairs.STRING));
		data.add(new StringKeyPairs("isEndAm",isEndAm?"1":"0",StringKeyPairs.STRING));
		return jsonBuilder(data);
	}
	
	public void delete() throws Exception
	{
		if(parent.remove(this))
		{
			//get parent location now it should not contain this timeblock anymore as we removed it from this object's parent
			LocationData location = parent.getParent().getParent();
			AdminDirectoryMain.HTTP.putJsonString("locations/"+location.getId(), location.jsonify());
		}
		else
			System.out.println("Nothing deleted from operations.dailytimes.timeblocks");
	}
	public String intToString(int i)
	{
		return (i<10?"0"+Integer.toString(i):Integer.toString(i));
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
					if(data.indexOf(entry)!=data.size()-1)
					{
						appender.append(",");						
					}
					else
						appender.append("]");
			}
			else if(entry.getDataType()==StringKeyPairs.OBJECT)
			{
				appender.append("{");
					appender.append(entry.getValue());
				
				appender.append("}");
				if(data.indexOf(entry)!=data.size()-1)
				{
					appender.append(",");						
				}
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
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	public int getStartMinute() {
		return startMinute;
	}
	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	public boolean isStartAm() {
		return isStartAm;
	}
	public void setStartAm(boolean isStartAm) {
		this.isStartAm = isStartAm;
	}
	public boolean isEndAm() {
		return isEndAm;
	}
	public void setEndAm(boolean isEndAm) {
		this.isEndAm = isEndAm;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public DailyTimes getParent() {
		return parent;
	}
	public void setParent(DailyTimes parent) {
		this.parent = parent;
	}
}
