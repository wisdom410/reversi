package net;

import core.Room;

/*
 * 
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
