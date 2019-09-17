package org.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Class to declare fundamental core method to implement a service
 * working correctly with Decision Service;
 * Is necessary to add @RestController annotation in the service class created;
 */
public interface MicroserviceController 
{	
	
	 /**
	 * Before sending any input to Decision Service, is necessary to
	 * let him know that data, this function can use static method 
	 * "registerNewType" from Communication class to add data types inside
	 * Decision Service Database. Pay attention about DataName spelling;
	 */
	@PostConstruct
	 public void addDataType();
	 
	 /**
	 * GET mapping is the final extension of service address where it is listening
	 * for a GET http request;
	 * @return  Final extension of service address;
	 */
	public String getGetMapping();
	 
	 /**
	 * PUT mapping is the final extension of service address where it is listening
	 * for a PUT http request;
	 * @return  Final extension of service address;
	 */
	public String getPutMapping();
	 
	 /**
	 * @return a JSONObject with a list of data types handled used as keys for Arrays of relative values; 
	 */
	public JSONObject getValues();
	 
	 /**
	 * @return a JSONObject with a list of data types handled used as keys for relative singles values;
	 */
	public JSONObject getWanted();
	 
	 /**
	 * Necessary to obtain from Decision Service a reference value; 
	 * @return a JSONArray with list of data type handled by this actuator;
	 */
	public JSONArray getWorkspaces();
	 
	 /**
	 * @return true if service is a sensor, false if it is an actuator;
	 */
	public boolean isSensor();

	 /**
	 * Function to put to Decision Service workspace;
	 */
	public void update();
	 	 
	 public String toString();
	 
	 /**
	 * Sending to Decision Service ending of this service process and communications;
	 */
	@PreDestroy
	 public void end();
}
