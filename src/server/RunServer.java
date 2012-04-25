/*
ID: lazydom1
LANG: JAVA
TASK: RunServer.java
Created on: 2012-3-27-下午4:25:29
Author: lazydomino[AT]163.com(pisces)
*/

package server;

import java.sql.SQLException;

/**
 * 
 * @author will
 * 这个是运行服务器的主类
 * 同时只允许运行一个服务器
 */
public class RunServer {
	
	public static void main(String[] args) throws SQLException {
		
		new ServerMain();
	}
}
