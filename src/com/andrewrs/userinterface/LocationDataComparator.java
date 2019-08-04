package com.andrewrs.userinterface;

import java.util.Comparator;

public class LocationDataComparator implements Comparator<LocationData> {

		@Override
		public int compare(LocationData o1, LocationData o2) 
		{
			return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
		}
	
}
