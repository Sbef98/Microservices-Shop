package com.gk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
	WeatherService weatherService;

	@Value("${service.id}")
	private String id;
	@Value("${service.description}")
	private String description;

	public ServiceController(WeatherService ws) {
		this.weatherService = ws;
	}

	@RequestMapping("/data")
	public Weather weather() {
		Weather currentWeather = weatherService.getWeather();
		currentWeather.setId(id);
		currentWeather.setDescription(description);
		return currentWeather;
	}
}
