/*
ID: lazydom1
LANG: JAVA
TASK: UndoChessClass.java
Created on: 2012-3-28-上午10:56:22
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import core.Room;
import core.User;

/**
 * 
 * @author will
 * 这个是传输悔棋信息的类
 */
public class UndoChessNet extends IDNet{

	public UndoChessNet(Room r,User u)
	{
		super(12);
		this.room = r;
		this.user = u;
	}

	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	private Room room;
	private User user;
}
