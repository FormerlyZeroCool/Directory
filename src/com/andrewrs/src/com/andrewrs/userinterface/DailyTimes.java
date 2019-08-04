package com.andrewrs.userinterface;

import java.util.ArrayList;

import com.andrewrs.ws.StringKeyPairs;

public class DailyTimes extends ArrayList<TimeBlock>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private OperationsData parent;
	public DailyTimes(OperationsData parent,String name)
	{
		super();
		this.setParent(parent);
		this.setName(name);
	}
	public String jsonify()
	{
		ArrayList<StringKeyPairs> data = new ArrayList<StringKeyPairs>();
		for(TimeBlock time:this)
		{
			data.add(new StringKeyPairs("",time.jsonify(),StringKeyPairs.NAMELESSOBJECT));
		}
		return jsonBuilder(data);
	}
	private String jsonBuilder(ArrayList<StringKeyPairs> data)
	{
		StringBuilder appender=new StringBuilder("");
		for(StringKeyPairs entry:data)
		{
			if(entry.getDataType()!=StringKeyPairs.NAMELESSOBJECT)
			{
				appender.append("\"");
				appender.append(entry.getKey());
				appender.append("\":");
			}
			else
				entry.setDataType(StringKeyPairs.OBJECT);
			
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
					appender.append(entry);
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
		appender.append("");
		return appender.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public OperationsData getParent() {
		return parent;
	}
	public void setParent(OperationsData parent) {
		this.parent = parent;
	}
}
