package com.example.DecisionServiceSte;

import java.util.Date;

import org.json.JSONObject;

public class ServiceDetailsRequestModel 
{
	private String URI;
	private String port;
	private String type;
	private String get_mapping = "NULL"; //optional
	private String get_putting = "NULL"; //optional
	private String groupID;
	private String description;
	private JSONObject values;
	private JSONObject wanted;
	private JSONObject needed_sensors;  //Will be empty in case of a sensor

	
	private Date lastUpdate;
	
	
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONObject getValues() {
		return values;
	}
	public void setValues(JSONObject values) {
		this.values = values;
	}
	public JSONObject getWanted() {
		return wanted;
	}
	public void setWanted(JSONObject wanted) {
		this.wanted = wanted;
	}
	public JSONObject getNeeded_sensors() {
		return needed_sensors;
	}
	public void setNeeded_sensors(JSONObject needed_sensors) {
		this.needed_sensors = needed_sensors;
	}
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
	public JSONObject getServiceData()
	{
		JSONObject returnValue = new JSONObject();
		returnValue.put("URI", URI);
		returnValue.put("port", port);
		returnValue.put("type", type);
		returnValue.put("lastUpdate", lastUpdate);
		returnValue.put("values", values);
		returnValue.put("wanted", wanted);
		returnValue.put("needed_sensors", needed_sensors);
		return returnValue;
	}
	@Override
	public String toString() {
		return getServiceData().toString(); 
	}
	
}
