/**
 * 
 */
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @author Leonardo;
 */
@RestController
public class TimeController {
	
	@GetMapping("/get-time")
	  public String getTime() {
		Date x = new Date();
		return x.toString();
	  }
	
}
