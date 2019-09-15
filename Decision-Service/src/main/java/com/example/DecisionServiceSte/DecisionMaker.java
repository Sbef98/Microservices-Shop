package com.example.DecisionServiceSte;

import java.util.Hashtable;

import org.json.JSONObject;

public class DecisionMaker 
{
	protected static String takeDecision(ServiceDetailsRequestModel apllicantInfo, 
			Hashtable<String, ServiceDetailsRequestModel> groupAvailableServices) 
	{
		JSONObject returnValue = new JSONObject();
		for(String workSpace: apllicantInfo.getWorkspaces())
		return returnValue.toString();
	}
}
