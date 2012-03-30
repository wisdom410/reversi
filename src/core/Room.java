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
		
		status = "默认";
		
		
	}
	
	public void flushRoom()
	{
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
	
	private String roomName;
	private String play1,play2;
	private String status;
	private int score1,score2;
	private int image1,image2;
	private boolean canview;
	private Vector<User> userList;
}
