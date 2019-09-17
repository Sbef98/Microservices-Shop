package org.example;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Temperature extends ServiceController{

	private Double active_value = 19.0;
	private Double active_value_2 = 19.0;
	private Double wanted_value = 21.0;
	
	@Override
	@PostConstruct
	public void addDataType() {
		Communication.registerNewType("temperature", url);
		System.out.println("Registered temperature!");
	}

	@GetMapping(value = "/temp", produces = "application/json")
	public String getTemp()
	{
		JSONObject msg = new JSONObject();
		msg.put("uri", serviceURI);
		msg.put("port", servicePort);
		msg.put("get_mapping", getGetMapping());
		msg.put("put_mapping", getPutMapping());
		msg.put("groupID", groupID);
		msg.put("description", description);
		if(getValues() != null)
			msg.put("values", getValues().toString());
		if(getWanted() != null)
			msg.put("wanted", getWanted().toString());
		if(getWorkspaces() != null)
			msg.put("workspaces", getWorkspaces().toString());
		msg.put("isSensor", isSensor());
		return msg.toString();
	}
	
	@Override
	public String getGetMapping() {
		return "/temp";
	}

	@Override
	public String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getValues() {
		JSONArray values = new JSONArray().put(active_value);
		values.put(active_value_2);
		return new JSONObject().put("temperature", values);
	}

	@Override
	public JSONObject getWanted() {
		return new JSONObject().put("temperature", wanted_value); 
	}

	@Override
	public JSONArray getWorkspaces() {
		return new JSONArray().put("temperature");
	}

	@Override
	public boolean isSensor() {
		return true;
	}

	@Override
	protected void elabResponse(String response) {
		/*Will simulate the temperature change due to the set value in the actuator!*/
		JSONArray responseValue;
		Double w;
		try{
			responseValue = new JSONArray(response);
		}catch(JSONException e) {
			System.out.println(e);
			System.out.println(response);
			return;
		}
		try {
			w = responseValue.getDouble(0);
		}catch(JSONException e) {
			System.out.println(e);
			return;
		}
		if(w > active_value) {
			active_value += w/100;
			active_value_2 = active_value;
		}
		else {
			active_value -= w/100;
			active_value_2 = active_value;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
