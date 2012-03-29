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

/*
 * 这个类是房间类
 * status：0->请求获取房间列表，1->回复房间列表
 */
public class RoomListNet extends IDNet{

	public RoomListNet()
	{
		super(5);
				
	}
	
	private Vector<Room> roomList;
	
}
