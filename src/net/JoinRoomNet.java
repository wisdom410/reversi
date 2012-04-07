package net;

import core.Room;
import core.User;

/*
 * 这个类是用来在客户端和服务器之间传输加入房间信息的
 * status: 0->加入成功, 1-> 用户已经在某个房间了
 */
public class JoinRoomNet extends IDNet{

	public JoinRoomNet(User u,Room r)
	{
		super(8);
		this.user = u;
		this.room = r;
	}
	
	public User getUser()
	{
		return user;
	}

	public Room getRoom()
	{
		return room;
	}
	
	
	private User user;
	private Room room;
}
