package example.service;

import java.util.concurrent.ThreadLocalRandom; //since java 1.7

import org.json.JSONArray;
import org.json.JSONObject;

public class SensorController {
	private String id;
	private String description;
	private static float wanted_value1 = 95;
	private static float wanted_value2 = 82;
	private static float wanted_value3 = 200;
	
	public SensorController(String id, String description) {
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
		
		JSONArray value1 = new JSONArray();
		value1.put(ThreadLocalRandom.current().nextInt(0, 256));
		value1.put(ThreadLocalRandom.current().nextInt(0, 256));
		JSONArray value2 = new JSONArray();
		value2.put(ThreadLocalRandom.current().nextInt(0, 256));
		JSONArray value3 = new JSONArray();
		value3.put(ThreadLocalRandom.current().nextInt(0, 256));
		
		data.put("value1", value1);
		data.put("value2", value2);
		data.put("value3", value3);
		
		suggestedValues.put("value1", wanted_value1);
		suggestedValues.put("value2", wanted_value2);
		suggestedValues.put("value3", wanted_value3);
		
		returnValue.put("id", id);
		returnValue.put("description", description);
		returnValue.put("measured", data);
		returnValue.put("wanted", suggestedValues);
		
		return returnValue;
	}
	
	@Override
	public String toString() {
		return getData().toString();
	}
}
