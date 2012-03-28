/*
ID: lazydom1
LANG: JAVA
TASK: IDNetClass.java
Created on: 2012-3-28-上午11:55:13
Author: lazydomino[AT]163.com(pisces)
*/

package net;

import java.io.Serializable;

public class IDNetClass implements Serializable{

	
	public IDNetClass(int ID)
	{
		this.ID = ID;
	}
	
	
	
	public int getID() {
		return ID;
	}



	protected int ID;
}
