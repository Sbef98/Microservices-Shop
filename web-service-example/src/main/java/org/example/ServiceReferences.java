package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 *Implements an object that describes for one service identified by "serviceName"
 *which data from from that service must be compared with comparator/s sensor/s. 
 *JSON obtained {"serviceName" : "valueReferences" }
 */
public class ServiceReferences
{
	 /**
	 * The service from which retrieve data;
	 */
	private String serviceName;
	 /**
	 * A list of object that describes the type of data to compare and the comparator service/s used like models
	 */
	private LinkedList<ValueReferences> valueReferences = new LinkedList<ValueReferences>();
	 
	 /**
	  * Constructor to create a ServiceReferences object with only one comparator service
	  * 
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
	  * Constructor to create a ServiceReferences object with a collection of comparator services
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
	 * add into valueReferences list a type of data to compare from another comparator sensor
	 * 
	 * @param valueName Type of data to compare: like temperature
	 * @param reference Sensor name with which compare serviceName data
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
	 * Add a ValueReference object into valueReferences list of this instance of ServiceReferences, 
	 * add into valueReferences list a type of data to compare from another list of comparator sensors
	 * 
	 * @param valueName Type of data to compare: like temperature
	 * @param references Sensor name with which compare serviceName data
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
	 
	/**
	 * Add a ValueReference object into valueReferences list of this instance of ServiceReferences, 
	 * add into valueReferences list a type of data to compare from all the comparator sensors of the same serviceName group
	 * 
	 * @param valueName Type of data to compare
	 */
	protected void addValueName(String valueName)
	{
		for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 return;
			 }
		 }
		ValueReferences rf = new ValueReferences(valueName, "*"); 
		//It refers to all services in the group
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
	/**
	 * @return Map of all the comparator sensors from each data type of the serviceName service
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
	 * @return JSONobject with the Map of all the comparator sensors from each data type of the serviceName service encapsulated in a serviceName's comparator set  
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
