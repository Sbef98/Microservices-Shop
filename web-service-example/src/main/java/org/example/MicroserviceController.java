package org.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public interface MicroserviceController 
{	
	
	 @PostConstruct
	 public void addDataType();
	 
	 public String getGetMapping();
	 
	 public String getPutMapping();
	 
	 public JSONObject getValues();
	 
	 public JSONObject getWanted();
	 
	 public JSONArray getWorkspaces();
	 
	 public boolean isSensor();

	 public void update();
	 	 
	 public String toString();
	 
	 @PreDestroy
	 public void end();
}
