package com.example.DecisionServiceSte;

import java.util.Hashtable;
import java.util.LinkedList;

import org.json.JSONObject;


public class DecisionMaker 
{
	private static Double mode(LinkedList<Double> numbers) {
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
	private static Double wantedValue(String workspace, 
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) throws NumberFormatException {
		LinkedList<Double> numbers = new LinkedList<Double>();
		for(String serviceID : groupAvailableServices.keySet()) {
			if(groupAvailableServices.get(serviceID).getWorkspaces() !=null) {
				for(Object wanted : groupAvailableServices.get(serviceID).getWanted().keySet()) {
					if((String) wanted == workspace) {
						String wantedVal = groupAvailableServices.get(serviceID).getWanted().getString((String) wanted);
						try {
							Integer wantedValNum = ((Integer)Integer.parseInt(wantedVal));
							numbers.add((double) wantedValNum);
						}catch(NumberFormatException e) {
							Double wantedValNum = ((Double) Double.parseDouble(wantedVal));
							numbers.add(wantedValNum);
						}
					}
				}
			}
		}
		return mode(numbers);
	}
	
	private static Double averageMeasuredValue(String workspace,
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) throws NumberFormatException {
		Double returnValue = 0.0;
		
		return returnValue;
	}
	
	protected static String takeDecision(ServiceDetailsRequestModel applicantInfo, 
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) 
	{
		JSONObject returnValue = new JSONObject();
		for(Object workspace: applicantInfo.getWorkspaces()) {
			Double modeWantedValue = wantedValue((String)workspace, groupAvailableServices);
			Double averageMeasuredValue;
			
		}
		return returnValue.toString();
	}
}
