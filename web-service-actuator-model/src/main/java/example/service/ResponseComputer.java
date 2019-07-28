package example.service;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseComputer 
{
	protected static void elabResponse(String response, ActuatorController actuator)
	{
		//Let's pretend the answer it's a simple, but really simple, json for each value.
		//System.out.println(response);
		JSONObject thing = new JSONObject(response);
		float value1, value2, value3;
		try {
			value1 = thing.getJSONObject("sensor-name").getFloat("value1");
			value2 = thing.getJSONObject("sensor-name").getFloat("value2");
			value3 = thing.getJSONObject("sensor-name").getFloat("value3");
		}catch(JSONException e) {
			System.out.println(e + ": valore mancante nella risposta!");
			return;
		}
		if(value1 < actuator.getSuggested_value1())
			actuator.setActiveValue1(true);
		else
			actuator.setActiveValue1(false);
		actuator.setActiveValue2(actuator.getActiveValue2() + value2);
		actuator.setActiveValue3(actuator.getActiveValue3() + value3);
	}
}
