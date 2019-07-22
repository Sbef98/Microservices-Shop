/**
 * 
 */
package com.example.demo;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Implementing a REST microservice using SPRING framework
 * to get weather information from internet, 
 * using default java.net libraries for connection and org.json library to manage JSONs files. 
 * 
 * @author Leonardo
 */
@RestController
public class WheatherController {
	
	/**
	 * Getting bollettino meteo of specified location from internet using openwheathermap.org
	 * and returning it using HTTP protocol
	 * 
	 * @param city String of city's name to retrive wheather conditions;
	 * @return JSONS's string view rapresenting wheather information;
	 * @throws IOException Error in input buffer reader;
	 */
	@GetMapping("/get-wheather/{city}")
	  public String getWheather(@PathVariable String city) throws IOException {
		//		TODO: Implementing a JSON return, at place of a normal string;
		URL WhetherSite = new URL("http://example.com/");
		try {
			WhetherSite = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + ",it&APPID=ebd0ace07cbc44cc01d888e9ca579a9f&mode=JSON");
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
        return result.toString();
	}
}
