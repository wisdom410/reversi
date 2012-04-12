/*
ID: lazydom1
LANG: JAVA
TASK: Room.java
Created on: 2012-3-29-下午8:42:19
Author: lazydomino[AT]163.com(pisces)
*/

package core;

import java.io.Serializable;
import java.util.Vector;

public class Room  implements Serializable{

	public Room(String roomName)
	{
		this.roomName = roomName;
		
		userList = new Vector<User>();
		chat ="";
		
		
		play1 = "";
		play2 = "";
		
		status = "等待玩家";
		
		player1Ready = player2Ready = false;
		
		canview = true;
		
		chessManList = new ChessManList();
		
		chessManList.add(4, 4, false);
		chessManList.add(4, 5, true);
		chessManList.add(5, 4, true);
		chessManList.add(5, 5, false);
		black ="";

		
	}
	
	
	private void flushRoom()
	{
		//play1 = "";
		//play2 = "";
		score1 = 0;
		score2 = 0;
		image1 = 0;
		image2 = 0;
		
		for(User u:userList)
		{
			if(u.isPlayer())
			{
				if(play1.length()==0)
				{
					play1 = u.getUsername();
					score1 = u.getScore();
					image1 = u.getImage();
				}else
				{
					if(play2.length()==0)
					{
						play2 = u.getUsername();
						score2 = u.getScore();
						image2 = u.getImage();
					}
				}
				
			}
		}
	}
	
	public void setcanView(boolean can)
	{
		this.canview = can;
	}
	
	public boolean getcanView()
	{
		return this.canview;
	}
	
	public void addUser(User user)
	{
		userList.add(user);
		flushRoom();
	}
	
	public int getnum_pep()
	{
		return userList.size();
	}
	
	public void delUser(User user)
	{
		userList.remove(user);
		flushRoom();
	}
	
	public String getRoomName()
	{
		return roomName;
	}
	public Vector<User> getUserList()
	{
		return userList;
	}
	
	public int getScore1()
	{
		return score1;
	}
	
	public int getScore2()
	{
		return score2;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String s)
	{
		status = s;
	}
	public String getPlayer1()
	{
		return play1;
	}
	
	public String getPlayer2()
	{
		return play2;
	}
	
	public boolean isPlayer1isEmpty()
	{
		if(play1.length()==0)
			return true;
		return false;
	}
	
	public boolean isPlayer2isEmpty()
	{
		if(play2.length()==0)
			return true;
		return false;
	}
	
	public void setPlayer1(User user)
	{
		this.play1 = user.getUsername();
		this.score1 = user.getScore();
		this.image1 = user.getImage();
	}
	
	public void setPlayer2(User user)
	{
		this.play2 = user.getUsername();
		this.score2 = user.getScore();
		this.image2 = user.getImage();
	}
	
	public void setChessManList (ChessManList c)
	{
		this.chessManList = c;
	}
	
	public ChessManList getChessManList()
	{
		return this.chessManList;
	}
	
	public void setBlack(String s)
	{
		this.black = s;
	}
	
	public String getBlack()
	{
		return this.black;
	}
	
	public void setPlayer1Ready(boolean b)
	{
		player1Ready = b;
	}
	public boolean getPlayer1Ready()
	{
		return player1Ready;
	}
	
	public void setPlayer2Ready(boolean b)
	{
		player2Ready = b;
	}
	public boolean getPlayer2Ready()
	{
		return player2Ready;
	}
	
	public int getImage1()
	{
		return image1;
	}
	
	public int getImage2()
	{
		return image2;
	}
	
//	public String getChat()
//	{
//		return chat;
//	}
//	
//	public void addChat(String s)
//	{
//		this.chat+=s;
//		System.out.println("Sth. add "+s);
//	}
	
	private String roomName;
	private String play1,play2;
	private boolean player1Ready,player2Ready;
	private String status;
	private int score1,score2;
	private int image1,image2;
	private boolean canview;
	private Vector<User> userList;
	private ChessManList chessManList;
	private String black;
	public String chat;
}
