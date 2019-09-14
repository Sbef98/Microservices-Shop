package org.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public abstract class ServiceController 
{
	@Value("${service.connectTo}")
	protected String url; // "http://localhost:8000/put?serviceName=thisServiceName"; //Where is the decision service located
	@Value("${server.port}")
    protected int servicePort;
	@Value("${service.groupID}")
	protected String groupID;
	@Value("${service.description}")
	protected String description;
	@Value("${service.name}")
	protected String serviceName;
	@Value("${service.URI}")
	protected String serviceURI;
	@Value("${service.type}")
	protected String type;
	@Value("${service.sleepTime}")
	protected int sleepTime; 
	abstract String getGetMapping();
	abstract String getPutMapping();
	abstract JSONObject getValues();
	abstract JSONObject getWanted();
	abstract JSONObject getNeeded_services();
	
	abstract void elabResponse(String response);
	@Override
	abstract public String toString();
	
	@PostConstruct
	public void init()
	{
		url = url + serviceName;
	}
	
	@Scheduled(fixedDelay = 1) //cool feature
	public void update()
	{
		String response = new String("Error");
		try {
			 response = Communication.put(url, serviceURI, servicePort, type, getGetMapping(), getPutMapping(),
					 					  groupID, description, getValues(), getWanted(), getNeeded_services());
		} catch (kong.unirest.UnirestException e) {
			System.out.println("UnirestException while connecting to " + url);
		}
		elabResponse(response.toString());
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void end()
	{
		try {
			Communication.close(url, groupID);
		} catch (kong.unirest.UnirestException e) {
			System.out.println("UnirestException while connecting to " + url);
		}
		System.out.println("Uscita da " + serviceName);
	}
}
