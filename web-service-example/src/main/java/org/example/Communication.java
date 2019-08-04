package org.example;

import org.json.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
/*
 * Communication.put(url, serviceURI, servicePort, type, getGetMapping(), getPutMapping(),
					 groupID, description, getValues(), getWanted(), getNeeded_sesnors());
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
		msg.put("values", values);
		msg.put("wanted", wanted);
		msg.put("needed_sensors", needed_sensors);
		HttpResponse<String> response = Unirest.put(url) //Gotta understand this first
	            .header("Accept", "application/json")
	            .header("Content-Type", "application/json")
	            .body(msg.toString())   //"{\"port\":\"" + serverPort + "\", \"type\" :\"actuator\", \"data\":"+ new Light(id,description,value).toString() +", \"body\":\"This is the body\"}")
	            .asString();
		return response.getBody();
	}
}
