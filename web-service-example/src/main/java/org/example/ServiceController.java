package org.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;


public abstract class ServiceController 
{
	@Value("${service.connectTo}")
	private String url; // "http://localhost:8000/put?serviceName=thisServiceName"; //Where is the decision service located
	@Value("${server.port}")
    private int servicePort;
	@Value("${service.groupID}")
	private String groupID;
	@Value("${service.description}")
	private String description;
	@Value("${service.name}")
	private String serviceName;
	@Value("${service.URI}")
	private String serviceURI;
	@Value("${service.type}")
	private String type;
	@Value("${service.sleepTime}")
	private int sleepTime; 
	
	abstract String getGetMapping();
	abstract String getPutMapping();
	abstract JSONObject getValues();
	abstract JSONObject getWanted();
	abstract JSONObject getNeeded_sesnors();
	
	
	abstract protected JSONObject getData();
	abstract void elabResponse(String response);
	@Override
	abstract public String toString();
	
	@PostConstruct
	public void init()
	{
		url = url + serviceName;
	}
	
	@Scheduled() //cool feature
	public void update()
	{
		String response = new String("Error");
		try {
			 response = Communication.put(url, serviceURI, servicePort, type, getGetMapping(), getPutMapping(),
					 					  groupID, description, getValues(), getWanted(), getNeeded_sesnors());
		/*} catch (IOException e) {
			System.out.println("IOException while connecting to " + url);*/
		} catch (kong.unirest.UnirestException e) {
			System.out.println("UnirestException while connecting to " + url);
		}
		//System.out.println(response.toString());
		elabResponse(response.toString());
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
