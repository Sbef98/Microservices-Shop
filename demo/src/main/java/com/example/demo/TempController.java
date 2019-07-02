package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leonardo;
 */
@RestController
public class TempController {
	
	@GetMapping("/get-temp")
	  public String getTime() {
		Integer x = 32;
		return x.toString();
	  }
	
}
