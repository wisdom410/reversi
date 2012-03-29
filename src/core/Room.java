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

	public Room(String roomName)
	{
		this.roomName = roomName;
		
	}
	
	private String roomName;
	public Vector<User> userList;
}
