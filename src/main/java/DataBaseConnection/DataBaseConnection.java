package DataBaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

	public static Connection con;
	private static final String url="jdbc:sqlserver://DESKTOP-D43V7LP\\SQLEXPRESS:1433;databaseName=testDB";
	private static final String userName="bashar";
	private static final String password="123";
	public static Connection DataBaseConnect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			 con = DriverManager.getConnection(url, userName, password);
			
		}catch(Exception e) {
			System.out.println(e);
			
			
		}
		return con;

		
	}
	

}
