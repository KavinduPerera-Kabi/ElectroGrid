package net.electrogrid.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
	
public static final String URL = "jdbc:mysql://localhost:3306/electrogrid";
	
	private static Connection connection;
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection==null || connection.isClosed()) {
			Class.forName("com.mysql.jdbc.Driver");			
			connection = DriverManager.getConnection(DatabaseUtils.URL,"root","root");		
					
		}
		
		return connection;
	}

}
