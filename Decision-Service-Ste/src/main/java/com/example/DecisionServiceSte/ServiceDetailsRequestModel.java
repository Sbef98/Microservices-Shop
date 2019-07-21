package com.example.DecisionServiceSte;

import java.util.Date;

import org.json.JSONObject;

public class ServiceDetailsRequestModel {
	private String port;
	private String type; 
	private String lastDataUpdate;
	private Date lastUpdate;
	private JSONObject data;
	
	public JSONObject getData() {
		return data;
	}
	public void setData(String data) {
		this.data = this.data.getJSONObject(data); //NEED TO BE CHACKED!
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
		this.type = type;
	}	
	public String getLastDataUpdate() {
		return lastDataUpdate;
	}
	public void setLastDataUpdate(String lastDataUpdate) {
		lastUpdate = new Date();
		this.lastDataUpdate = lastDataUpdate;
	}
	public Date getLatUpdateDate() {
		return lastUpdate;
	}
	@Override
	public String toString() {
		return "{\"port\":\"" + port + "\", \"type\":\"" + type + "\", \"lastDataUpdate\":\"" + lastDataUpdate
				+ "\", \"lastUpdate\":" + lastUpdate + "\", \"data\":\"" + data + "\"}"; //Should be kinda JSON formatted
	}
	
}
