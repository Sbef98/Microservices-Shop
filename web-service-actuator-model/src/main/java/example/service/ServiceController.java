package example.service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableScheduling
public class ServiceController
{
	private String url; // "http://localhost:8000/put?serviceName=thisServiceName"; //Where is the decision service located
	@Value("${server.port}")
    private String servicePort;
	@Value("${service.id}")
	private String id;
	@Value("${service.description}")
	private String description;
	private String serviceName;
	private String serviceURI;
	private String type;
	private ActuatorController actuator; //TODO fix The difference ActuatorController/SensorController
	
	@Autowired
	public ServiceController(String[] serviceArgs) {
		super();
		System.out.print("THE NUMBERR OF PASSED ARGS IS ");
		System.out.println(serviceArgs.length); //COME MAI 0??
		this.url = serviceArgs[0] + serviceArgs[1]; //First argument is the Url at which the decision service is listening. The second one is the service name
		this.serviceName = serviceArgs[1];
		this.serviceURI = serviceArgs[2];
		this.type = serviceArgs[3];
	}
	
	@PostConstruct
	public void init() {
		if(type.compareToIgnoreCase("actuator") == 0) {
			actuator = new ActuatorController(id,description);
		}
		url = url + serviceName;
	}
	
	@GetMapping("get")
	public JSONObject showData()
	{
		return new ActuatorController(id,description).getData();
	}
	
	@Scheduled(fixedDelay = 1000) //cool feature
	public void update()
	{
		String response = new String("Error");
		if(type.compareToIgnoreCase("sensor") == 0) {
			actuator = new ActuatorController(id,description);
		}
		try {
			 System.out.println("Attendo connessione a " + url);
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

