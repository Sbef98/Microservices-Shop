/**
 * 
 */
package org.example;

//import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;



/**
 * Implementing a REST microservice using SPRING framework
 * to get weather information from internet, 
 * using default java.net libraries for connection and org.json library to manage JSONs files. 
 */
@RestController
public class WeatherController extends ServiceController {
	
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
	
	/**
	 * Getting bollettino meteo of specified location from internet using openwheathermap.org
	 * and returning it using HTTP protocol
	 * 
	 * @param city String of city's name to retrive wheather conditions;
	 * @return JSONObject rapresenting wheather information;
	 * @throws IOException Error in input buffer reader;
	 */
	@GetMapping("/get-weather/{city}")
	  public JSONObject getWheather(@PathVariable String city) throws IOException {
		//		TODO: Implementing a JSON return, at place of a normal string;
		String key = "f3d84a1b031d0ab92fbc30f9b1bb2233";
		String nation = "it";
		URL WhetherSite = new URL("http://example.com/");
		try {
			WhetherSite = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + nation + "&APPID=" + key + "&mode=JSON");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) WhetherSite.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		con.setRequestMethod("GET");	//Già settato di default	
		
		int responseCode = -1;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        System.out.println("\nSending 'GET' request to URL : " + WhetherSite);
        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        
        String inputLine; 
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);		//In realtà si tratta di una sola linea;
        }
        
        System.out.println(response.toString());
        
        //Producing JSONobject
        JSONObject result = new JSONObject(response.toString());
        //JSONObject is correctly created, but returning it as JSONObject generate problems;
        System.out.println(result);
        return result;
	}
	
	@Override
	String getGetMapping() {
		return "/get-weather/{Rome}";
	}

	@Override
	String getPutMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	JSONObject getValues() {
		try {
			return getWheather("Modena");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new JSONObject().put("Error", "Connection to opemweathermap.com impossible");
		}
	}

	@Override
	JSONObject getWanted() {
		return null;
	}

	@Override
	void elabResponse(String response) {
		// TODO Auto-generated method stub
	}

	@Override
	public String toString() {
		try {
			return getWheather("Rome").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}

	@Override
	JSONObject getNeeded_services() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@PostConstruct
	public void init()
	{
		url = url + serviceName;
	}
	
	@Scheduled(fixedDelay = 1000) //cool feature
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