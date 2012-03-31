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
		
		play1 = "";
		play2 = "";
		
		status = "等待玩家";
		
		
	}
	
	private void flushRoom()
	{
		play1 = "";
		play2 = "";
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
	
	public String getPlayer1()
	{
		return play1;
	}
	
	public String getPlayer2()
	{
		return play2;
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
	
	private String roomName;
	private String play1,play2;
	private String status;
	private int score1,score2;
	private int image1,image2;
	private boolean canview;
	private Vector<User> userList;
}
