package org.example;

import java.util.Collection;
//import java.util.HashMap;
import java.util.LinkedList;
//import java.util.Map;

//import org.json.JSONArray;
import org.json.JSONObject;

/**
 *Creates an object to represent all the values to compare from all the
 *comparator services. 
 *
 *JSON obtained: a list of ServiceReferences
 */
public class NeededServices {
	/**
	 * The list representing the data to compare and the services from which compare that data;
	 */
	LinkedList<ServiceReferences> neededValues = new LinkedList<ServiceReferences>();
	/**
	 * Constructor to create an object with only one data type to compare from only one comparator service;
	 * 
	 * @param serviceName first comparison service
	 * @param valueName comparison data type
	 * @param reference comparator model service
	 */
	public NeededServices(String serviceName, String valueName, String reference)
	{
		ServiceReferences sr = new ServiceReferences(serviceName, valueName, reference);
		neededValues.push(sr);
	}
	/**
	 * Constructor to create an object with only one data type to compare from a collection of comparator service;
	 * 
	 * @param serviceName first comparison service
	 * @param valueName comparison data type
	 * @param references comparator model services 
	 */
	public NeededServices(String serviceName, String valueName, Collection<String> references)
	{
		ServiceReferences sr = new ServiceReferences(serviceName, valueName, references);
		neededValues.push(sr);
	}
	
	/**
	 * Add a new comparison in needValues list with only one data type to compare from only one comparator service;
	 * 
	 * @param serviceName first comparison service
	 * @param valueName comparison data type
	 * @param reference comparator model service
	 */
	
	
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
	/**
	 *Add a new comparison collection in needValues list with only one data type to compare from a collection of comparator services;
	 * 
	 *@param serviceName first comparison service
	 *@param valueName comparison data type
	 *@param references comparator model services
	 */
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
	/**
	 *Add a new comparisons collection in needValues list with comparisons data types to compare from all services of the same group;
	 * 
	 *@param serviceName first comparison service
	 *@param valueName comparison data type
	 *
	 */
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
	
	/**
	 * @return JSONobject with the map of all the comparisons for each serviceName
	 */
	public JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		for(ServiceReferences sr : neededValues)
			returnValue.put(sr.getServiceName(), sr.getJSONObjectOfReferences());
		return returnValue;
	}
}
