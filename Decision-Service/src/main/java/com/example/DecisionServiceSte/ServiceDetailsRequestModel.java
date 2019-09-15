package com.example.DecisionServiceSte;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceDetailsRequestModel 
{
	
	private int port;
	private String uri;
	private String get_mapping = null; //optional
	private String put_mapping = null; //optional
	private String groupID;
	private String description;
	private JSONObject values;
	private JSONObject wanted;
	private JSONArray workspaces;  //Will be empty in case of a sensor
	private boolean closed = false;
	private Date lastUpdate;
	private String name;

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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
		this.values = values;
	}
	public JSONObject getWanted() {
		return wanted;
	}
	public void setWanted(JSONObject  wanted) {
		this.wanted = wanted;
	}
	
	public JSONArray getWorkspaces() {
		return workspaces;
	}
	public void setWorkspaces(JSONArray workspaces) {
		this.workspaces = workspaces;
	}
	public String getGet_mapping() {
		return get_mapping;
	}
	public void setGet_mapping(String get_mapping) {
		this.get_mapping = get_mapping;
	}
	public String getPut_mapping() {
		return put_mapping;
	}
	public void setPut_mapping(String get_putting) {
		this.put_mapping = get_putting;
	}
	public String getURI() {
		return uri;
	}
	public void setURI(String uri) { //Self generated. I don't know why the u is lower case
		this.uri = uri;
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
		returnValue.put("uri", uri);
		returnValue.put("port", port);
		returnValue.put("GetMapping", getGet_mapping());
		returnValue.put("PutMapping", getPut_mapping());
		returnValue.put("lastUpdate", lastUpdate);
		returnValue.put("description", description);
		returnValue.put("values", values);
		returnValue.put("wanted", wanted);
		returnValue.put("workspaces", workspaces);
		return returnValue;
	}
	@Override
	public String toString() {
		return getServiceData().toString(); 
	}
	
}
