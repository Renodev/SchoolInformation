package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/si_db";
	
	private static String username = "root";
	
	private static String password = "mysql123";
	
	public static Connection getDBConnection () {
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (Exception e) {
			return null;
		}
	}
	
}
