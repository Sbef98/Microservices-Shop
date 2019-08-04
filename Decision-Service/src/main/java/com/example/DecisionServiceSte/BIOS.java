package com.example.DecisionServiceSte;

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

/**
 * @author Stefano Lugli;
 */
@RestController
public class BIOS { // basic input output service (nice joke i know)
	Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>> availableServices = new Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>>();
	/*
	 * Using HASHTABLES for thread safety
	 * This hastable uses the groupid of each service as key. The vlues is itself an hastable composed of every sensor
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
		// possible TODO The surveillance thread used to check the correct services
		// updates will be starting from here
		// Not sure if i actually do need this
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

	@PutMapping(value = "put", produces = "application/json") // update services data
	public String updateService(@RequestParam(value = "serviceName") String serviceName, @RequestBody ServiceDetailsRequestModel requestServiceDetails) 
	{	
		if(requestServiceDetails.isClosed() == true) {
			try {
				availableServices.get(requestServiceDetails.getGroupID()).remove(serviceName);
				if(availableServices.get(requestServiceDetails.getGroupID()).size() == 0) // IF the group is empty
					availableServices.remove(requestServiceDetails.getGroupID()); //delete it from the map!
				return "ok";
			}catch(NullPointerException e) {
				return "error";
			}
		}
		Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices;
		try{
			groupAvailableServices = availableServices.get(requestServiceDetails.getGroupID());
			//The group is already available in the services list
		}catch(NullPointerException e){
			groupAvailableServices = new Hashtable<String, ServiceDetailsRequestModel>();
			//The group is not available in the services list, so we create a new one
			availableServices.put(requestServiceDetails.getGroupID(), groupAvailableServices);
			//And add it to the services list!
		}
		groupAvailableServices.put(serviceName, requestServiceDetails);
		
		String returnValue = requestServiceDetails.getType().compareToIgnoreCase("sensor") == 0
				? new String(requestServiceDetails.toString()) // Returning the same string may be used to check that the communcation was correct!
				: DecisionMaker.takeDecision(requestServiceDetails, groupAvailableServices); // The way decision will be
																						// hadnled may vary
		// TODO availableServices.get(serviceName); //Write the old ServiceName's value
		// in the service history.
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
