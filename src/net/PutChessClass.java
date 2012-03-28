/*
ID: lazydom1
LANG: JAVA
TASK: PutChessClass.java
Created on: 2012-3-28-上午10:54:20
Author: lazydomino[AT]163.com(pisces)
*/

package net;

public class PutChessClass extends IDNetClass{

	
	public PutChessClass(int x,int y)
	{
		super(2);
		
		this.x = x;
		this.y = y;
	}
	
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	private int x,y;
}
