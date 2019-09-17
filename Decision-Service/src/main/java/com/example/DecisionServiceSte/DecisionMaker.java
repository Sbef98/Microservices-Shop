package com.example.DecisionServiceSte;

import java.util.Hashtable;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;


public class DecisionMaker 
{
	
	private static   Double mode(LinkedList<Double> numbers) {
		int maxCount = 0, i, j;
		Double maxValue = 0.0;
		for (i = 0; i < numbers.size(); i++) {
			int count = 0;
			for (j = 0; j < numbers.size(); ++j) {
				if (numbers.get(j).compareTo(numbers.get(i)) == 0)
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = numbers.get(i);
			}
		}
		return maxValue;
	}
	private static   Double wantedValue(String workspace, 
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) throws NumberFormatException {
		LinkedList<Double> numbers = new LinkedList<Double>();
		for(String serviceID : groupAvailableServices.keySet()) {
			if(groupAvailableServices.get(serviceID).getWorkspaces() != null) {
				for(Object wanted : groupAvailableServices.get(serviceID).getWanted().keySet()) {
					if(workspace.compareToIgnoreCase((String) wanted) == 0) {
						String wantedVal = groupAvailableServices.get(serviceID).getWanted().getString((String) wanted);
						try {
							numbers.add((double) ((Integer)Integer.parseInt(wantedVal)));
						}catch(NumberFormatException e) {
							numbers.add(Double.parseDouble(wantedVal));
						}
					}
				}
			}
		}
		return mode(numbers);
	}
	
	private static   Double serviceMeasuredMean(JSONArray measuredValues) throws NumberFormatException {
		Double mean = 0.0;
		for(Object measuredValue : measuredValues) {
			try {
				mean+= (double)((Integer) Integer.parseInt((String) measuredValue));
			}catch(NumberFormatException e) {
				mean += Double.parseDouble((String) measuredValue);
			}
		}
		return mean/measuredValues.length();
	}
	
	private static   Double averageMeasuredValue(String workspace,
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) throws NumberFormatException {
		Double mean = 0.0;
		//cont conta il numero di sensori aggiunti alla media
		int  cont = 0;
		/*
		 * I need to go through all the services and find the datatype that i'm looking for, then make a weighted 
		 * mean of the measures (weighted because a sensor may have more than one measure and i will make the mean
		 * of these measrues too. In that case, the mean coming from a 9 measurtes sensor must be more valuable
		 * than one with just 2 measures!)
		 */
		for(String serviceID : groupAvailableServices.keySet()) {
			for(String type : groupAvailableServices.get(serviceID).getValues().keySet()) {
				if(type.compareToIgnoreCase(workspace) == 0) {
					cont += groupAvailableServices.get(serviceID).getValues().getJSONArray(type).length();
					mean += serviceMeasuredMean(groupAvailableServices.get(serviceID).getValues().getJSONArray(type)) * groupAvailableServices.get(serviceID).getValues().getJSONArray(type).length();
					/*
					 * I multiply sensorMeasuredMean by the number of measures provided by the service
					 * to give it the right weight in the final mean! 
					 */
				}
			}
		}
		return mean/cont;
	}
	
	private static Double getDecision(Double wanted, Double actual, ServiceDetailsRequestModel applicantInfo, String workspace) throws NumberFormatException 
	{		
		Double activeValue = serviceMeasuredMean(applicantInfo.getValues().getJSONArray(workspace));
		/*The mean setting should be more than enough.*/
		Double newActiveValue = wanted + (wanted - actual);
		if(wanted - actual > wanted/10) {
			newActiveValue += activeValue/8;
		}
		return newActiveValue;
	}
	
	protected static String takeDecision(ServiceDetailsRequestModel applicantInfo, 
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) 
	{
		JSONArray returnValue = new JSONArray();
		for(Object workspace: applicantInfo.getWorkspaces()) {
			Double modeWantedValue;
			Double averageMeasuredValue;			
			try {
				modeWantedValue = wantedValue((String)workspace, groupAvailableServices);
			}catch(NumberFormatException e) {
				return e + ": one or more of the wanted values was not measured with a number!"; 
			}
			try {
				averageMeasuredValue = averageMeasuredValue((String)workspace, groupAvailableServices);
			}catch(NumberFormatException e) {
				return e + ": one or more of the values was not measured using a number!";
			}
			try {
				returnValue.put(getDecision(modeWantedValue, averageMeasuredValue, applicantInfo, (String)workspace));
			}catch(NumberFormatException e) {
				return e + ": " + (String) workspace + " in " + applicantInfo.getName() + " is not a number!";
			}
		}
		return returnValue.toString();
	}
}
