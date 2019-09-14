package history;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.example.DecisionServiceSte.ServiceDetailsRequestModel;

public class HistoryTracker {
	public static void main(String[] args) throws SQLException {
	DatabaseConnection dbConn = new DatabaseConnection();
	Connection conn = DatabaseConnection.DBConnection();
	
	StoreProcedure()
	ResultSet res = dbConn.ExecQuery("SELECT * FROM Services");
	System.out.print(res.toString());
	
	dbConn.PrintResult(res, DBTablesModels.Services);
	}
	
	public boolean StoreProcedure(ServiceDetailsRequestModel service, Integer serviceId) {
		String tmp = service.getURI();
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
		
		return true;
	}
}
