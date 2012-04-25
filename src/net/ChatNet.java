/*
ID: lazydom1
LANG: JAVA
TASK: ChatNet.java
Created on: 2012-3-29-下午6:45:11
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import core.Room;
import core.User;

/**
 * @author will
 * ID = 4
 * 这个类是用来封装聊天信息的
 * status：
 */
public class ChatNet extends IDNet{

	public ChatNet(User user,Room r, String msg)
	{
		super(4);
	//	this.user = user;
		
		this.user = user;
		this.room = r;
		this.msg =msg;
		
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


	private User user;
	private Room room;
	private String msg;
}
