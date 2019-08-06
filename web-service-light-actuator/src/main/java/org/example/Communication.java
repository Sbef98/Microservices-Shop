package org.example;

import org.json.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
/*
 * response = Communication.put(url, serviceURI, servicePort, type, getGetMapping(), getPutMapping(),
 * 								groupID, description, getValues(), getWanted(), getNeeded_sensors());
 */
public class Communication {
	protected static String put(String url, String serviceURI, int serverPort, String type,
								String getMapping, String putMapping, String groupID, String description,
								JSONObject values, JSONObject wanted, JSONObject needed_sensors) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("URI", serviceURI);
		msg.put("port", serverPort);
		msg.put("type",type);
		msg.put("GetMapping", getMapping);
		msg.put("PutMapping", putMapping);
		msg.put("groupID", groupID);
		msg.put("description", description);
		msg.put("values", values.toString());
		msg.put("wanted", wanted.toString());
		msg.put("needed_sensors", needed_sensors);
		return sendJSONObject(msg,url);		
	}
	protected static void close(String url, String groupID) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("close", true);
		msg.put("groupID", groupID);
		sendJSONObject(msg, url);
	}
	
	private static String sendJSONObject(JSONObject msg, String url)
	{
		HttpResponse<String> response = Unirest.put(url) //Gotta understand this first
	            .header("Accept", "application/json")
	            .header("Content-Type", "application/json")
	            .body(msg.toString())   //"{\"port\":\"" + serverPort + "\", \"type\" :\"actuator\", \"data\":"+ new Light(id,description,value).toString() +", \"body\":\"This is the body\"}")
	            .asString();
		return response.getBody();
	}
}
