package com.gk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

@Service
@EnableScheduling
public class WeatherService {
	private static final String apiKey = "f9539f2de7f93bfe92b99206c5e65d27";
	private static final String cityId = "2853292"; // Plauen
	private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id={cityId}&APPID={key}";

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	private RestTemplate restTemplate;
	Weather currentWeather;

	public WeatherService(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
		retrieveWeather();
	}

	@Scheduled(fixedDelay = 60000)
	public void retrieveWeather() {
		logger.info("Requesting current weather for {}", cityId);
		currentWeather = restTemplate.getForObject(new UriTemplate(WEATHER_URL).expand(cityId, apiKey), Weather.class);
	}

	public Weather getWeather() {
		return currentWeather;
	}
}
