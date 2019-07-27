package com.example.DecisionServiceSte;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DecisionMaker 
{
	private static LinkedList<String> getAllSensorsNames(HashMap<String,ServiceDetailsRequestModel> availableServices)
	{
		LinkedList<String> names_list = new LinkedList<String>();
		Iterator it = availableServices.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next(); 
	        if(((ServiceDetailsRequestModel) pair.getValue()).getType().compareToIgnoreCase("sensor") == 0) {
	        	names_list.add((String) pair.getKey()); 
	        }
	    }
		return names_list;
	}
	
	private static float getMeasuredSensorData(String valueName, JSONObject measuredValues)
	{
		/*
		 * valueName is the name of the measured value i am looking for while measured values are ALL the measured values by the sensor
		 * in the form valueName : [measuredValues1, measuredValue2, ...]
		 */
		float returnValue = 0;
		int i = 0;
		Map<String,Object> values = measuredValues.toMap();
		//Now i have a map where the key is the name of the measured value from the sensor
		Iterator it = values.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry pair = (Map.Entry)it.next();
	    	//I need to check if this is the value i was looking for
	    	if(valueName.compareToIgnoreCase((String) pair.getKey()) == 0) {
	    		//This is what i was looking for!
	    		for(Object value : ((JSONArray) pair.getValue())) {
	    			i++;
	    			returnValue += (float) value;
	    		}
	    		return returnValue/i; //return the mean of the measured values
	    	}
	    	
	    }
		return returnValue;
	}
	
	private static float getWantedSensorValue()
	{
		float returnValue = 0;
		
		return returnValue;
	}
	
	private static float computeCorrection()
	{
		float returnValue = 0;
		
		return returnValue;
	}
	
	private static JSONArray ComputeAnswer(String key, JSONObject value, HashMap<String,ServiceDetailsRequestModel> availableServices)
	{
		/*
		 * Key is the sensor's name
		 * value is needed_value : [references,...]
		 */
		JSONArray returnValue = new JSONArray();
		ServiceDetailsRequestModel sensorData = availableServices.get(key);
		if(sensorData == null) 
			System.out.println("Servizio "+ key +" non disponibile");
		else {
			Map<String,Object> needed_data = value.toMap(); //Now i have a map where the key is the needed_value and the body is the JSONArray of references
			Iterator it = needed_data.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next(); //We can use the key to retrive the right sensor measured value from 
		        try {
		        	float measuredData = getMeasuredSensorData((String) pair.getKey(), sensorData.getData().getJSONObject("measured")); //This is the value measured by this sensor
		        }catch(JSONException e) {
		        	System.out.println("Missing measured field in sensor " + key);
		        }
		        float wantedValue = getWantedSensorValue(); //TODO
		        float correction = computeCorrection(); //TODO
		        returnValue.put(correction);
		    }
		}
		return returnValue;
	}
	
	
	public static String takeDecision(ServiceDetailsRequestModel applicantInfo, HashMap<String,ServiceDetailsRequestModel> availableServices)
	{
		
		JSONObject returnValue = new JSONObject();
		Map<String,Object> needed_sensors;
		try {
			needed_sensors = ((JSONObject) applicantInfo.getData().get("needed_sensors")).toMap();
		} catch(JSONException e){
			return "Wrong protocol used.";
		}
		
		Iterator it = needed_sensors.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next(); 
	        String key = (String) pair.getKey();
	        JSONObject value = (JSONObject) pair.getValue();
	        if(key.compareToIgnoreCase("*") != 0) {
	        	returnValue.put(key, ComputeAnswer(key,value, availableServices)); //TODO add the version with the suggested values
	        }
	        else {
	        	for(String key2 : getAllSensorsNames(availableServices)) {
	        		returnValue.put((String) pair.getKey(), ComputeAnswer(key2,value,availableServices));
	        	}
	        }
	        
	    }
	    
		return returnValue.toString();
	}
}
