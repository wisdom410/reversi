/*
ID: lazydom1
LANG: JAVA
TASK: UndoChessClass.java
Created on: 2012-3-28-上午10:56:22
Author: lazydomino[AT]163.com(pisces)
*/

package net;

public class UndoChessClass extends IDNet{

	public UndoChessClass(boolean can)
	{
		super(3);
		this.can = can;
	}
	
	
	
	public void upCan(boolean can)
	{
		this.can = can;
	}
	public boolean getCan()
	{
		return this.can;
	}
	
	private boolean can;
}
