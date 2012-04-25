/*
ID: lazydom1
LANG: JAVA
TASK: LoginNetClass.java
Created on: 2012-3-28-上午10:44:31
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import core.User;

/**
 * @author will
 * 这个类是用来封装登陆类的，用于客户登录端和服务器传输登录信息
 * status：0->验证成功，1->没有此用户，2->密码不正确,3->此用户已经登录.
 */
public class LoginNet extends IDNet{

	public LoginNet(User user)
	{
		super(0);
		
		this.user = user;
	}
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}


	private User user;
}
