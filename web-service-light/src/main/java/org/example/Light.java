package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Light extends ServiceController
{
	private Float lightLevel = (float) 0;
	private final Float wantedLightLevel = (float) 500;

	@GetMapping(value = "/get", produces = "application/json")
	public String get()
	{
		return toString();
	}
	
	@Override
	String getGetMapping() {
		return "/get";
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
		values.put(getNewLightLevel().toString());
		returnValue.put("lightLevel", values);
		return returnValue;
	}

	@Override
	JSONObject getWanted() {
		JSONObject returnValue = new JSONObject();
		returnValue.put("lightLevel", wantedLightLevel);
		return returnValue;
	}

	@Override
	void elabResponse(String response) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		JSONObject returnValue = new JSONObject();
		returnValue.put("Sensor's name:", serviceName);
		returnValue.put("Wanted", getWanted());
		returnValue.put("Measured Values", getValues());
		return returnValue.toString();
	}
	
	private Float getNewLightLevel()
	{
		if(lightLevel > 1000)
			lightLevel = (float) 0;
		else {
			lightLevel += 19;
		}
		return lightLevel;
	}

	@Override
	JSONObject getNeeded_services() {
		// TODO Auto-generated method stub
		return null;
	}



}
