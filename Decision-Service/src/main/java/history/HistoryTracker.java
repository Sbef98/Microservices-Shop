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
	DatabaseConnection.AssignID();
	ResultSet res = DatabaseConnection.ExecQuery("SELECT * FROM Services");
	System.out.print(res.toString());
	
	dbConn.PrintResult(res, DBTablesModels.Services);
	}
	
	public static boolean storeProcedure(ServiceDetailsRequestModel service, String serviceId) {
		String sql = "INSERT INTO Service VALUES (" + service.getURI() + ", " 
													+ service.getPort() + ", "
													+ service.getName() + ", "
													+ service.getGroupID() + ", "
													+ service.getDescription() + ", "
													+ service.getGet_mapping() + ", "
													+ service.getGet_putting() + ", "
													+ serviceId + 
													" );";
		try{
			DatabaseConnection.ExecQuery(sql);
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
				String sql2 = ("INSERT INTO ServicesValues VALUES (" 	+ (String)val + ", " 
																	+ dataType + ", "
																	+ timestamp + ", "
																	+ serviceId.toString()
																	+ ");"
																	);
				try{
					DatabaseConnection.ExecQuery(sql2);
				} catch (Exception e) {
					return false;
				}
			}
		}		
		return true;
	}
}
