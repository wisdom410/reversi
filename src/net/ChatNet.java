/*
ID: lazydom1
LANG: JAVA
TASK: ChatNet.java
Created on: 2012-3-29-下午6:45:11
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import core.User;

/*
 * ID = 4
 * 这个类是用来封装聊天信息的
 * status：
 */
public class ChatNet extends IDNet{

	public ChatNet(User user, String msg)
	{
		super(4);
	//	this.user = user;
		
		this.msg =msg;
		
	}
	
	
	private String msg;
}
