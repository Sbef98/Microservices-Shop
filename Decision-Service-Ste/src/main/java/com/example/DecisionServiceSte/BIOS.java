package com.example.DecisionServiceSte;

import java.io.IOException;
import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Stefano Lugli;
 */
@RestController
public class BIOS { //basic input output service (nice joke i know)
	Hashtable<String,ServiceDetailsRequestModel> availableServices = new Hashtable<String, ServiceDetailsRequestModel>();
	//Hashtable has a big overhead due to sync features, but it's thread safe.
	//File servicesHistory; Files in java suck. Check it later  
	
	@Autowired
	public BIOS() throws IOException{
		//servicesHistory = new File("servicesHistory.JSON");  //Used just for personal Info
	}
	@PostConstruct
	public void init() {
		//The surveillance thread used to check the correct services updates will be starting from here
		//Not sure if i actually do need this
	}
	
	@PutMapping("put") //update services data
	public String updateService(@RequestParam(value="serviceName") String serviceName, @RequestBody ServiceDetailsRequestModel requestServiceDetails) {
		String returnValue = new String(requestServiceDetails.toString());
		availableServices.put(serviceName, requestServiceDetails);
		//availableServices.get(serviceName); //Write the old ServiceName's value in the service history.
		System.out.print("Put request for service status update from: ");
		System.out.println(serviceName);
		System.out.println("Message: " + returnValue);
		return returnValue; 
	}
	//@GetMapping("get-history") TODO -> will return the services requests history since a given date
	@PreDestroy
	public void destroy() {
		//servicesHistory.close(); files do not close... like what? 
		//surveillance_thread.join(); (or kill it brutally who cares)
	}
}
