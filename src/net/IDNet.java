/*
ID: lazydom1
LANG: JAVA
TASK: IDNetClass.java
Created on: 2012-3-28-上午11:55:13
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import java.io.Serializable;

/**
 * @author will
 * 该类是所有网络传输 object类 的父类。
 * status 主要用与 服务器返回给客户端 信息的标记。
 */
public class IDNet implements Serializable{

	
	public IDNet(int ID)
	{
		this.ID = ID;
		this.status = -1;
	}
	
	
	
	public int getID() {
		return ID;
	}
	
	public int getStatus()
	{
		return this.status; 
	}
	public void setStatus(int s)
	{
		this.status = s;
	}



	protected int ID;
	protected int status;
}
