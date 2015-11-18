import java.sql.Connection;
import java.sql.DriverManager;

import MyDBInfo;


public class DBConnection 
{
	private  Connection conn;
	
	/**
	 * Constructor. Creates a connection string and uses this string to connect to the  database
	 * using the credentials and information provided in MyDBInfo.java. 
	 */
	public DBConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionString = "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER + "/" + MyDBInfo.MYSQL_DATABASE_NAME;
			this.conn = DriverManager.getConnection(connectionString, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
		} 
		catch (Exception ex)
		{
			System.out.println("Error getting connection to database.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns a connection to the database, allowing caller to interact
	 * with records.
	 * @return Connection to the database
	 */
	public Connection getConnection()
	{
		return this.conn;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public  void close()
	{
		try {
			this.conn.close();
		}
		catch (Exception ex)
		{
			System.out.println("Error closing connection.");
			ex.printStackTrace();
		}
	}
	
}
