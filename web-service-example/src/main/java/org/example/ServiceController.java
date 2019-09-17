package org.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Abstract class to implements the effective REST architecture 
 * and communication with Decision Service. Add @RestController
 * annotation at the head of your class declaration;
 */
@EnableScheduling
//@RestController	#!important		Add Rest controller annotations!
public abstract class ServiceController implements MicroserviceController
{
	
	@PostConstruct
	public abstract void addDataType();
	
	/**
	 * Http address of Decision Service;
	 */
	@Value("${service.connectTo}")
	protected String url; // "http://localhost:8000/put?serviceName=thisServiceName"; //Where is the decision service located
	/**
	 * Port where Service is listening; 
	 */
	@Value("${server.port}")
    protected int servicePort;
	/**
	 * ID of group of working; 
	 */
	@Value("${service.groupID}")
	protected String groupID;
	/**
	 * Description of service, optional, avoid punctuation, max 225 characters;
	 */
	@Value("${service.description}")
	protected String description;
	/**
	 * Name of this service;
	 */
	@Value("${service.name}")
	protected String serviceName;
	/**
	 * http address where this service is listening;
	 */
	@Value("${service.URI}")
	protected String serviceURI;
	/**
	 * Sleep time between executions;
	 */
	@Value("${service.sleepTime}")
	protected String sleepTime; //MUST BE A CRON EXpression

	
	public abstract String getGetMapping();
	public abstract String getPutMapping();
	public abstract JSONObject getValues();
	public abstract JSONObject getWanted();
	public abstract JSONArray getWorkspaces();
	public abstract boolean isSensor();
	
	/**
	 * @param response data to elaborate, if necessary;
	 */
	protected abstract void elabResponse(String response);
	@Override
	abstract public String toString();
	
	
	/**
	 * @return sleeptime time between executions
	 */
	@Bean
	private String getCronValue()
	{
		return  sleepTime;
	}
	
	/**
	 * Send a PUT message to the Decision Service and call elabResponse to eaborate data;
	 * It's possible to enable a scheduling;
	 */
	@Async //Async means that the scheduled function will run again after the given time even if the previous one was not finished
	@Scheduled(cron="#{@getCronValue}") //cool feature
	public void update()
	{
		String response = new String("Error");
		try {
			 response = Communication.put(url, serviceName, serviceURI, servicePort, getGetMapping(), getPutMapping(),
					 					  groupID, description, getValues(), getWanted(), getWorkspaces(), isSensor());
		} catch (kong.unirest.UnirestException e) {
			System.out.println("UnirestException while connecting to " + url);
		}
		elabResponse(response);
	}
	
	/**
	 *Send a exiting message;
	 */
	@PreDestroy
	public void end()
	{
		System.out.println("Uscita da " + serviceName);
	}
}
