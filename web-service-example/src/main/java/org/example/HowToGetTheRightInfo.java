package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HowToGetTheRightInfo
{ //TODO
	private JSONObject sensorValues;
	private JSONArray directions;
	private JSONObject neededValue;
	
	public HowToGetTheRightInfo(String sensorValues, String directions, String neededValue) 
	{
		this.sensorValues = new JSONObject();
		this.directions = new JSONArray();
		this.neededValue = new JSONObject();
		
		this.directions.put(directions);
		this.neededValue.put(neededValue, this.directions);
		this.sensorValues.put(sensorValues, this.neededValue);
	}
	
}
