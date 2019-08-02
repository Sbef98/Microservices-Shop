package example.service;

import org.json.JSONArray;
//import java.util.concurrent.ThreadLocalRandom; //since java 1.7
import org.json.JSONObject;

public class ActuatorController {
	private String id;
	private String description;
	private final float suggested_value1 = 95;
	private final float suggested_value2 = 82;
	private final float suggested_value3 = 200;
	private boolean activeValue1 = false; // like an on/off
	private float activeValue2 = 82;
	private float activeValue3 = 200;

	
	
	public float getSuggested_value1() {
		return suggested_value1;
	}

	public float getSuggested_value2() {
		return suggested_value2;
	}

	public float getSuggested_value3() {
		return suggested_value3;
	}

	public boolean isActiveValue1() {
		return activeValue1;
	}

	public float getActiveValue2() {
		return activeValue2;
	}

	public float getActiveValue3() {
		return activeValue3;
	}

	public void setActiveValue1(boolean activeValue1) {
		this.activeValue1 = activeValue1;
	}

	public void setActiveValue2(float activeValue2) {
		this.activeValue2 = activeValue2;
	}

	public void setActiveValue3(float activeValue3) {
		this.activeValue3 = activeValue3;
	}

	public ActuatorController(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JSONObject getData() {
		JSONObject returnValue = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject suggestedValues = new JSONObject();
		JSONObject needed_sensors = new JSONObject();
		JSONArray references1 = new JSONArray();
		JSONArray references2 = new JSONArray();
		JSONArray references3 = new JSONArray();
		JSONObject needed_value = new JSONObject();

		references1.put("sensor-name"); // references = In which services should i look for the "wanted" amount of this
										// value
		references2.put("sensor-name");
		references3.put("sensor-name");
		references3.put("sensor-name2");

		needed_value.put("value1", references1); // Which values i look for : references
		needed_value.put("value2", references2);
		needed_value.put("value3", references3);
		
		
		needed_sensors.put("sensor-name", needed_value); // As a key, the name of the sensor where i should look for
															// the emasured values.
		data.put("value1", activeValue1);
		data.put("value2", activeValue2);
		data.put("value3", activeValue3);

		// suggestedValues.put("value1", suggested_value1);
		suggestedValues.put("value2", suggested_value2);
		suggestedValues.put("value3", suggested_value3);

		returnValue.put("id", id);
		returnValue.put("description", description);
		returnValue.put("active", data);
		returnValue.put("suggested", suggestedValues);
		returnValue.put("needed_sensors", needed_sensors);
		//System.out.println(returnValue);

		return returnValue;
	}

	@Override
	public String toString() {
		return getData().toString();
	}
}
