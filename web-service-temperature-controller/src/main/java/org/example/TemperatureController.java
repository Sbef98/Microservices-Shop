package org.example;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController extends ServiceController{

	private Double set_temperature = 22.2;
	private Double wanted_temperature = 22.2;
	
	@GetMapping(value = "/set", produces = "application/json")
	public String getSetTemp()
	{
		JSONObject msg = new JSONObject();
		msg.put("set", set_temperature.toString());
		return msg.toString();
	}
	
	@Override
	@PostConstruct
	public void addDataType() {
		Communication.registerNewType("temperature", url);
		System.out.println("Registered temperature!");
	}

	@Override
	public String getGetMapping() {
		return "/set";
	}

	@Override
	public String getPutMapping() {
		return null;
	}

	@Override
	public JSONObject getValues() {
		JSONObject values = new JSONObject();
		JSONArray value = new JSONArray().put(set_temperature);
		values.put("temperature", value);
		return values;
	}

	@Override
	public JSONObject getWanted() {
		return new JSONObject().put("temperature", wanted_temperature);
	}

	@Override
	public JSONArray getWorkspaces() {
		return new JSONArray().put("temperature");
	}

	@Override
	public boolean isSensor() {
		return false;
	}

	@Override
	protected void elabResponse(String response) {
		JSONArray responseValue;
		try{
			responseValue = new JSONArray(response);
		}catch(JSONException e) {
			System.out.println(e);
			System.out.println(response);
			return;
		}
		try {
			set_temperature  = responseValue.getDouble(0);
		}catch(JSONException e) {
			System.out.println(e);
			return;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
