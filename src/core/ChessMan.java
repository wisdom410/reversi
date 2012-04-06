/*
ID: lazydom1
LANG: JAVA
TASK: ChessMan.java
Created on: 2012-2-20-下午5:30:09
Author: lazydomino@163.com(pisces)
*/

package core;

import java.io.Serializable;

/*
 * 描述棋子的类，因为棋盘是 8*8 的，所以为了方便直接用 i，j坐标，坐标都是从[1,1]开始[8,8]结束。
 */
public class ChessMan implements Cloneable,Serializable
{	
	public ChessMan(int x,int y, boolean black)
	{
		this.x = x;
		this.y = y;
		this.black = black;
	}

	public boolean isBlack()
	{
		return black;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public void setColor()
	{
		this.black = !this.black;
	}
	public ChessMan clone() throws CloneNotSupportedException
	{
		ChessMan cloned = (ChessMan) super.clone();
		cloned.black = (boolean) black;
		cloned.x = (int) x;
		cloned.y = (int) y;
		return cloned;
	}
	//var
	private int x, y;
	private boolean black;//记录棋子颜色
}