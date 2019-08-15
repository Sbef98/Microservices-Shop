package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 *Implements an object that describes for one service identified by "serviceName"
 *which data from which services are needed. 
 *JSON obtained {"serviceName" : "valueReferences" }
 */
public class ServiceReferences
{
	 /**
	 * The service which need data from other services
	 */
	private String serviceName;
	 /**
	 * A list of ValueReference objects that describe which data are needed and from which services
	 */
	private LinkedList<ValueReferences> valueReferences = new LinkedList<ValueReferences>();
	 
	 /**
	  * Constructor to create a ServiceReferences object with only one service referenced
	 * @param serviceName The service which need data from other services
	 * @param valueName Type of data to retrieve: like temperature
	 * @param reference Sensor name from which get valueName data
	 */
	protected ServiceReferences(String serviceName, String valueName, String reference)
	 {
		 this.serviceName = serviceName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	
	 /**
	  * Constructor to create a ServiceReferences object with a collection of service referenced
	  * 
	 * @param serviceName The service which need data from other services
	 * @param valueName Type of data to retrieve: like temperature
	 * @param reference Collection of Sensors names from which get valueName data
	 */
	protected ServiceReferences(String serviceName, String valueName, Collection<String> reference)
	 {
		 this.serviceName = serviceName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	
	/**
	 * Add a ValueReference object into valueReferences list of this instance of ServiceReferences, 
	 * add into valueReferences list a type of data to obtain from one single sensor
	 * 
	 * @param valueName Type of data to retrieve: like temperature
	 * @param reference Sensor name from which get valueName data
	 */
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
	
	/**
	 * Add a list of ValueReference objects into valueReferences list of this instance of ServiceReferences, 
	 * add into valueReferences list a valueName type of data to obtain from a collection of services
	 * 
	 * @param valueName Type of data to retrieve: like temperature
	 * @param references Collection of sensors names from which get valueName data
	 */
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
	
	/**
	 * @return The service which need data from other services, the service which wants this references
	 */
	protected String getServiceName() 
	{
		return serviceName;
	}
	/**
	 * @param serviceName The service which need data from other services, the service which wants this references 
	 */
	protected void setServiceName(String serviceName) 
	{
		this.serviceName = serviceName;
	}
	/**
	 * @return valueReferences list of all the references from this service
	 */
	protected LinkedList<ValueReferences> getValueReferences() 
	{
		return valueReferences;
	}
	/** 
	 * @param valueReferences new list to set in this ServiceReferences instance
	 */
	protected void setValueReferences(LinkedList<ValueReferences> valueReferences) 
	{
		this.valueReferences = valueReferences;
	}
	/**
	 * @return Map of all the references required by the serviceName
	 */
	protected Map<String, Collection<String>> getMapOfValueReferences() 
	{
		HashMap<String, Collection<String>> returnValue = new HashMap<String, Collection<String>>();
		for(ValueReferences vf : valueReferences)
			returnValue.put(vf.getValueName(), vf.getReferences());
		return returnValue;
	}
	/**
	 * @return JSONobject with the map of all the references required by the serviceName
	 */
	protected JSONObject getJSONObjectOfReferences()
	{
		JSONObject returnValue = new JSONObject();
		for(ValueReferences vf : valueReferences)
			returnValue.put(vf.getValueName(), vf.getReferences());
		return returnValue;
	}
	/**
	 * @return JSONobject with the map of all the references required by the serviceName encapsulated in a serviceName's references set  
	 */
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
