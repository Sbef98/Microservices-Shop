/**
 * 
 */
package com.example.demo;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Leonardo;
 */
@RestController
public class WheatherController {

	@GetMapping("/get-wheather")
	  public JSONObject getWheather() throws IOException {
		
		URL WhetherSite = new URL("http://example.com/");
		try {
			WhetherSite = new URL("http://api.openweathermap.org/data/2.5/weather?q=Modena,it&APPID=ebd0ace07cbc44cc01d888e9ca579a9f&mode=JSON");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) WhetherSite.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con.setRequestMethod("GET");
		
		int responseCode = -1;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("\nSending 'GET' request to URL : " + WhetherSite);
        System.out.println("Response Code : " + responseCode);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
	        System.out.println(inputLine);
        }
        in.close();
        
        System.out.println(response.toString());
        
        JSONObject myResponse = new JSONObject();
        myResponse.put("clima", response);
        System.out.println(myResponse.toJSONString());
        
        return myResponse;
	}
}
