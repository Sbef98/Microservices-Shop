package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

public class SensorReferences
{
	 private String sensorName;
	 private LinkedList<ValueReferences> valueReferences = new LinkedList<ValueReferences>();
	 
	 public SensorReferences(String sensorName, String valueName, String reference)
	 {
		 this.sensorName = sensorName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	 public SensorReferences(String sensorName, String valueName, Collection<String> reference)
	 {
		 this.sensorName = sensorName;
		 ValueReferences vf = new ValueReferences(valueName, reference);
		 this.valueReferences.push(vf);
	 }
	 public void addReference(String valueName, String reference)
	 {
		 for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 vf.addReference(reference);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	 }
	 public void addReferences(String valueName, Collection<String> references)
	 {
		 for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 vf.addMultipleReferences(references);
				 return;
			 }
		 }
		 throw new NullPointerException("The valueName " + valueName +" Was not available. The reference was not updated!\n");
	}
	public void addValueName(String valueName)
	{
		for(ValueReferences vf : valueReferences) {
			 if(vf.getValueName().compareTo(valueName) == 0) {
				 return;
			 }
		 }
		ValueReferences rf = new ValueReferences(valueName, "*"); 
		//It referes to all sensors in the group
	}
	public String getSensorName() 
	{
		return sensorName;
	}
	public void setSensorName(String sensorName) 
	{
		this.sensorName = sensorName;
	}
	public LinkedList<ValueReferences> getValueReferences() 
	{
		return valueReferences;
	}
	public void setValueReferences(LinkedList<ValueReferences> valueReferences) 
	{
		this.valueReferences = valueReferences;
	}
	public Map<String, Collection<String>> getMapOfValueReferences() 
	{
		HashMap<String, Collection<String>> returnValue = new HashMap<String, Collection<String>>();
		for(ValueReferences vf : valueReferences)
			returnValue.put(vf.getValueName(), vf.getReferences());
		return returnValue;
	}
	public JSONObject toJSONObject()
	{
		JSONObject returnValue = new JSONObject();
		JSONObject valueReferencesReturn = new JSONObject();
		
		for(ValueReferences vf : valueReferences)
			valueReferencesReturn.put(vf.getValueName(), new JSONArray(vf.getReferences()));
		returnValue.put(sensorName, valueReferencesReturn);
		return returnValue;
	}
}
