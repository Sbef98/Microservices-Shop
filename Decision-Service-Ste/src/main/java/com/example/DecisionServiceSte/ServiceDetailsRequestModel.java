package com.example.DecisionServiceSte;

import java.util.Date;

import org.json.JSONObject;

public class ServiceDetailsRequestModel {
	private String port;
	private String type;
	private Date lastUpdate;
	private String data;
	private String URI;
	private String get_mapping = "NULL"; //optional
	private String get_putting = "NULL"; //optional
	
	
	public String getGet_mapping() {
		return get_mapping;
	}
	public void setGet_mapping(String get_mapping) {
		this.get_mapping = get_mapping;
	}
	public String getGet_putting() {
		return get_putting;
	}
	public void setGet_putting(String get_putting) {
		this.get_putting = get_putting;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) { //Self generated. I don't know why the u is lower case
		URI = uRI;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		lastUpdate = new Date();
		this.data = data; //NEED TO BE CHACKED!
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type.compareToIgnoreCase("actuator") != 0 && type.compareToIgnoreCase("sensor") != 0) {
			throw new RuntimeException("Available types of services are either sensor or actuator!");
		}
		this.type = type;  //"actuator" or "sensor"
	}	

	public Date getLastUpdateDate() {
		return lastUpdate;
	}
	@Override
	public String toString() {
		JSONObject returnValue = new JSONObject();
		returnValue.put("URI", URI);
		returnValue.put("port", port);
		returnValue.put("type", type);
		returnValue.put("lastUpdate", lastUpdate);
		returnValue.put("data", data);
		return returnValue.toString(); //"{\"port\":\"" + port + "\", \"type\":\"" + type + "\", \"lastUpdate\":" + lastUpdate + "\", \"data\":\"" + data + "\"}"; //Should be kinda JSON formatted
	}
	
}
