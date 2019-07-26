package example.service;
import java.util.concurrent.ThreadLocalRandom; //since java 1.7

import org.json.JSONObject;
public class SensorController {
	private String id;
	private String description;
	private static String wanted_value1 = "100";
	private static String wanted_value2 = "78";
	private static String wanted_value3 = "210";
	
	
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
	
	public int getValue() {
		return ThreadLocalRandom.current().nextInt(0,256); //Returns a random value. Just for testing purposes
	}
	@Override
	public String toString() {
		JSONObject returnValue = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject wantedValues = new JSONObject();
		
		data.put("value1", getValue());
		data.put("value2", getValue());
		data.put("value3", getValue());
		
		wantedValues.put("value1", wanted_value1);
		wantedValues.put("value2", wanted_value2);
		wantedValues.put("value3", wanted_value3);
		
		returnValue.put("id", id);
		returnValue.put("description", description);
		returnValue.put("measured", data.toString());
		returnValue.put("wanted", wantedValues.toString());
		
		return returnValue.toString();
	}
}
