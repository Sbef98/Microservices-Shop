package history;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class HistoryTracker {
	public static void main(String[] args) throws SQLException {
	DatabaseConnection dbConn = new DatabaseConnection();
	Connection conn = dbConn.DBConnection();
	ResultSet res = dbConn.ExecQuery(conn, "SELECT * FROM Services");
	dbConn.PrintResult(res, DBTablesModels.Services);
	}
}
