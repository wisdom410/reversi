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
import java.util.*;
public class test {

	public static void main(String[] args) throws IOException, SQLException {
		
		Scanner cin = new Scanner(System.in);
		ExecSql execsql = new ExecSql();
		
	while(cin.hasNext())
	{
		String ab = cin.nextLine();
		String cmd = cin.nextLine();
		
		
		
		//execsql.state.execute("INSERT INTO Greetings VALUES('Hello world!')");
		if(ab.equals("q"))
		{
		ResultSet result = execsql.state.executeQuery(cmd);
		
		while(result.next())
		{
			System.out.println(result.getString(3));
		}
		}else
		{
			execsql.state.execute(cmd);
		}
	}
}
}
