package org.example;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightActuator extends ServiceController
{
	private Integer lightLevel = 501;
	private Integer switchLevel = 500; //under 500 the lights are switched on
	private char lightSwitch = 0;
	
	@PostConstruct
	public void addDatatType() {
		System.out.println("Registering SwitchOn type at " + url);
		Communication.registerNewType("SwitchOn", url);

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
		returnValue.put("light", values);
		
		values = new JSONArray();
		values.put(lightSwitch);
		returnValue.put("SwitchOn", values);
		return returnValue;
	}

	@Override
	JSONObject getWanted() {
		//TODO Auto-generated method stub
		return null;
	}

	@Override
	void elabResponse(String response) 
	{
		JSONArray responseValue;
		try{
			responseValue = new JSONArray(response);
		}catch(JSONException e) {
			System.out.println(e);
			System.out.println(response);
			return;
		}
		try {
			lightLevel = (int) responseValue.getDouble(0);
		}catch(JSONException e) {
			System.out.println(e);
			return;
		}
		if(lightLevel > 500)
			lightSwitch = 1;
		if(lightLevel < 500)
			lightSwitch = 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	JSONArray getWorkspaces() {
		JSONArray returnValue = new JSONArray();
		returnValue.put("light");
		return returnValue;
	}

	@Override
	boolean isSensor() {
		return false;
	}

}
