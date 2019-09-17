package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightActuator extends ServiceController
{
	private float lightLevel = 501;
	private float switchLevel = 500; //under 500 the lights are switched on
	private boolean lightSwitch = false;
	
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
		JSONObject responseValue;
		try{
			responseValue = new JSONObject(response);
		}catch(JSONException e) {
			System.out.println(e);
			System.out.println(response);
			return;
		}
		JSONObject lightServiceValue;
		try {
			lightServiceValue = responseValue.getJSONObject("lightService");
		}catch(JSONException e)
		{
			System.out.println(e);
			return;
		}
		try {
			lightLevel += lightServiceValue.getInt("lightLevel");
		}catch(JSONException e) {
			System.out.println(e);
			return;
		}
		if(lightLevel > 500)
			lightSwitch = true;
		if(lightLevel < 500)
			lightSwitch = false;
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

}
