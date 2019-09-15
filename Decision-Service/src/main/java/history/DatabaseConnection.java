/**
 * 
 */
package history;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Manage connection to RDBMS;
 */
public class DatabaseConnection {
	protected static Connection conn = null;
	static Statement cmd = null;

	public static Connection DBConnection() {
		// Creiamo la stringa di connessione
		String url = "jdbc:mysql://212.237.20.175:3306/DB_Bicocco?serverTimezone=GMT";
		// Otteniamo una connessione con username e password
		try {
			conn = DriverManager.getConnection (url, "Leo", "LeoMirots15");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore nella connessione al DB");
		}
		return conn;
	}
	
	public static ResultSet ExecQuery(String query) {
	// Creiamo un oggetto Statement per poter interrogare il db;
		try {
			cmd = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Impossibile creare lo statement per la query");
		}
	 // Eseguiamo una query e immagazziniamone i risultati;
	 // in un oggetto ResultSet;
		ResultSet res = null;
		try {
			res = cmd.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore nell'esecuzione della query");
		}
		return res;
	}
	
	public static Integer ExecUpdate(String query) {
		// Creiamo un oggetto Statement per poter interrogare il db;
			try {
				cmd = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Impossibile creare lo statement per la query");
			}
		 // Eseguiamo una query e immagazziniamone i risultati;
		 // in un oggetto ResultSet;
			Integer res = null;
			try {
				res = cmd.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Errore nell'esecuzione della query");
			}
			return res;
		}
	
	public void PrintResult(ResultSet res, LinkedList<String> columnName) throws SQLException{
		while (res.next()) {
			for(String column : columnName) {
				System.out.println(res.getString(column));
			}
		}
		res.close();
		cmd.close();
		conn.close();
	}

	public static Integer AssignID() {
		// TODO Auto-generated method stub
		ResultSet res = DatabaseConnection.ExecQuery("SELECT MAX(ServiceId) AS ServiceId FROM Services");
		try {
			res.next();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Integer currentIndex = 0;
		try {
			currentIndex = res.getInt("ServiceId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentIndex + 1;
	}
	
	public static boolean checkDataType(String dataName) throws SQLException {
		ResultSet chk = ExecQuery("SELECT * FROM DataType WHERE (DataName = '" + dataName + "');");
		while (chk.next()) {
			return true;
		}
		return false;
	}
}


