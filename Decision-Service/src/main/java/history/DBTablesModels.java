/**
 * 
 */
package history;

import java.util.LinkedList;

/**
 * 
 */
public class DBTablesModels {
	static LinkedList<String> Services = new LinkedList<String>();
	static LinkedList<String> ServicesValues = new LinkedList<String>();
	static LinkedList<String> ServicesWanted = new LinkedList<String>();

	
	public static LinkedList<String> ServicesTableModel(){
		Services.add("URI");
		Services.add("Port");
		Services.add("Name");
		Services.add("GroupId");
		Services.add("Description");
		Services.add("GetMapping");
		Services.add("PutMapping");
		Services.add("ServiceId");
		Services.add("IsSensor");
		return Services;
	}
	
	public static LinkedList<String> ServicesValuesTableModel(){
		ServicesValues.add("Values");
		ServicesValues.add("DataType");
		ServicesValues.add("Timestamp");
		ServicesValues.add("SensorOrigin");
		return ServicesValues;
	}
	
	public static LinkedList<String> ServicesWantedTableModel(){
		ServicesWanted.add("Wanted");
		ServicesWanted.add("DataType");
		ServicesWanted.add("Timestamp");
		ServicesWanted.add("SensorOrigin");
		return ServicesWanted;
	}
}
