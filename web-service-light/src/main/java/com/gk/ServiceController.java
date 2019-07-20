package com.gk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class ServiceController {
	@Value("${service.id}")
	private String id;
	@Value("${service.description}")
	private String description;

	double value = 4000;
	boolean ascending = true;

	@RequestMapping("/light")
	public Light data() {
		return new Light(id, description, value);
	}

	@Scheduled(fixedDelay = 100)
	public void update() {
		if (ascending)
			value += 25;
		if (!ascending)
			value -= 25;
		if (value == 3000 || value == 6000) {
			ascending = !ascending;
		}
	}
}
