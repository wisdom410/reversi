package net;

import core.Room;
import core.User;

/**
 * @author will
 * 这个类是用来客户端和服务器之间传递观看房间内容的请求/反馈的
 * status:0->加入成功，1->此房间不允许观看,2->用户已经在某房间了
 */
public class ViewRoomNet extends IDNet{
	
	public ViewRoomNet(User u,Room r)
	{
		super(9);
		this.user = u;
		this.room = r;
	}
	
	public User getUser() {
		return user;
	}
	public Room getRoom() {
		return room;
	}
	
	private User user;
	private Room room;
}
