package example.service;

import org.json.JSONObject;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class Communication {
	protected static String put(String url, String serviceURI, String serverPort, String type, SensorController sensor) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("URI", serviceURI);
		msg.put("port", serverPort);
		msg.put("type",type);
		msg.put("data", sensor.toString());
		//optional
		msg.put("getMapping", "NULL");
		msg.put("putMapping", "NULL");
		HttpResponse<String> response = Unirest.put(url) //Gotta understand this first
	            .header("Accept", "application/json")
	            .header("Content-Type", "application/json")
	            .body(msg.toString())   //"{\"port\":\"" + serverPort + "\", \"type\" :\"sensor\", \"data\":"+ new Light(id,description,value).toString() +", \"body\":\"This is the body\"}")
	            .asString();
		return response.getBody();
	}
	protected static String put(String url, String serviceURI, String serverPort, String get_mapping, String type, SensorController sensor) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("URI", serviceURI);
		msg.put("port", serverPort);
		msg.put("type",type);
		msg.put("data", sensor.toString());
//		Optional
		msg.put("getMapping", get_mapping);
		msg.put("putMapping", "NULL");
		HttpResponse<String> response = Unirest.put(url) //Gotta understand this first
	            .header("Accept", "application/json")
	            .header("Content-Type", "application/json")
	            .body(msg.toString())   //"{\"port\":\"" + serverPort + "\", \"type\" :\"sensor\", \"data\":"+ new Light(id,description,value).toString() +", \"body\":\"This is the body\"}")
	            .asString();
		return response.getBody();
	}
	protected static String put(String url, String serviceURI, String serverPort, String put_mapping, String get_mapping, String type, SensorController sensor) throws UnirestException
	{
		JSONObject msg = new JSONObject();
		msg.put("URI", serviceURI);
		msg.put("port", serverPort);
		msg.put("type",type);
		msg.put("data", sensor.toString());
//		Optional
		msg.put("getMapping", get_mapping);
		msg.put("putMapping", put_mapping);
		HttpResponse<String> response = Unirest.put(url) //Gotta understand this first
	            .header("Accept", "application/json")
	            .header("Content-Type", "application/json")
	            .body(msg.toString())   //"{\"port\":\"" + serverPort + "\", \"type\" :\"sensor\", \"data\":"+ new Light(id,description,value).toString() +", \"body\":\"This is the body\"}")
	            .asString();
		return response.getBody();
	}
}
