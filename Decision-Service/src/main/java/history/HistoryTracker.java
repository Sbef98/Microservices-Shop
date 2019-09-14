package history;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;

import com.example.DecisionServiceSte.ServiceDetailsRequestModel;

public class HistoryTracker {
	public static void main(String[] args) throws SQLException {
	DatabaseConnection dbConn = new DatabaseConnection();
	Connection conn = DatabaseConnection.DBConnection();
	DatabaseConnection.AssignID();
	ResultSet res = DatabaseConnection.ExecQuery("SELECT * FROM Services");
	System.out.print(res.toString());
	
	dbConn.PrintResult(res, DBTablesModels.Services);
	}
	
	public boolean StoreProcedure(ServiceDetailsRequestModel service, Integer serviceId) {
		String sql = "INSERT INTO Service VALUES (" + service.getURI() + ", " 
													+ service.getPort() + ", "
													+ service.getGroupID() + ", "
													+ service.getDescription() + ", "
													+ service.getGet_mapping() + ", "
													+ service.getGet_putting() + ", "
													+ serviceId.toString() + 
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
