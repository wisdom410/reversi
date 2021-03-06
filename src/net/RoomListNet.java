/*
ID: lazydom1
LANG: JAVA
TASK: RoomListNet.java
Created on: 2012-3-29-下午8:54:19
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import java.util.Vector;

import core.Room;
import core.User;

/**
 * @author will
 * 这个类是传输房间列表的类
 * status：0->请求获取房间列表，1->回复房间列表
 */
public class RoomListNet extends IDNet{

	public RoomListNet(Vector<Room> roomList)
	{
		super(5);
		this.roomList = roomList;
	}
	
	public RoomListNet(User u)
	{
		super(5);
		this.user = u;
	}
	
	public Vector<Room> getRoomList()
	{
		return roomList;
	}
	
	public User getUser()
	{
		return user;
	}
	
	private Vector<Room> roomList;
	private User user;
	
}
