package com.example.DecisionServiceSte;

import java.util.Date;

public class ServiceDetailsRequestModel {
	private String serviceName;
	private String type; 
	private String lastDataUpdate;
	private Date lastUpdate;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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
		return "ServiceDetailsRequestModel [serviceName=" + serviceName + ", lastDataUpdate=" + lastDataUpdate
				+ ", lastUpdate=" + lastUpdate + "]";
	}
	
}
