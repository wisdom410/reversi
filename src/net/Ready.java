package net;

import core.Room;
import core.User;

/*
 * 这个类是由客户端向服务器发送点击了准备按钮的命令的
 */
public class Ready extends IDNet{

	
	public Ready(Room r,User u)
	{
		super(11);
		
		this.r = r;
		this.u = u;
	}
	
	
	
	
	public Room getR() {
		return r;
	}
	public void setR(Room r) {
		this.r = r;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}




	private Room r;
	private User u;
}
