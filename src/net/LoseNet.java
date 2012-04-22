package net;

import core.Room;
import core.User;

/*
 * 这个类是来通信认输信息的
 */
public class LoseNet extends IDNet{

	
	public LoseNet(Room r,User u)
	{
		super(3);
		this.user = u;
		this.room = r;
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


	private User user;
	private Room room;
}
