/*
ID: lazydom1
LANG: JAVA
TASK: ServerMain.java
Created on: 2012-3-12-上午8:20:04
Author: lazydomino@163.com(pisces)
*/

package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql.ExecSql;


import core.GenMD5;


import net.IDNet;
import net.LoginNet;

public class ServerMain{

	public ServerMain() throws SQLException
	{
		
		try {
			
			ServerSocket s = new ServerSocket(8090);
			ExecSql.connected();
			
			
			while(true)
			{
				
				linkednum++;
				
				Socket incoming = s.accept();
				Runnable r = new Server(incoming);
				Thread t = new Thread(r);
				t.start();
				
				System.out.println("Num "+linkednum+":");
				System.out.println("Connect From: "+
						s.getInetAddress().getHostName()+"\t"+
						s.getInetAddress().getHostAddress()
				);
					
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	private int linkednum = 0;
	
	
	class Server implements Runnable
	{
		
		public Server(Socket i)
		{
			incoming = i;
		}
		
		
		public void run()
		{
			
			try {
				
				
				
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(incoming.getInputStream()));
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(incoming.getOutputStream()));
				
			
				try{
					IDNet cmd = (IDNet) in.readObject();
					int cmdType = cmd.getID();
					
					switch (cmdType)
					{
					case 0://登录
					{
						LoginNet login = (LoginNet) cmd;
						String user = login.getUsername();
						char[] pass = login.getPasswd();
						
						String passwd = GenMD5.getMD5(pass);
						
						try {
							
							ResultSet result = ExecSql.state.executeQuery("SELECT * FROM users");
							//ResultSet result = ExecSql.state.executeQuery("SELECT * FROM Greetings");
							String nameinsql;
							String passwdinsql = "";
							
							boolean find = false; 
							while(result.next())
							{
								nameinsql = result.getString(1);
								if(nameinsql.equals(user))
								{
									find = true;
									passwdinsql = result.getString(2);
									
								}
							}
							
							if(!find)
							{
								System.out.println("not find");
								login.setStatus(1);
								out.writeObject(login);
								out.flush();
							}else
							{
								if(passwdinsql.equals(passwd))
								{
									login.setStatus(0);
									out.writeObject(login);
									out.flush();
								}else
								{
									login.setStatus(1);
									out.writeObject(login);
									out.flush();
								}
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
					}
				} catch (IOException e){
					e.printStackTrace();
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
				finally
				{
					incoming.close();
					
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		private Socket incoming;
	}
	
	
	
		
}
