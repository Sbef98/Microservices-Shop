/**
 * 
 */
package history;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static ResultSet ExecQuery(String query) throws SQLException {
		cmd = conn.createStatement();
		ResultSet res = null;
		res = cmd.executeQuery(query);
		return res;
	}
	
	protected static Integer ExecUpdate(String query) throws SQLException {
			cmd = conn.createStatement();
			Integer res = null;
			res = cmd.executeUpdate(query);
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
		ResultSet res = null;
		try {
			res = DatabaseConnection.ExecQuery("SELECT MAX(ServiceId) AS ServiceId FROM Services");
			res.next();
		} catch (SQLException e1) {
			;
		}
		Integer currentIndex = 0;
		try {
			currentIndex = res.getInt("ServiceId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentIndex + 1;
	}
	
	public static JSONArray convert( ResultSet rs ) throws SQLException, JSONException {
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
	
		while(rs.next()) {
			int numColumns = rsmd.getColumnCount();
		    JSONObject obj = new JSONObject();
	
		    for (int i=1; i<numColumns+1; i++) {
		    	String column_name = rsmd.getColumnName(i);
	
				if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
					obj.put(column_name, rs.getArray(column_name));
			    }
				else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
					obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
					obj.put(column_name, rs.getBoolean(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
					obj.put(column_name, rs.getBlob(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
				   obj.put(column_name, rs.getDouble(column_name)); 
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
					obj.put(column_name, rs.getFloat(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
					obj.put(column_name, rs.getInt(column_name));
			    }
			    else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
			    	obj.put(column_name, rs.getNString(column_name));
			    }
				else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
				    obj.put(column_name, rs.getString(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
				    obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
				    obj.put(column_name, rs.getInt(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
				    obj.put(column_name, rs.getDate(column_name));
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
				    obj.put(column_name, rs.getTimestamp(column_name));   
				}
				else{
					obj.put(column_name, rs.getObject(column_name));
				}
		    }
		    	json.put(obj);
	    }
	    return json;
	}
}


