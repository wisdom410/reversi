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
import java.util.Vector;

import sql.ExecSql;


import core.GenMD5;
import core.Room;
import core.User;


import net.ChatNet;
import net.CreateRoomNet;
import net.ExitRoomNet;
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
			
			Thread clearUser = new ClearUsers();
			clearUser.start();
			
			
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
	private static Vector<User> userList;
	private static Vector<Room> roomList;
	private final int MAXUSER = 10000;
	private int needRemoveNum = 0;
	
	
	class Server implements Runnable
	{
		
		public Server(Socket i)
		{
			incoming = i;
		}
		
		
		public void run()
		{
			
			try {
				
				
				
				in = new ObjectInputStream(new BufferedInputStream(incoming.getInputStream()));
				out = new ObjectOutputStream(new BufferedOutputStream(incoming.getOutputStream()));
				
			
				try{
					IDNet cmd = (IDNet) in.readObject();
					int cmdType = cmd.getID();
					
					
					switch (cmdType)
					{
					case 0://登录
					{
						LoginNet login = (LoginNet) cmd;
						String user = login.getUser().getUsername();
						char[] pass = login.getUser().getPasswd();
						
						String passwd = GenMD5.getMD5(pass);
						
						try {
							
							boolean isLogin = false;
							
							for(User u:userList)
							{
								if(u.getUsername().equals(user))
								{
									isLogin = true;
									break;
								}
							}
							
							if(isLogin)
							{
								login.setStatus(3);
								out.writeObject(login);
								out.flush();
								return;								
							}
							
							ResultSet result = ExecSql.state.executeQuery("SELECT * FROM users");
							//ResultSet result = ExecSql.state.executeQuery("SELECT * FROM Greetings");
							String nameinsql = "";
							String passwdinsql = "";
							int sex = 0;
							int score = 0;
							int image = 0;
							String email = null;
							String nickname = null;
							
							boolean find = false; 
							while(result.next())
							{
								nameinsql = result.getString(1);
								if(nameinsql.equals(user))
								{
									find = true;
									passwdinsql = result.getString(2);
									sex = Integer.parseInt(result.getString(3));
									score = Integer.parseInt(result.getString(6));
									image = Integer.parseInt(result.getString(7));
									email = result.getString(5);
									nickname = result.getString(4);
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
									User u = new User(user,sex,nickname,email,score,image);
									login.setUser(u);
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
								System.out.println(cmdStr);
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
//						roomList.add(new Room("server1"));
//						User sdlwwlp = new User("sdlwwlp",1,"will","sdlwwlp@163.com",1,1);
//						for(Room r:roomList)
//						{
//							if(r.getRoomName().equals("server1"))
//							{
//								r.addUser(sdlwwlp);
//								r.flushRoom();
//							}
//						}
						RoomListNet roo = (RoomListNet) cmd;
						
						boolean inUserList = false;
						for(User u:userList)
						{
							if(u.getUsername().equals(roo.getUser().getUsername()))
							{
								inUserList = true;
								break;
							}
						}
						
						if(!inUserList)
						{
							userList.add(roo.getUser());
						}
						
						RoomListNet net = new RoomListNet(roomList);
						out.writeObject(net);
						out.flush();
						
						
					break;
					}
					case 6://创建房间
					{
						CreateRoomNet cre = (CreateRoomNet) cmd;
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(cre.getRoomName()))
							{
								cre.setStatus(1);
								out.writeObject(cre);
								out.flush();
								return;
							}
						}
						
						Room room = new Room(cre.getRoomName());
						cre.getUser().setPlayer(true);
						
						cre.setStatus(0);
						room.addUser(cre.getUser());
						room.setPlayer1(cre.getUser());
						
						out.writeObject(cre);
						out.flush();
						
						roomList.add(room);
						
					break;
					}
					
					case 7://退出房间
					{
						
						ExitRoomNet exit = (ExitRoomNet) cmd;
//需要修改						
						int[] needRemove = new int[MAXUSER];
						needRemoveNum = 0;
						int num = 0;
						
						for(User u:userList)
						{
							
							if(u.getUsername().equals(exit.getUser().getUsername()))
							{
								needRemoveNum++;
								needRemove[needRemoveNum] = num;
							}
								num++;
						}
						
						for(int i = 1;i<=needRemoveNum;i++)
						{
							userList.remove(needRemove[i]);
						}

						int[] needRemoveRoom = new int[MAXUSER];
						int[] needRemoveUser = new int[MAXUSER];
						int needRemoveRoomNum = 0;
						int needRemoveUserNum = 0;
						
						int numRoom = 0;
						
						for(Room r:roomList)
						{
							Vector<User> ul = r.getUserList();
							needRemoveUserNum = 0;
							num = 0;
							for(User u:ul)
							{
								if(u.getUsername().equals(exit.getUser().getUsername()))
								{
									needRemoveUserNum++;
									needRemoveUser[needRemoveUserNum] = num;
								}
								num++;
							}
							
							for(int i = 1;i<=needRemoveUserNum;i++)
							{
								ul.remove(needRemoveUser[i]);
								
							}
							if(ul.isEmpty())
							{
								needRemoveRoomNum++;
								needRemoveRoom[needRemoveRoomNum] = numRoom;
							}
							
							numRoom++;
							
						}
						
						
						for(int i = 1;i<=needRemoveRoomNum;i++)
						{
							roomList.remove(needRemoveRoom[i]);
						}
						
						
					break;
					}
					
					
					}//end switch
				} catch (IOException e){
					e.printStackTrace();
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					out.close();
					in.close();
					incoming.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	
		private Socket incoming;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
	}
	
	/*
	 * 定时清除登录的用户列表，用于"心跳检测"
	 */
	class ClearUsers extends Thread 
	
	{
		public void run()
		{
			while(true)
			{
				userList.clear();
				try {
					sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
		
}
