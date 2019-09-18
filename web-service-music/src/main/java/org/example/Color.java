package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Color extends ServiceController{

	private int active_color = 1;
	
	@Override
	public void addDataType() {
		Communication.registerNewType("color", url);
		System.out.println("Registered color type!");
		
	}

	@Override
	public String getGetMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getValues() {
		JSONArray val = new JSONArray();
		if(active_color == 0 )
			val.put("pink");
		if(active_color == 1 )
			val.put("yellow");
		if(active_color == 2)
			val.put("red");
		if(active_color == 3)
			val.put("orange");
		if(active_color == 4)
			val.put("purple");
		if(active_color == 5)
			val.put("Blue");
		return new JSONObject().put("color", val);
	}

	@Override
	public JSONObject getWanted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getWorkspaces() {
		return new JSONArray().put("color");
	}

	@Override
	public boolean isSensor() {
		// TODO Auto-generated method stub
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
			active_color = (int) responseValue.getDouble(0);
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
