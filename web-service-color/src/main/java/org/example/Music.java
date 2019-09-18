package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Music extends ServiceController{

	private int active_music = 1;
	
	@Override
	public void addDataType() {
		Communication.registerNewType("music", url);
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
		if(active_music == 0 )
			val.put("Vivaldi");
		if(active_music == 1 )
			val.put("Mozart");
		if(active_music == 2)
			val.put("Indie");
		if(active_music == 3)
			val.put("Pop");
		if(active_music == 4)
			val.put("Rap");
		if(active_music == 5)
			val.put("Trap");
		if(active_music == 6 )
			val.put("Rock");
		if(active_music == 7 )
			val.put("Timmy Trumpet");
		if(active_music == 8)
			val.put("Queen");
		if(active_music == 9)
			val.put("Magic Jam");
		if(active_music == 10)
			val.put("Loltr");
		return new JSONObject().put("music", val);
	}

	@Override
	public JSONObject getWanted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getWorkspaces() {
		return new JSONArray().put("music");
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
			active_music = (int) responseValue.getDouble(0);
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
