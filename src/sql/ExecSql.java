/*
ID: lazydom1
LANG: JAVA
TASK: execSql.java
Created on: 2012-3-26-上午9:29:03
Author: lazydomino[AT]163.com(pisces)
*/

package sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @author will
 * 这个是执行数据库命令的静态方法类
 */
public  class ExecSql {


	public ExecSql()
	{
		
	}
	
	public static void connected() throws IOException, SQLException
	{
		connect();
	}
	
	public static void close() throws SQLException
	{
		con.close();
	}
	
	private static void connect() throws IOException, SQLException
	{
		con = getConnection();
		state = con.createStatement();
	}
	
	
	private static Connection getConnection() throws IOException, SQLException
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
	
	
	//var
	public static Statement state;
	private static Connection con;
}
