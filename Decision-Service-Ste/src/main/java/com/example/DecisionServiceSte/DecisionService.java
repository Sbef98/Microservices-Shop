package com.example.DecisionServiceSte;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.UserDetailsRequestModel;

/**
 * @author Stefano Lugli;
 */
@RestController
@RequestMapping("put")
public class DecisionService {
	private static final Logger logger = Logger.getLogger(DecisionService.class);
	@PutMapping("?{servicePort}")
	public String updateService(@PathVariable String servicePort, @RequestBody ServiceDetailsRequestModel requestServiceDetails) {
		String returnValue = new String(requestServiceDetails.toString());
		System.out.print("Put request for service status update from: ");
		System.out.println(servicePort);
		System.out.println("Message: " + returnValue);
		return returnValue;
	}
}
