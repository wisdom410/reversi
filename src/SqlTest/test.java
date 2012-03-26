/*
ID: lazydom1
LANG: JAVA
TASK: test.java
Created on: 2012-3-26-上午9:36:58
Author: lazydomino[AT]163.com(pisces)
*/

package SqlTest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql.ExecSql;

public class test {

	public static void main(String[] args) throws IOException, SQLException {
		
		ExecSql execsql = new ExecSql();
		
		execsql.state.execute("INSERT INTO Greetings VALUES('Hello world!')");
		
		ResultSet result = execsql.state.executeQuery("SELECT * FROM Greetings");
		
		while(result.next())
		{
			System.out.println(result.getBytes(1));
		}
	}
}
