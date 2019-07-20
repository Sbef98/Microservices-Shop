/**
 * 
 */
package com.example.demo;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        StringBuilder sb = new StringBuilder(response.toString());
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        JSONObject result = new JSONObject(sb.toString());
        System.out.println(result);
        return result;
	}
}
