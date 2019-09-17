package org.example;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Temperature extends ServiceController{

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
		
	}

	@Override
	public String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getWanted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getWorkspaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSensor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void elabResponse(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
