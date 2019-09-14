package org.example;

import org.json.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
/*
 * response = Communication.put(url, serviceURI, servicePort, type, getGetMapping(), getPutMapping(),
 * 								groupID, description, getValues(), getWanted(), getNeeded_sensors());
 */
/**
 *Class to describe HTTP communication model between services of the system.
 */
public class Communication {
	/**
	 * Function to process the PUT request building the JSONObject 
	 * 
	 * @param url Decision service's url: the target of PUT message
	 * @param serviceURI address of the actual service
	 * @param serverPort port listening at "serviceURI" address
	 * @param type type of service: sensor or actuator
	 * @param getMapping GET functions list to use with this service to execute a data request. Optional
	 * @param putMapping PUT functions list of this service. Optional
	 * @param groupID service belonging group
	 * @param description general info
	 * @param values Actual measured values
	 * @param wanted Ideal target values suggested
	 * @param needed_sensors USED ONLY FOR ACTUATORS: model to describe the comparison beetwen which services are needed to execute actions   
	 * @return TO SENSORS: resends the received message, TO ACTUATOR: return a JSONObject with values' delta
	 * @throws UnirestException
	 */
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
		if(wanted != null)
			msg.put("wanted", wanted.toString());
		if(needed_sensors != null)
		msg.put("needed_sensors", needed_sensors.toString());
		return sendJSONObject(msg,url);		
	}
	
	/**
	 * Function to tell to the decision service that this service will be shut down
	 * 
	 * @param url target address of message: Decision service URL
	 * @param groupID service belonging group
	 * @throws UnirestException
	 */
	protected static void close(String url, String groupID) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("close", true);
		msg.put("groupID", groupID);
		sendJSONObject(msg, url);
	}
	
	/**
	 * Internal service function to execute a HTTP PUT request on a target URL
	 * 
	 * @param msg JSONObject to send
	 * @param url target address
	 * @return body of the PUT response message
	 */
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