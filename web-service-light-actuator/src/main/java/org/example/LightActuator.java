package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class LightActuator extends ServiceController
{
	private float lightLevel;
	private float switchLevel = 500; //under 500 the lights are switched on
	@Override
	String getGetMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	JSONObject getValues() {
		JSONObject returnValue = new JSONObject();
		JSONArray values = new JSONArray();
		values.put(lightLevel);
		returnValue.put("lightLevel", values);
		return returnValue;
	}

	@Override
	JSONObject getWanted() {
		//TODO Auto-generated method stub
		return null;
	}

	@Override
	JSONObject getNeeded_sesnors() {
		JSONObject needed_sensor1 = new JSONObject();
		JSONObject neededValue1 = new JSONObject();
		JSONArray sensorsForValue1 = new JSONArray();
		return null;
	}

	@Override
	protected JSONObject getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void elabResponse(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
