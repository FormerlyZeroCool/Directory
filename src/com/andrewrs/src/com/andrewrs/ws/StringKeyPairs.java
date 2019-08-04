package com.andrewrs.ws;

public class StringKeyPairs 
{
	private String key,value;
	private int dataType;
	public static final int STRING=0,INT=1,STRINGARR=2,OBJECT=3,ARRAY=4,NAMELESSOBJECT=5;
	
	public StringKeyPairs(String key,String value, int dataType)
	{
		this.setKey(key);
		this.setValue(value);
		this.dataType=dataType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
}
