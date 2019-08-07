package org.example;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ValueReferences {
	private String valueName;
	private LinkedList<String> references = new LinkedList<String>(); //never using random access within the list
	
	public ValueReferences(String valueName, String reference) {
		super();
		this.valueName = valueName;
		this.references.push(reference);
	}
	
	public ValueReferences(String valueName, Collection<String> references) {
		super();
		this.valueName = valueName;
		this.references = new LinkedList<String>(references);
	}
	
	public JSONObject getDirections() 
	{
		JSONObject returnValue = new JSONObject();
		JSONArray referencesArray = new JSONArray(references);
		returnValue.put(valueName, referencesArray);
		return returnValue;
	}
	
	public void addReference(String reference)
	{
		for(String ref : references) {
			if(ref.compareTo(reference) == 0)
				return; //The reference it's already inside of the JSONArray;
		}
		references.push(reference);
	}
	public void addMultipleReferences(Collection<String> references)
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
	
	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public Collection<String> getReferences() {
		return references;
	}

	public void setReferences(Collection<String> references) {
		this.references = (LinkedList<String>)references;
	}

	@Override
	public String toString()
	{
		return getDirections().toString();
	}
	public JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		returnValue.put(valueName, new JSONArray(references));
		return returnValue;
	}
}
