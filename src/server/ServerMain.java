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
import java.util.HashMap;
import java.util.Vector;

import sql.ExecSql;


import core.GenMD5;
import core.Room;
import core.User;


import net.ChatNet;
import net.IDNet;
import net.LoginNet;
import net.RegNet;
import net.RoomListNet;

public class ServerMain{

	public ServerMain() throws SQLException
	{
		
		try {

			userList = new Vector<User>();
			roomList = new Vector<Room>();
			
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
	
	private void sendObject(int room,ChatNet chat) throws IOException
	{
		ObjectInputStream in;
		ObjectOutputStream out;
		for(User u:userList)
		{
			if(u.getRoom()==room)
			{
				in = u.getIn();
				out = u.getOut();
				out.writeObject(chat);
				out.flush();
			}
		}
	}
	
	private int linkednum = 0;
	private static Vector<User> userList;
	private static Vector<Room> roomList;
	
	
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
					
					System.out.println(cmdType);
					
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
//									login.getUser().setRoom(1);
//									userList.add(login.getUser());
									
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
						
					break;	
					}
					
					case 1://注册
					{
						RegNet reg = (RegNet) cmd;
						
						
						String username = reg.getUsername();
						char[] pass = reg.getPasswd();
						int sex = reg.getSex();
						String nickname = reg.getNickname();
						String email = reg.getEmail();
						int score = reg.getScore();
						int image = reg.getImage();
						String passwd = GenMD5.getMD5(pass);
						
						try {
							
							ResultSet result = ExecSql.state.executeQuery("SELECT * FROM users");
							String nameinsql;
							String passwdinsql = "";
							
							boolean find = false; 
							while(result.next())
							{
								nameinsql = result.getString(1);
								if(nameinsql.equals(username))
								{
									find = true;
									passwdinsql = result.getString(2);
									
								}
							}
							
							if(find)
							{
								System.out.println("this user already register!");
								reg.setStatus(1);
								out.writeObject(reg);
								out.flush();
							}else
							{
								String cmdStr = "INSERT INTO users VALUES("+"\'"+username+"\',\'"+passwd+"\',"+sex+",\'"+nickname+"\',\'"+email+"\',"+score+","+image+")";
								ExecSql.state.execute(cmdStr);
								System.out.println(username+" register success!");
								reg.setStatus(0);
								out.writeObject(reg);
								out.flush();
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
						
						
					break;
					}
					
					case 5://房间列表
					{
						out.writeObject(new RoomListNet(roomList));
						out.flush();
						
					break;
					}
					
					
					
					}//end switch
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
