package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightActuator extends ServiceController
{
	private float lightLevel;
	private float switchLevel = 500; //under 500 the lights are switched on
	NeededServices needed_services;
	
	public LightActuator()
	{
		super();
		needed_services = new NeededServices("lightService", "lighLevel", "*");
	}
	
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
	JSONObject getNeeded_services() {
		return needed_services.toJSONObject();
	}


	@Override
	void elabResponse(String response) 
	{
		//System.out.println(response);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
