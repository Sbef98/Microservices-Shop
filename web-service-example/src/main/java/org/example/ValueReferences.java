package org.example;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to describe a measurement request. Create an object with a 
 * key that is the name of the data measured requested and a Collection
 * of sensor from which retrieve that data.
 * JSON obtained {"valueName" : "references" }
 */
public class ValueReferences {
	/**
	 * Name of the data measured: like "temperature"
	 */
	private String valueName;
	/**
	 * Value of the data referenced
	 */
	private LinkedList<String> references = new LinkedList<String>(); //never using random access within the list
	
	/**
	 * Constructor to create a ValueReferences object that
	 * want the data from only one sensor
	 * 
	 * @param valueName name of the data measured: like "temperature"
	 * @param reference sensors from which retrieve data
	 */
	protected ValueReferences(String valueName, String reference) {
		super();
		this.valueName = valueName;
		this.references.push(reference);
	}
	
	/**
	 * Constructor to create a ValueReferences object that wants
	 * the data from a collection of sensors
	 * 
	 * @param valueName name of the data measured: like "temperature"
	 * @param references sensors from which retrieve data
	 */
	protected ValueReferences(String valueName, Collection<String> references) {
		super();
		this.valueName = valueName;
		this.references = new LinkedList<String>(references);
	}
	
	//Questa funzione SthefSthef è identica a toJSONObject, è necessaria?
	protected JSONObject getDirections() 
	{
		JSONObject returnValue = new JSONObject();
		JSONArray referencesArray = new JSONArray(references);
		returnValue.put(valueName, referencesArray);
		return returnValue;
	}
	
	/**
	 * Add a sensor into references list from which retrieve
	 * valueName data
	 * 
	 * @param reference sensor to add into references list
	 */
	protected void addReference(String reference)
	{
		for(String ref : references) {
			if(ref.compareTo(reference) == 0)
				return; //The reference it's already inside of the JSONArray;
		}
		references.push(reference);
	}
	/**
	 * Add a list of sensor into references list from which retrieve
	 * valueName data
	 * 
	 * @param references collections of references to add into a ValueReferences object
	 */
	protected void addMultipleReferences(Collection<String> references)
	{
		for(String ref1 : this.references) {
			for(String ref2 : references) {
				if(ref1.compareTo(ref2) == 0)
					return; //The reference it's already inside of the JSONArray;
				else
					this.references.push(ref2);
			}
		}
	}
	
	/**
	 * Get name of the data retrieved
	 * 
	 * @return key name of the data measured: like temperature
	 */
	protected String getValueName() {
		return valueName;
	}

	/**
	 * Set name of type of data to retrieve
	 * 
	 * @param valueName name of the data measured to retrieve
	 */
	protected void setValueName(String valueName) {
		this.valueName = valueName;
	}

	/**
	 * @return Collection of sensors from which retrieve "valueName" data
	 */
	protected Collection<String> getReferences() {
		return references;
	}

	/**
	 * Change references collections with a new collection
	 * of sensors from which retrieve the valueName data
	 * 
	 * @param references Collections of sensors from which retrieve the valueName data
	 */
	protected void setReferences(Collection<String> references) {
		this.references = (LinkedList<String>)references;
	}

	@Override
	public String toString()
	{
		return getDirections().toString();
	}
	
	/**
	 * Generates a JSONObject with a field "valueName" containing "references"
	 * 
	 * @return JSONObject with ValueReferences object saved inside
	 */
	protected JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		returnValue.put(valueName, new JSONArray(references));
		return returnValue;
	}
}
