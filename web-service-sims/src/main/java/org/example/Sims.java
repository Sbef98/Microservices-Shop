package org.example;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class Sims extends ServiceController{

	@Override
	@PostConstruct
	public void addDataType() {
		Communication.registerNewType("temperature", url);
		System.out.println("Registered temperature type!");
		Communication.registerNewType("music", url);
		System.out.println("Registered music type!");
		Communication.registerNewType("color", url);
		System.out.println("Registered color type!");
		Communication.registerNewType("PeopleNumber", url);
		System.out.println("Registered PeopleNumber type!");
	}

	@Override
	public String getGetMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getValues() {
		return new JSONObject().put("PeopleNumber", new JSONArray().put(10));
	}

	@Override
	public JSONObject getWanted() {
		/*Ten people wil set 10 wants*/
		JSONObject wanted = new JSONObject();
		Random r = new Random();
		wanted.put( "music" ,r.nextInt(11));
		/*Adding one music desire*/
		wanted.put( "color" ,r.nextInt(6));
		/*YOu could make it more real using hexadecimal values for RGB*/
		wanted.put("temperature", ThreadLocalRandom.current().nextInt(17, 27));
		return wanted;
	}

	@Override
	public JSONArray getWorkspaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSensor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void elabResponse(String response) {
		// TODO Auto-generated method stub
		//System.out.println(response);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	private String getCronValue()
	{
		return  sleepTime;
	}
	@Override
	@Scheduled(cron="#{@getCronValue}") //cool feature
	public void update()
	{
		String response = new String("Error");
		for(int i = 0; i < 10; i++) {
			try {
				 response = Communication.put(url, serviceName + i, serviceURI, servicePort, getGetMapping(), getPutMapping(),
						 					  groupID, description, getValues(), getWanted(), getWorkspaces(), isSensor());
			} catch (kong.unirest.UnirestException e) {
				System.out.println("UnirestException while connecting to " + url);
			}
			elabResponse(response);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
