/*
ID: lazydom1
LANG: JAVA
TASK: RegNetClass.java
Created on: 2012-3-28-上午10:43:08
Author: lazydomino[AT]163.com(pisces)
*/

package net;

/**
 * @author will
 *
 * 这个类是用来封装注册信息的类，用于注册客户端和服务器之间传输注册信息
 * status：0->注册成功，1->存在此用户。
 */
public class RegNet extends IDNet{

	public RegNet(String username, char[] passwd,int sex, String nickname, String email,int score, int image)
	{
		
		super(1);
		this.username = username;
		this.passwd = passwd;
		this.sex = sex;
		this.nickname = nickname;
		this.email = email;
		this.score = score;
		this.image = image;
	}
	
	
	
	
	
	public String getUsername() {
		return username;
	}
	public char[] getPasswd() {
		return passwd;
	}
	public String getNickname() {
		return nickname;
	}
	public String getEmail() {
		return email;
	}
	public int getScore() {
		return score;
	}
	public int getImage() {
		return image;
	}
	public int getSex() {
		return sex;
	}





	private String username,nickname,email;
	private char[] passwd;
	private int score,image,sex;
}
