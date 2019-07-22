/**
 * Default package for this demo application
 */
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * Implementing a REST microservice using SPRING framework
 * to get time and date information using a HTTP protocol;
 * 
 * @author Leonardo
 */
@RestController
public class TimeController {
	
	/**
	 * Basic function that gets the time info from local
	 * machine and publish it
	 * 
	 * @return String The time and date
	 */
	@GetMapping("/get-time")
	  public String getTime() {
		Date x = new Date();
		return x.toString();
	  }
	
}
