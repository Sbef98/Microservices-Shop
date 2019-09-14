package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

public class ServiceReferences
{
	 private String serviceName;
	 private LinkedList<ValueReferences> valueReferences = new LinkedList<ValueReferences>();
	 
	 protected ServiceReferences(String serviceName, String valueName, String reference)
	 {
		 this.serviceName = serviceName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	 protected ServiceReferences(String serviceName, String valueName, Collection<String> reference)
	 {
		 this.serviceName = serviceName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	 protected void addReference(String valueName, String reference)
	 {
		 for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 vf.addReference(reference);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	 }
	 protected void addReferences(String valueName, Collection<String> references)
	 {
		 for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 vf.addMultipleReferences(references);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	}
	protected void addValueName(String valueName)
	{
		for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 return;
			 }
		 }
		ValueReferences rf = new ValueReferences(valueName, "*"); 
		//It referes to all services in the group
		valueReferences.push(rf);
	}
	protected String getServiceName() 
	{
		return serviceName;
	}
	protected void setServiceName(String serviceName) 
	{
		this.serviceName = serviceName;
	}
	protected LinkedList<ValueReferences> getValueReferences() 
	{
		return valueReferences;
	}
	protected void setValueReferences(LinkedList<ValueReferences> valueReferences) 
	{
		this.valueReferences = valueReferences;
	}
	protected Map<String, Collection<String>> getMapOfValueReferences() 
	{
		HashMap<String, Collection<String>> returnValue = new HashMap<String, Collection<String>>();
		for(ValueReferences vf : valueReferences)
			returnValue.put(vf.getValueName(), vf.getReferences());
		return returnValue;
	}
	protected JSONObject getJSONObjectOfReferences()
	{
		JSONObject returnValue = new JSONObject();
		for(ValueReferences vf : valueReferences)
			returnValue.put(vf.getValueName(), vf.getReferences());
		return returnValue;
	}
	protected JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		JSONObject valueReferencesReturn = new JSONObject();
		
		for(ValueReferences vf : valueReferences)
			valueReferencesReturn.put(vf.getValueName(), new JSONArray(vf.getReferences()));
		returnValue.put(serviceName, valueReferencesReturn);
		return returnValue;
	}
}
