package net;

import core.User;

/*
 *   
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
