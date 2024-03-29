package history;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.DecisionServiceSte.ServiceDetailsRequestModel;

public class HistoryTracker {
	
	private static int isSensorDB(boolean isSensor) {
		if (isSensor = true) {
			return 1;
		}
		else return 0;
	}
	
	public static boolean storeProcedure(ServiceDetailsRequestModel service, String serviceId) {
		int isSensor = isSensorDB(service.isSensor());
		String sql = "INSERT INTO Services VALUES ( '" + serviceId + "'" + ", " 
													+ "'" + service.getURI() + "'" + ", " 
													+ "'" + service.getPort() + "'" + ", "
													+ "'" + service.getName() + "'" + ", "
													+ "'" + service.getGroupID() + "'" + ", "
													+ "'" + service.getDescription() + "'" + ", "
													+ "'" + service.getGet_mapping() + "'" + ", "
													+ "'" + service.getPut_mapping() + "'" + ", "
													+ "'" + isSensor + "' );";
		try {
			DatabaseConnection.ExecUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();;
		}
		Date date = new Date();
		Object timestamp = new Timestamp(date.getTime());
		Iterator<String> it = service.getValues().keys();
		while (it.hasNext()) {
			String dataType = it.next();
			JSONArray values = (JSONArray) service.getValues().get(dataType);
			for (Object val : values) {
				String sql2 = new String("INSERT INTO ServicesValues VALUES ( " + "'"+ val.toString() + "'" + ", "
																	+ "'" + dataType + "'" + ", "
																	+ "'" + timestamp + "'" + ", "
																	+ "'" + serviceId.toString() + "'"
																	+ ");"
																	);
				try{
					DatabaseConnection.ExecUpdate(sql2);
				} catch (Exception e) {
					;
				}
			}
			
		}
		if(service.getWanted() != null) {
			for (String key : service.getWanted().keySet()) {
				String sql3 = new String("INSERT INTO ServicesWanted VALUES ( " + "'"+  service.getWanted().get(key).toString() + "'" + ", "
																	+ "'" + key + "'" + ", "
																	+ "'" + timestamp + "'" + ", "
																	+ "'" + serviceId.toString() + "'"
																	+ ");"
																	);
				try{
					DatabaseConnection.ExecUpdate(sql3);
				} catch (Exception e) {
					e.printStackTrace();;
				}
			}
		}
		return true;
	}
	
	public static boolean checkDataType(String dataName) 
	{
		try {
			ResultSet chk = DatabaseConnection.ExecQuery("SELECT * FROM DataType WHERE (DataName = '" + dataName + "');");
			while (chk.next()) {
				return true;
			}
		}catch (SQLException e) {
			/*It means it was unbale to do the queary*/
			System.out.println("Unable to execute the query in checkDataType!!");
			return false;
		}
		return false;
	}
	
	public static boolean insertDataType(String dataName) {
		Integer x = null;
		try {
			x = DatabaseConnection.ExecUpdate("INSERT INTO DataType VALUES	('" + dataName + "');");
		} catch (SQLException e) {
			System.out.println("This DataType already existing");
		}
		if (x != null)
			return true;
		return false;
	}
	
	public static JSONArray query(String query) {
		ResultSet res = null;
		if(query == null) {
			query = "SELECT * FROM ServicesValues SV JOIN Services S ON SV.SensorOrigin = S.ServiceId JOIN ServicesWanted SW ON S.ServiceID = SW.SensorOrigin AND SV.Timestamp = SW.Timestamp ORDER BY SV.Timestamp;";	
		}
		try {
			res = DatabaseConnection.ExecQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			return DatabaseConnection.convert(res);
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	 }
	
	 public static void garbage() {
		 String query1 = "DELETE FROM ServicesValues WHERE Timestamp < DATE_SUB(NOW(), INTERVAL 2 DAY);";
		 String query2 = "DELETE FROM Services WHERE ServiceId NOT IN ( SELECT DISTINCT SensorOrigin FROM ServicesValues );";
		 
		 try {
			DatabaseConnection.ExecUpdate(query1);
			DatabaseConnection.ExecUpdate(query2);
		} catch (SQLException e) {
			;
		}
	 }
}
