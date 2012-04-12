package net;

import core.Room;

/*
 *用于服务器和客户端之间交互房间内部信息
 *status: 0->客户端请求服务器返回房间信息，1->客户端向服务器发送消息 
 */
public class ChessFrameNet extends IDNet{
	
	public ChessFrameNet(Room r)
	{
		super(10);
		this.room = r;
	}
	
	
	
	public Room getRoom() {
		return room;
	}



	public void setRoom(Room room) {
		this.room = room;
	}


	private Room room;

}
