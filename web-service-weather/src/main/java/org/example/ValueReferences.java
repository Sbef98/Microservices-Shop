package org.example;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to describe a data request for comparison.
 * Create an object with a key that is the name of the data requested and a Collection
 * of sensor/s to obtain that data for comparison.
 * 
 * JSON obtained {"valueName" : ["comparator"] }
 */
public class ValueReferences {
	/**
	 * Comparison data type to retrieve: like "temperature"
	 */
	private String valueName;
	/**
	 * "comparator" sensor/sensors name used like models to compare valueName type of data
	 */
	private LinkedList<String> references = new LinkedList<String>(); //never using random access within the list
	
	/**
	 * Constructor to create a ValueReferences object that
	 * want the data to compare from only one comparator sensor
	 * 
	 * @param valueName name of the data measured: like "temperature"
	 * @param reference sensor from which retrieve data
	 */
	protected ValueReferences(String valueName, String reference) {
		super();
		this.valueName = valueName;
		this.references.push(reference);
	}
	/**
	 * Constructor to create a ValueReferences object that wants
	 * the data to compare from a collection of comparator sensors
	 * 
	 * @param valueName type of the data to compare: like "temperature"
	 * @param references comparator sensors
	 */
	protected ValueReferences(String valueName, Collection<String> references) {
		super();
		this.valueName = valueName;
		this.references = new LinkedList<String>(references);
	}
	
	/**
	 * Add a comparator sensor into references list from which retrieve
	 * valueName data for comparison
	 * 
	 * @param reference comparator sensor to add into references list
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
	 * Add a list of comparator sensors into references list from which retrieve
	 * valueName data for comparison
	 * 
	 * @param references collections of comparator sensors to add into a ValueReferences object
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
	
	
	protected String getValueName() {
		return valueName;
	}
	protected void setValueName(String valueName) {
		this.valueName = valueName;
	}
	protected Collection<String> getReferences() {
		return references;
	}
	/**
	 * Set references collections with a new collection
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
		return toJSONObject().toString();
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
