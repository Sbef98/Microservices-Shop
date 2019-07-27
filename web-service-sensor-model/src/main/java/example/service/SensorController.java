package example.service;

import java.util.concurrent.ThreadLocalRandom; //since java 1.7
import org.json.JSONObject;

public class SensorController {
	private String id;
	private String description;
	private static String wanted_value1 = "95";
	private static String wanted_value2 = "82";
	private static String wanted_value3 = "200";
	
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
		
		data.put("value1", ThreadLocalRandom.current().nextInt(0, 256));
		data.put("value2", ThreadLocalRandom.current().nextInt(0, 256));
		data.put("value3", ThreadLocalRandom.current().nextInt(0, 256));
		
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
