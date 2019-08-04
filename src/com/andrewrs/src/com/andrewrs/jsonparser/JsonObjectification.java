package com.andrewrs.jsonparser;


public class JsonObjectification 
{
	public JsonObject jsonObject;
	public JsonObjectification() {}
	public JsonObjectification(String json)
	{
		String baseStartTag,baseEndTag;
		if(json.charAt(0)=='[')
		{
			baseStartTag="";
			baseEndTag="";
			jsonObject=new JsonObject(JsonObject.ARRAY, 
					new JsonObject(""),
					"base",
					"\"base\":"+baseStartTag+json+baseEndTag);
		}
		else
		{
			baseStartTag= "{";
			baseEndTag= "}";
			jsonObject=new JsonObject(JsonObject.OBJECT, 
					null,
					"base",
					"\"base\":"+baseStartTag+json+baseEndTag);
		}
	}
	public void objectifyJson(String json)
	{
		String baseStartTag,baseEndTag;
		if(json.charAt(0)=='[')
		{
			baseStartTag="";
			baseEndTag="";
			jsonObject=new JsonObject(JsonObject.ARRAY, 
					new JsonObject(""),
					"base",
					"\"base\":"+baseStartTag+json+baseEndTag);
		}
		else
		{
			baseStartTag= "{";
			baseEndTag= "}";
			jsonObject=new JsonObject(JsonObject.OBJECT, 
					null,
					"base",
					"\"base\":"+baseStartTag+json+baseEndTag);
			
		}
	}
	public void print()
	{
		for(JsonObject d: jsonObject.children)
			d.printData();
	}
}