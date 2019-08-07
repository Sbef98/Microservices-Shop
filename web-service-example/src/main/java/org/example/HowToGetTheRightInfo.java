package org.example;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class HowToGetTheRightInfo
{
	private HashMap<String, ValueReferences> neededSensor = new HashMap<String, ValueReferences>();
	
	public HowToGetTheRightInfo(String neededSensorName, String valueName, String reference)
	{
		ValueReferences valueReferences = new ValueReferences(valueName, reference);
		this.neededSensor.put(neededSensorName, valueReferences);
	}
	
	public void addReference(String neededSensorName, String valueName, String reference)
	{
		
	}
	
	public void addNeededSensor(String neededSensorName, String valueName, String reference)
	{
		if(neededSensor.get(neededSensorName) != null) {
			
		}
	}
}
