package com.example.DecisionServiceSte;


import history.DatabaseConnection;
import history.HistoryTracker;

import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stefano Lugli;
 */
@RestController
@EnableScheduling
public class BIOS { // basic input output service (nice joke i know)
	Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>> availableServices = new Hashtable<String, Hashtable<String, ServiceDetailsRequestModel>>();
	/*
	 * Using HASHTABLES for thread safety
	 * This hastable uses the groupid of each service as key. The values is itself an hastable composed of every service
	 * that is part of the group. Its key will be the services' name and its value the ServiceDetailsRequestModel 
	 */

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

	@GetMapping(value = "get-available-connections", produces = "application/json")
	public String getActiveConnections()
	/*
	 * This function returns all the available services with their informations.
	 * It may be used by other services who need a direct connection or just to
	 * have a quick look at the overall situation without needing the whole values report!
	 */
	{
		JSONObject returnValue = new JSONObject();
		/*Let's go through all the groups*/
		for(String groupID : availableServices.keySet()) {
			/*And now through all the services in a group*/
			for(String serviceID : availableServices.get(groupID).keySet()) {
				JSONObject serviceValues = new JSONObject();
				serviceValues.put("groupID", availableServices.get(groupID).get(serviceID).getGroupID());
				serviceValues.put("URI", availableServices.get(groupID).get(serviceID).getURI());
				serviceValues.put("Port", availableServices.get(groupID).get(serviceID).getPort());
				serviceValues.put("Name", availableServices.get(groupID).get(serviceID).getName());
				serviceValues.put("get_mapping", availableServices.get(groupID).get(serviceID).getGet_mapping());
				serviceValues.put("put_mapping", availableServices.get(groupID).get(serviceID).getPut_mapping());
				serviceValues.put("Description:", availableServices.get(groupID).get(serviceID).getDescription());
				returnValue.put(serviceID, serviceValues);
			}
		}
		return returnValue.toString();
	}
	
	@PutMapping(value = "register-new-data-type", produces = "application/json")
	public String registerDataType(@RequestParam(value = "data-type") String type)
	{
			System.out.println("Registering new type: " + type);
			JSONObject returnValue = new JSONObject();
			try {
				DatabaseConnection.insertDataType(type);
				returnValue.put("response", "ok");
			} catch(SQLException e) {
				returnValue.put("response", "Database error");
			}
			return returnValue.toString();
	}
	
	/*TODO another getMapping for the database. Just incase*/
	
	@PutMapping(value = "put", produces = "application/json") // update services data
	public String updateService(@RequestParam(value = "serviceName") String serviceName, @RequestBody ServiceDetailsRequestModel requestServiceDetails) 
	{	
		/*First of all i check the datatypes if the datatypes passed are valid.*/
		for(String value : requestServiceDetails.getValues().keySet()) {
			/*The key of each value is its DataType*/
			if(DatabaseConnection.checkDataType(value) == false) {
				System.out.println("Tried to insert unknown data type!");
				return new String("Error, unknown data type:  " + value );
			}
		}
		
		requestServiceDetails.setName(serviceName);
		requestServiceDetails.setLastUpdate(new Date());
		System.out.println("I received: ");
		System.out.println(requestServiceDetails.getServiceData());
		
		//I make a service ID using the used URI and the port, so that i get a unique id to use in the map.
		String serviceID = ((Integer) new String(requestServiceDetails.getURI() + requestServiceDetails.getPort() + serviceName).hashCode()).toString();
		
		//First of all i check if the service is closing. In that case, i delete it from the available services list.
		if(requestServiceDetails.isClosed() == true) {
			try {
				HistoryTracker.storeProcedure(availableServices.get(requestServiceDetails.getGroupID()).get(serviceID), serviceID);
				availableServices.get(requestServiceDetails.getGroupID()).remove(serviceID);
				if(availableServices.get(requestServiceDetails.getGroupID()).size() == 0) // IF the group is empty
					availableServices.remove(requestServiceDetails.getGroupID()); //delete it from the map!
				return "ok";
			} catch(NullPointerException e) {
				return "error";
			}
		}
		
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
			ServiceDetailsRequestModel oldServiceDetails = groupAvailableServices.get(serviceID);
			if(oldServiceDetails != null) {
				/*if it were null it'd mean there is no such service listed yet.*/
				HistoryTracker.storeProcedure(oldServiceDetails, serviceID.toString());
			}
			groupAvailableServices.put(serviceID, requestServiceDetails);
		}catch (NullPointerException e) {
			//It'd mean there is no hashtable for that groupID
			groupAvailableServices = new Hashtable<String, ServiceDetailsRequestModel>();
			//The group is not available in the services list, so we create a new one
			availableServices.put(requestServiceDetails.getGroupID(), groupAvailableServices);
			//And add it to the services list!
			groupAvailableServices.put(serviceID, requestServiceDetails);
		}
		
		String returnValue = requestServiceDetails.getNeeded_services() == null //If it's null it means it just wants to update the data on the decision service
				? new String(requestServiceDetails.toString()) // Returning the same string may be used to check that the communcation was correct!
				: DecisionMaker.takeDecision(requestServiceDetails, groupAvailableServices); // The way decision will be hadnled may vary

		System.out.print("Put request for service status update from: ");
		System.out.println(serviceID);
		System.out.println("Answer: " + returnValue);
		return returnValue;
	}
	
	@Scheduled(fixedDelay = 600000)
	/*Everyten minutes*/
	public void garbageCollector(){
		/*
		 * This function autocloses the services inside of available services in case they do not update for more than 1 hour
		 * and cleans the database deleting the records after a set amount of time
		 */
		Set<String> groupIDS = availableServices.keySet();
	    for(String groupID : groupIDS) { 
	    	/*Going through all the groups
	    	 */
	       Set<String> serviceIDS = availableServices.get(groupID).keySet();
	       for(String serviceID : serviceIDS) {
	    	   /*Going through all the services inside of a group*/
	    	   if(availableServices.get(groupID).get(serviceID).getLastUpdateDate().getTime() - (new Date()).getTime() >= (60*60*1000) ) {
	    		   /*If the last update was more than 1 hours ago i delete it ...*/
	    		   HistoryTracker.storeProcedure(availableServices.get(groupID).get(serviceID), serviceID);
	    		   availableServices.get(groupID).remove(serviceID);
	    		   /*Last but not least, i check if the group is empty or not. In case it were empty, i delete the group too!*/
	    		   if(availableServices.get(groupID).size() == 0)
	    			   availableServices.remove(groupID);
	    	   }
	       }
	    }
	    
	    /* Now i call the function from the package history that will take care of deleting the old records*/
	    DatabaseConnection.garbage();
	}
	
	@PreDestroy
	public void destroy() {
		//In the predestroy i need to save in the history all the available service
		Set<String> groupIDS = availableServices.keySet();
	    for(String groupID : groupIDS) { 
	       Set<String> serviceIDS = availableServices.get(groupID).keySet();
	       for(String serviceID : serviceIDS)
	    	   HistoryTracker.storeProcedure(availableServices.get(groupID).get(serviceID), serviceID);
	    } 
	}
}
