package org.example;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ValueReferences {
	private String valueName;
	private LinkedList<String> references = new LinkedList<String>(); //never using random access within the list
	
	protected ValueReferences(String valueName, String reference) {
		super();
		this.valueName = valueName;
		this.references.push(reference);
	}
	
	protected ValueReferences(String valueName, Collection<String> references) {
		super();
		this.valueName = valueName;
		this.references = new LinkedList<String>(references);
	}
	
	protected JSONObject getDirections() 
	{
		JSONObject returnValue = new JSONObject();
		JSONArray referencesArray = new JSONArray(references);
		returnValue.put(valueName, referencesArray);
		return returnValue;
	}
	
	protected void addReference(String reference)
	{
		for(String ref : references) {
			if(ref.compareTo(reference) == 0)
				return; //The reference it's already inside of the JSONArray;
		}
		references.push(reference);
	}
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

	protected void setReferences(Collection<String> references) {
		this.references = (LinkedList<String>)references;
	}

	@Override
	public String toString()
	{
		return getDirections().toString();
	}
	protected JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		returnValue.put(valueName, new JSONArray(references));
		return returnValue;
	}
}
