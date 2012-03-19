/*
ID: lazydom1
LANG: JAVA
TASK: TestSql.java
Created on: 2012-3-19-上午9:00:00
Author: lazydomino@163.com(pisces)
*/

package SqlTest;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.io.ObjectInputStream.GetField;
public class TestSql {

	public static void main(String[] args) throws IOException, SQLException {
		
		
		runTest();
		
	}
	
	
	
	public static void runTest() throws IOException, SQLException
	{
		
		Connection con = getConnection();
		
		try
		{
			Statement state = con.createStatement();
			
			state.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
			state.executeUpdate("INSERT INTO Greetings VALUES('Hello world!')");
			
			ResultSet result = state.executeQuery("SELECT * FROM Greetings");
			if(result.next())
			{
				System.out.println(result.getString(1));
			}
			result.close();
			state.executeUpdate("DROP TABLE Greetings");
		}finally
		{
			con.close();
		}
		
	}
	
	
	public static Connection getConnection() throws IOException, SQLException
	{
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("config"+File.separator+"database.props");
		props.load(in);
		in.close();
		
		String drivers = props.getProperty("jdbc.drivers");
		if(drivers != null) System.setProperty("jdbc.drivers", drivers);
		
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String passwd = props.getProperty("jdbc.password");
		
		return DriverManager.getConnection(url, username, passwd);
		
	}
}
