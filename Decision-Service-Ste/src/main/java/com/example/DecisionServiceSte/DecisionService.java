package com.example.DecisionServiceSte;

import java.util.Hashtable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Stefano Lugli;
 */
@RestController
public class DecisionService {
	Hashtable<String,ServiceDetailsRequestModel> availableServices = new Hashtable<String, ServiceDetailsRequestModel>();
	@PutMapping("put")
	public String updateService(@RequestParam(value="port") String servicePort, @RequestBody ServiceDetailsRequestModel requestServiceDetails) {
		String returnValue = new String(requestServiceDetails.toString());
		availableServices.put(servicePort, requestServiceDetails);
		System.out.print("Put request for service status update from: ");
		System.out.println(servicePort);
		System.out.println("Message: " + returnValue);
		return returnValue;
	}
	
}
