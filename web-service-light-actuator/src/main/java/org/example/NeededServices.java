package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class NeededServices {
	LinkedList<ServiceReferences> neededValues = new LinkedList<ServiceReferences>();
	public NeededServices(String serviceName, String valueName, String reference)
	{
		ServiceReferences sr = new ServiceReferences(serviceName, valueName, reference);
		neededValues.push(sr);
	}
	public NeededServices(String serviceName, String valueName, Collection<String> references)
	{
		ServiceReferences sr = new ServiceReferences(serviceName, valueName, references);
		neededValues.push(sr);
	}
	
	public void addReference(String serviceName,String valueName, String reference)
	 {
		 for(ServiceReferences vf : neededValues) {
			 if(vf.getServiceName().compareTo(serviceName) == 0) {
				 vf.addReference(valueName, reference);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	 }
	public void addReferences(String serviceName,String valueName, Collection<String> references)
	 {
		 for(ServiceReferences vf : neededValues) {
			 if(vf.getServiceName().compareTo(serviceName) == 0) {
				 vf.addReferences(valueName, references);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	 }
	public void addValueName(String serviceName, String valueName)
	{
		for(ServiceReferences vf : neededValues) {
			 if(vf.getServiceName().compareTo(serviceName) == 0) {
				 vf.addValueName(valueName);
			 }
			 else {
				 throw new NullPointerException("No serviceName found fo the value " + serviceName);
			 }
		 }
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		for(ServiceReferences sr : neededValues)
			returnValue.put(sr.getServiceName(), sr.getJSONObjectOfReferences());
		return returnValue;
	}
}
