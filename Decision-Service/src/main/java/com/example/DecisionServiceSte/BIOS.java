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
	Hashtable<String, ServiceDetailsRequestModel> availableServices = new Hashtable<String, ServiceDetailsRequestModel>();
	// Hashtable has a big overhead due to sync features, but it's thread safe.
	// TODO File servicesHistory; Files in java suck. Check it later

	@Autowired
	public BIOS() { // throws IOException{
		// TODO servicesHistory = new File("servicesHistory.JSON"); //Used just for
		// personal Info
		// or maybe a tiny data base
	}

	@PostConstruct
	public void init() {
		// possible TODO The surveillance thread used to check the correct services
		// updates will be starting from here
		// Not sure if i actually do need this
	}

	@GetMapping(value = "get-available-services", produces = "application/json")
	public String getConnectionsInfo() {
		JSONObject returnValue = new JSONObject();
		for(String key : availableServices.keySet()) {
			returnValue.put(key, availableServices.get(key).getServiceData());
		}
		return returnValue.toString();
	}

	@PutMapping(value = "put", produces = "application/json") // update services data
	public String updateService(@RequestParam(value = "serviceName") String serviceName,
			@RequestBody ServiceDetailsRequestModel requestServiceDetails) {
		availableServices.put(serviceName, requestServiceDetails);
		String returnValue = requestServiceDetails.getType().compareToIgnoreCase("sensor") == 0
				? new String(requestServiceDetails.toString())
				: // Returning the same string may be used to check that the communcation was
					// correct!
				DecisionMaker.takeDecision(requestServiceDetails, availableServices); // The way decision will be
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
