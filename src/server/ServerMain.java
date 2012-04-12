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
import net.ChessFrameNet;
import net.CreateRoomNet;
import net.ExitRoomNet;
import net.IDNet;
import net.JoinRoomNet;
import net.LoginNet;
import net.Ready;
import net.RegNet;
import net.RoomListNet;
import net.ViewRoomNet;

public class ServerMain{

	public ServerMain() throws SQLException
	{
		
		try {

			userList = new Vector<User>();
			roomList = new Vector<Room>();
			
			ServerSocket s = new ServerSocket(8090);
			ExecSql.connected();
			
			Thread clearUser = new ClearUsers();
			//clearUser.start();
			
			
			while(true)
			{
				
				linkednum++;
				if(Long.MAX_VALUE==linkednum)
				{
					linkednum=0;
					System.out.println("This server can't server for so much users!");
					
				}
				
				Socket incoming = s.accept();
				Runnable r = new Server(incoming);
				Thread t = new Thread(r);
				t.start();
				
				//System.out.println("Num "+linkednum+":");
				//System.out.println("Connect From: "+
				//		s.getInetAddress().getHostName()+"\t"+
			//			s.getInetAddress().getHostAddress()
				//);
					
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/*
	 * 用于验证此用户是否已经在某房间里了
	 */
	private boolean isInRoom(User usr)
	{
		for(Room r:roomList)
		{
			Vector<User> ul = r.getUserList();
			for(User u:ul)
			{
				if(u.getUsername().equals(usr.getUsername()))
					return true;
			}
		}
		
		return false;
	}
	/*
	 * 用来从房间或大厅中清除用户,清除没有玩家的房间
	 */
	private void removeUser(User user)
	{
		int[] needRemove = new int[MAXUSER];
		needRemoveNum = 0;
		int num = 0;
		
		for(User u:userList)
		{
			
			if(u.getUsername().equals(user.getUsername()))
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
				if(u.getUsername().equals(user.getUsername()))
				{
					needRemoveUserNum++;
					needRemoveUser[needRemoveUserNum] = num;
				}
				num++;
			}
			
			for(int i = 1;i<=needRemoveUserNum;i++)
			{
				if(ul.get(needRemoveUser[i]).getUsername().equals(r.getPlayer1()))
				{
					r.setPlayer1(new User("",3,"","",0,0));
				}
				if(ul.get(needRemoveUser[i]).getUsername().equals(r.getPlayer2()))
				{
					r.setPlayer2(new User("",3,"","",0,0));
				}
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
		needRemoveRoomNum = 0;
		num = 0;
		
		for(Room r:roomList)
		{
			if(r.getPlayer1().equals("")&&r.getPlayer2().equals(""))
			{
				needRemoveRoomNum++;
				needRemoveRoom[needRemoveRoomNum] = num;
			}
			num++;
		}
		
		for(int i = 1;i<=needRemoveRoomNum;i++)
		{
			roomList.remove(needRemoveRoom[i]);
		}
		
	}
	
	private void upDateRoomList(Room room)
	{
		for(Room r:roomList)
		{
			if(r.getRoomName().equals(room.getRoomName()))
			{
				r = room;
				System.out.println("updateRoom"+r.getRoomName());
				return;
			}
		}
	}
	
	
	
	private long linkednum = 0;
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
					
					case 4://客户端向服务器发送聊天信息
					{
						ChatNet chatNet = (ChatNet) cmd;
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(chatNet.getRoom().getRoomName()))
							{
								r.chat+=chatNet.getUser().getUsername()+" 说："+chatNet.getMsg()+"\n";
								return;
							}
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
						
						if(isInRoom(cre.getUser()))
						{
							cre.setStatus(2);
							out.writeObject(cre);
							out.flush();
							return;
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
						System.out.println("received exit:"+exit.getUser().getUsername());

						removeUser(exit.getUser());
						
						
					break;
					}
					
					case 8://加入房间
					{
						JoinRoomNet join = (JoinRoomNet) cmd;
						
						if(isInRoom(join.getUser()))
						{
							join.setStatus(1);
							out.writeObject(join);
							out.flush();
							return;
						}
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(join.getRoom().getRoomName()))
							{
								r.addUser(join.getUser());
								
								if(r.getPlayer1().length()==0)
									r.setPlayer1(join.getUser());
								else
									r.setPlayer2(join.getUser());
								
								join.setStatus(0);
								out.writeObject(join);
								out.flush();
								return;
							}
						}
						
					break;
					}
					
					case 9://申请观看当前房间
					{
						ViewRoomNet view = (ViewRoomNet) cmd;
						
						if(isInRoom(view.getUser()))
						{
							view.setStatus(2);
							out.writeObject(view);
							out.flush();
							return;
						}
						
						if(!view.getRoom().getcanView())
						{
							view.setStatus(1);
							out.writeObject(view);
							out.flush();
							return;
						}
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(view.getRoom().getRoomName()))
							{
								r.addUser(view.getUser());
								
								view.setStatus(0);
								out.writeObject(view);
								out.flush();
								return;
							}
						}
						
					break;	
					}
					
					case 10://获取房间内信息
					{
						ChessFrameNet chess = (ChessFrameNet) cmd;
						
						
						//System.out.println("receieved from client "+chess.getRoom().chat);
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(chess.getRoom().getRoomName()))
							{
								
								
								if(chess.getStatus()==0)
								{
									chess = new ChessFrameNet(r);
									
									out.writeObject(chess);
									out.flush();
									
									return;
								}
								
								if(chess.getStatus()==1)
								{
									r.setChessManList(chess.getRoom().getChessManList());
									
									if(r.getNext().equals(r.getPlayer1()))
									{
										r.setNext(r.getPlayer2());
									}else
									{
										r.setNext(r.getPlayer1());
									}
								}
								
							}
						}
						
					break;	
					}
//需要修改					
					case 11://告诉服务器点击了准备按钮
					{
						Ready ready = (Ready) cmd;

						
						System.out.println(ready.getU().getUsername());
						System.out.println(ready.getR().getRoomName());
						
						for(Room r:roomList)
						{
							if(r.getRoomName().equals(ready.getR().getRoomName()))
							{
								if(r.getPlayer1().equals(ready.getU().getUsername()))
								{
									r.setPlayer1Ready(true);
								}
								if(r.getPlayer2().equals(ready.getU().getUsername()))
								{
									r.setPlayer2Ready(true);
								}
								
								if(r.getPlayer1Ready()&&r.getPlayer2Ready())
								{
									
									r.chat+="开始游戏！\n";
									
									int a = (int) Math.random()*1000;
									
									if(a%2==0)
									{
										r.setNext(ready.getR().getPlayer1());
										r.setBlack(ready.getR().getPlayer1());
										r.chat+=r.getPlayer1()+" 是 先手黑棋\n";
										r.chat+=r.getPlayer2()+" 是 后手白棋\n";
									}else
									{
										r.setNext(ready.getR().getPlayer2());
										r.setBlack(ready.getR().getPlayer2());
										r.chat+=r.getPlayer2()+" 是 先手黑棋\n";
										r.chat+=r.getPlayer1()+" 是 后手白棋\n";
									}
									
									
									r.setStatus("游戏中...");
									//System.out.println(ready.getR().chat);
									
									
									
								}else
								{
									r.setStatus("等待玩家");
								}
								
								
								upDateRoomList(ready.getR());
					
						break;
							}
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
