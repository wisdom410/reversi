/*
ID: lazydom1
LANG: JAVA
TASK: CreateRoomNet.java
Created on: 2012-3-30-下午9:00:32
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import core.User;

/**
 * @author will
 * 这个类是用来向server请求创建房间的
 * status :0->创建成功,1->已经有此房间,2->用户已经在某房间，不允许建立房间
 */
public class CreateRoomNet extends IDNet{

	public CreateRoomNet(String roomName,User user)
	{
		super(6);
		this.roomName = roomName;
		this.user = user;
	}
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}



	private User user;
	private String roomName;
}
