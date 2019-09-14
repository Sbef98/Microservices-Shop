package com.example.DecisionServiceSte;

import java.util.Date;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import history.DatabaseConnection;
import history.HistoryTracker;

/**
 * @author Stefano Lugli;
 */
@RestController
public class BIOS { // basic input output service (nice joke i know)
	Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>> availableServices = new Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>>();
	/*
	 * Using HASHTABLES for thread safety
	 * This hastable uses the groupid of each service as key. The values is itself an hastable composed of every service
	 * that is part of the group. Its key will be the services' name and its value the ServiceDetailsRequestModel 
	 */

	@Autowired
	public BIOS() 
	{
		// TODO servicesHistory = new File("servicesHistory.JSON"); //Used just for
		// personal Info
		// or maybe a tiny data base
	}

	@PostConstruct
	public void init() 
	{
		DatabaseConnection.DBConnection();
	}

	@GetMapping(value = "get-available-services", produces = "application/json")
	public String getConnectionsInfo() 
	{	
		JSONObject returnValue = new JSONObject();
		for(String key : availableServices.keySet()) {
			//Iterate through all the groups
			JSONObject thisGroupValue = new JSONObject();
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices = availableServices.get(key); 
			for(String key2 : groupAvailableServices.keySet())
				//Iterate trough all the services in a group
				thisGroupValue.put(key2, groupAvailableServices.get(key2).getServiceData());
				//Key2 is the service name
			returnValue.put(key, thisGroupValue);
			//key is the groupID
		}
		return returnValue.toString();
	}

	/*@GetMapping(value = "get-active-connections", produces = "application/json")
	public JSONObject getActiveConnections()
	{
		JSONObject returnValue = new JSONObject();
		for(String key : availableServices.keySet()) {
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices = availableServices.get(key); 
			for(String key2 : groupAvailableServices.keySet()) {
				returnValue.
				thisGroupValue.put(key2, groupAvailableServices.get(key2).getServiceData());
			}
				//Key2 is the service name
			//key is the groupID
		}
		return returnValue;
	}*/
	/*TODO another getMapping for the database. Just incase*/
	@PutMapping(value = "put", produces = "application/json") // update services data
	public String updateService(@RequestParam(value = "serviceName") String serviceName, @RequestBody ServiceDetailsRequestModel requestServiceDetails) 
	{	
		requestServiceDetails.setName(serviceName);
		requestServiceDetails.setLastUpdate(new Date());
		System.out.println(requestServiceDetails.getServiceData());
		if(requestServiceDetails.isClosed() == true) { //TODO check if using delete mapping may be better
			try {
				availableServices.get(requestServiceDetails.getGroupID()).remove(serviceName);
				if(availableServices.get(requestServiceDetails.getGroupID()).size() == 0) // IF the group is empty
					availableServices.remove(requestServiceDetails.getGroupID()); //delete it from the map!
				return "ok";
			} catch(NullPointerException e) {
				return "error";
			}
		}
		//I make a service ID using the used URI and the port, so that i get a unique id to use in the map.
		Integer serviceID = new String(requestServiceDetails.getURI() + requestServiceDetails.getPort() + serviceName).hashCode();
		Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices;
		try{
			groupAvailableServices = availableServices.get(requestServiceDetails.getGroupID());
			//The group is already available in the services list
		}catch(NullPointerException e){
			System.out.print(e);
			System.out.println(" won't be able to use this service's informations!");
			return "error";
		}
		/*
		 * The key previously was the service name. That prevented any user to use the same name for multiple 
		 * services inside the same group. It should have not been a problem, but since humans made mistakes
		 * we choosed to use an id based on the URI+PORT+SERVICENAME combination hash. There could not be more than one 
		 * service using the same uri and port! 
		 */
		
		/*First of all i check if there is already a service with the same service id. In that case, i save
		 * it in the history and add the  new values!
		 */
		try {
			ServiceDetailsRequestModel oldServiceDetails = groupAvailableServices.get(serviceID.toString());
			if(oldServiceDetails != null) {
				/*if it were null it'd mean there is no such service listed yet.*/
				HistoryTracker.storeProcedure(oldServiceDetails, serviceID.toString());
			}
			groupAvailableServices.put(serviceID.toString(), requestServiceDetails);
		}catch (NullPointerException e) {
			//It'd mean there is no hashtable for that groupID
			groupAvailableServices = new Hashtable<String, ServiceDetailsRequestModel>();
			//The group is not available in the services list, so we create a new one
			availableServices.put(requestServiceDetails.getGroupID(), groupAvailableServices);
			//And add it to the services list!
			groupAvailableServices.put(serviceName, requestServiceDetails);
		}
		
		String returnValue = requestServiceDetails.getNeeded_services() == null //If it's null it means it just wants to update the data on the decision service
				? new String(requestServiceDetails.toString()) // Returning the same string may be used to check that the communcation was correct!
				: DecisionMaker.takeDecision(requestServiceDetails, groupAvailableServices); // The way decision will be hadnled may vary

		System.out.print("Put request for service status update from: ");
		System.out.println(serviceName);
		System.out.println("Answer: " + returnValue);
		return returnValue;
	}

	// @GetMapping("get-history") TODO -> will return the services requests history
	// since a given date
	// The get mapping may be used by any kind of service or by the GUI
	@PreDestroy
	public void destroy() {
		// servicesHistory.close(); files do not close... ?
		// surveillance_thread.join(); (or kill it brutally who cares)
		// TODO close all the services
	}
}
