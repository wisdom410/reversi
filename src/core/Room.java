/*
ID: lazydom1
LANG: JAVA
TASK: Room.java
Created on: 2012-3-29-下午8:42:19
Author: lazydomino[AT]163.com(pisces)
*/

package core;

import java.util.Vector;

public class Room {

	public Room(String roomName,Vector<User> userList)
	{
		this.roomName = roomName;
		
		this.userList = userList;
		play1 = "";
		play2 = "";
		
		
		for(User u:userList)
		{
			if(u.isPlayer())
			{
				if(play1.length() == 0)
				{
					play1 = u.getUsername();
					score1 = u.getScore();
					image1 = u.getImage();
				}
				if(play2.length() == 0)
				{
					play2 = u.getUsername();
					score2 = u.getScore();
					image2 = u.getImage();
				}
			}
		}
		
		
	}
	
	public void setcanView(boolean can)
	{
		this.canview = can;
	}
	
	public boolean iscanView()
	{
		return this.canview;
	}
	
	public void addUser(User user)
	{
		userList.add(user);
	}
	
	public int getnum_pep()
	{
		return userList.size();
	}
	
	public void delUser(User user)
	{
		userList.remove(user);
	}
	
	private String roomName;
	private String play1,play2;
	private String status;
	private int score1,score2;
	private int image1,image2;
	private boolean canview;
	private Vector<User> userList;
}
