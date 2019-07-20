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

	double value = 30;
	boolean ascending = true;

	@RequestMapping("/people")
	public People data() {
		return new People(id, description, value, 0, 0);
	}

	@Scheduled(fixedDelay = 100)
	public void update() {
		if (ascending)
			value += 1;
		if (!ascending)
			value -= 1;
		if (value == 10 || value == 60) {
			ascending = !ascending;
		}
	}
}
