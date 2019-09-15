package history;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import org.json.JSONArray;

import com.example.DecisionServiceSte.ServiceDetailsRequestModel;

public class HistoryTracker {
	public static void main(String[] args) throws SQLException {
	DatabaseConnection dbConn = new DatabaseConnection();
	DatabaseConnection.DBConnection();
	boolean chk = DatabaseConnection.checkDataType("hudimity");
	boolean ins = DatabaseConnection.insertDataType("humidity");
	ins = DatabaseConnection.insertDataType("person");
	ResultSet services = DatabaseConnection.ExecQuery("SELECT * FROM Services;");
	JSONArray servicesJson = DatabaseConnection.convert(services);
	System.out.println(servicesJson.toString());
	ResultSet allData = DatabaseConnection.ExecQuery();
	JSONArray allDataJson = DatabaseConnection.convert(allData);
	System.out.println(allDataJson.toString());
	DatabaseConnection.AssignID();
	ResultSet res = DatabaseConnection.ExecQuery("SELECT * FROM Services");
	System.out.print(res.toString());
	
	dbConn.PrintResult(res, DBTablesModels.Services);
	}
	
	public static boolean storeProcedure(ServiceDetailsRequestModel service, String serviceId) {
		String sql = "INSERT INTO Services VALUES (" + "'" + service.getURI() + "'" + ", " 
													+ "'" + service.getPort() + "'" + ", "
													+ "'" + service.getName() + "'" + ", "
													+ "'" + service.getGroupID() + "'" + ", "
													+ "'" + service.getDescription() + "'" + ", "
													+ "'" + service.getGet_mapping() + "'" + ", "
													+ "'" + service.getPut_mapping() + "'" + ", "
													+ "'" + serviceId + "'" + 
													" );";
		try{
			Integer x = DatabaseConnection.ExecUpdate(sql);
		} catch (Exception e) {
			return false;
		}
		Date date = new Date();
		Object timestamp = new Timestamp(date.getTime());
		Iterator<String> it = service.getValues().keys();
		while (it.hasNext()) {
			String dataType = it.next();
			JSONArray values = (JSONArray) service.getValues().get(dataType);
			for (Object val : values) {
				String sql2 = new String("INSERT INTO ServicesValues VALUES ( " + "'"+ (String)val + "'" + ", " 
																	+ "'" + dataType + "'" + ", "
																	+ "'" + timestamp + "'" + ", "
																	+ "'" + serviceId.toString() + "'"
																	+ ");"
																	);
				try{
					DatabaseConnection.ExecUpdate(sql2);
				} catch (Exception e) {
					return false;
				}
			}
		}		
		return true;
	}
}
