package example.service;

//import java.util.concurrent.ThreadLocalRandom; //since java 1.7
import org.json.JSONObject;

public class ActuatorController {
	private String id;
	private String description;
	//private static String suggested_value1 = "95";
	private static String suggested_value2 = "82";
	private static String suggested_value3 = "200";
	private String activeValue1 = "off"; // useremo un booleano per questo
	private String activeValue2 = "82";
	private String activeValue3 = "200";
	
	
	public void setActiveValue1(String activeValue1) {
		this.activeValue1 = activeValue1;
	}
	public void setActiveValue2(String activeValue2) {
		this.activeValue2 = activeValue2;
	}
	public void setActiveValue3(String activeValue3) {
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
		
		data.put("value1", activeValue1);
		data.put("value2", activeValue2);
		data.put("value3", activeValue3);
		
		//suggestedValues.put("value1", suggested_value1);
		suggestedValues.put("value2", suggested_value2);
		suggestedValues.put("value3", suggested_value3);
		
		returnValue.put("id", id);
		returnValue.put("description", description);
		returnValue.put("active", data);
		returnValue.put("suggested", suggestedValues);
		
		return returnValue;
	}
	
	@Override
	public String toString() {
		return getData().toString();
	}
}
