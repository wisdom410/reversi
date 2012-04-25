package net;

import core.User;

/**
 * @author will
 *   这个类是用来传输用户退出房间信息的类
 */
public class ExitRoomNet extends IDNet{

	public ExitRoomNet(User user)
	{
		super(7);
		this.user = user;
	}
	
	public User getUser()
	{
		return user;
	}
	
	
	
	private User user;
}
