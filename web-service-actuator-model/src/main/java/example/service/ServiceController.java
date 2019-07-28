package example.service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableScheduling
public class ServiceController
{
	@Value("${service.connectTo}")
	private String url; // "http://localhost:8000/put?serviceName=thisServiceName"; //Where is the decision service located
	@Value("${server.port}")
    private String servicePort;
	@Value("${service.id}")
	private String id;
	@Value("${service.description}")
	private String description;
	@Value("${service.name}")
	private String serviceName;
	@Value("${service.URI}")
	private String serviceURI;
	@Value("${service.type}")
	private String type;
	private ActuatorController actuator; //TODO fix The difference ActuatorController/SensorController
	
	@PostConstruct
	public void init() {
		if(type.compareToIgnoreCase("actuator") == 0) {
			actuator = new ActuatorController(id,description);
		}
		url = url + serviceName;
		//System.out.println(url);
	}
	
	@GetMapping(value="get", produces="application/json")
	public String showData()
	{
		return actuator.toString();
	}
	
	@Scheduled(fixedDelay = 1000) //cool feature
	public void update()
	{
		String response = new String("Error");
		if(type.compareToIgnoreCase("sensor") == 0) {
			actuator = new ActuatorController(id,description);
		}
		try {
			 //System.out.println("Attendo connessione a " + url);
			 //response = connectToService(url, servicePort, actuator);
			 response = Communication.put(url, serviceURI, servicePort, type, actuator);
		/*} catch (IOException e) {
			System.out.println("IOException while connecting to " + url);*/
		} catch (kong.unirest.UnirestException e) {
			System.out.println("UnirestException while connecting to " + url);
		}
		//System.out.println(response.toString());
		ResponseComputer.elabResponse(response.toString(), actuator);
	}
	 
	@PreDestroy
	public void destroy() {
		// TODO
	}
}

