package rdv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConn {
	private static final String URL = "jdbc:mysql://localhost:3306/remsdb";
	private static final String USER = "root";
	private static final String PSW = "";
	
	public static Connection getConn() throws Exception {
		return DriverManager.getConnection(URL, USER, PSW);
	}
}
