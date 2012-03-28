/*
ID: lazydom1
LANG: JAVA
TASK: LoginNetClass.java
Created on: 2012-3-28-上午10:44:31
Author: lazydomino[AT]163.com(pisces)
*/

package net;

/*
 * 这个类是用来封装登陆类的，用于客户登录端和服务器传输登录信息
 * status：0->验证成功，1->没有此用户，2->密码不正确。
 */
public class LoginNet extends IDNet{

	public LoginNet(String username, char[] passwd)
	{
		super(0);
		
		this.username = username;
		if(username.equals("-1"))
			this.ID = -1;
		this.passwd = passwd;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public char[] getPasswd() {
		return passwd;
	}



	private String username;
	private char[] passwd;
}
