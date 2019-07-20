package com.gk;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JDialogFis;

public class DecisionService implements Runnable {
	private static final Logger logger = Logger.getLogger(DecisionService.class);
	ObjectMapper objectMapper;
	FIS fis;
	JDialogFis jdf;

	public DecisionService() {
		objectMapper = new ObjectMapper();
		fis = FIS.load("fcl/GK.fcl", true);
		jdf = new JDialogFis(fis, 1024, 768);

		// Show active fuzzy rules
		System.out.println(fis);
	}

	public Map<String, Object> connectToService(String url)
			throws JsonParseException, JsonMappingException, IOException, UnirestException {
		String json = Unirest.get(url).asString().getBody();
		System.out.println(json);
		return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});
	}

	public void run() {
		Map<String, Object> map;
		String url;

		while (true) {
			// weather service
			url = "http://localhost:8080/data";
			try {
				map = connectToService(url);
				fis.setVariable("temp_outside", (Double) map.get("temperature"));
				fis.setVariable("humidity_outside", (Double) map.get("humidity"));
				fis.setVariable("wind_outside", (Double) map.get("wind"));
			} catch (JsonParseException e) {
				System.out.println("ParseException while connecting to " + url);
			} catch (JsonMappingException e) {
				System.out.println("MappingException while connecting to " + url);
			} catch (IOException e) {
				System.out.println("IOException while connecting to " + url);
			} catch (kong.unirest.UnirestException e) {
				System.out.println("IOException while connecting to " + url);
			}

			// people service
			url = "http://localhost:8081/data";
			try {
				map = connectToService("http://localhost:8081/data");
				fis.setVariable("people_density", (Double) map.get("density"));
			} catch (JsonParseException e) {
				System.out.println("ParseException while connecting to " + url);
			} catch (JsonMappingException e) {
				System.out.println("MappingException while connecting to " + url);
			} catch (IOException e) {
				System.out.println("IOException while connecting to " + url);
			} catch (kong.unirest.UnirestException e) {
				System.out.println("IOException while connecting to " + url);
			}

			// light service
			url = "http://localhost:8082/data";
			try {
				map = connectToService("http://localhost:8082/data");
				fis.setVariable("light_outside", (Double) map.get("intensity"));
			} catch (JsonParseException e) {
				System.out.println("ParseException while connecting to " + url);
			} catch (JsonMappingException e) {
				System.out.println("MappingException while connecting to " + url);
			} catch (IOException e) {
				System.out.println("IOException while connecting to " + url);
			} catch (kong.unirest.UnirestException e) {
				System.out.println("IOException while connecting to " + url);
			}

			// update decision system and repaint charts
			fis.evaluate();
			jdf.repaint();

			// delay
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
