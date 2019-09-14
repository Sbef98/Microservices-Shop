package com.example.DecisionServiceSte;

import java.util.Date;

import org.json.JSONObject;

public class ServiceDetailsRequestModel 
{
	private String URI;
	private int port;
	private String get_mapping = null; //optional
	private String get_putting = null; //optional
	private String groupID;
	private String description;
	private JSONObject values;
	private JSONObject wanted;
	private JSONObject needed_services;  //Will be empty in case of a sensor
	private boolean closed = false;
	private Date lastUpdate;
	private String name;

	public ServiceDetailsRequestModel(String uRI, int port, String get_mapping, String get_putting, String groupID,
			String description, JSONObject values, JSONObject wanted, JSONObject needed_services, boolean closed) {
		super();
		URI = uRI;
		this.port = port;
		this.get_mapping = get_mapping;
		this.get_putting = get_putting;
		this.groupID = groupID;
		this.description = description;
		this.values = values;
		this.wanted = wanted;
		this.needed_services = needed_services;
		this.closed = closed;
		this.lastUpdate = new Date();
	}
	public ServiceDetailsRequestModel(String uRI, int port, String get_mapping, String get_putting, String groupID,
			String description, JSONObject values, JSONObject wanted, JSONObject needed_services, boolean closed, String name) {
		super();
		URI = uRI;
		this.port = port;
		this.get_mapping = get_mapping;
		this.get_putting = get_putting;
		this.groupID = groupID;
		this.description = description;
		this.values = values;
		this.wanted = wanted;
		this.needed_services = needed_services;
		this.closed = closed;
		this.lastUpdate = new Date();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
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
		System.out.println(values);
		this.values = values;
	}
	public JSONObject getWanted() {
		return wanted;
	}
	public void setWanted(JSONObject  wanted) {
		System.out.println(wanted);
		this.wanted = wanted;
	}
	public JSONObject getNeeded_services() {
		return needed_services;
	}
	public void setNeeded_services(JSONObject needed_services) {
		System.out.println(needed_services);
		this.needed_services = needed_services;
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
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public Date getLastUpdateDate() {
		return lastUpdate;
	}
	public JSONObject getServiceData()
	{
		JSONObject returnValue = new JSONObject();
		returnValue.put("Name:", name);
		returnValue.put("URI", URI);
		returnValue.put("port", port);
		returnValue.put("GetMapping", getGet_mapping());
		returnValue.put("PutMapping", getGet_putting());
		returnValue.put("lastUpdate", lastUpdate);
		returnValue.put("description", description);
		returnValue.put("values", values);
		returnValue.put("wanted", wanted);
		returnValue.put("needed_services", needed_services);
		return returnValue;
	}
	@Override
	public String toString() {
		return getServiceData().toString(); 
	}
	
}
