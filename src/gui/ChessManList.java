/*
ID: lazydom1
LANG: JAVA
TASK: ChessMan.java
Created on: 2012-2-19-上午11:34:11
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.util.ArrayList;

/*
 * 描述整个棋盘的类。每个棋子都存在一个ArrayList里面，遍历ArrayList就可以找到各个棋子位置。
 */
public class ChessManList {
	
	public ChessManList()
	{
		
	}
	//var
	
	private ArrayList<ChessMan> list = new ArrayList<ChessMan>();
	private boolean[][] black_white = new boolean[8][8];
	private boolean[][] have_ChessMan = new boolean[8][8];
	
	public void add(int x,int y, boolean black)
	{
		x -= 1;
		y -= 1;
		list.add(new ChessMan(x, y, black));
		black_white[x][y] = black;
		have_ChessMan[x][y] = true; 
	}
	
	public int getNum()
	{
		return list.size();
	}
	
	public ChessMan getChessMan(int index)
	{
		return list.get(index);
	}
	
	public boolean havaChessman(int x, int y)
	{
		return have_ChessMan[x - 1][y -1];
	}
	

}

/*
 * 描述棋子的类，因为棋盘是 8*8 的，所以为了方便直接用 i，j坐标，例如 [1,2]代表C2上的棋子。 
 */
class ChessMan
{	
	public ChessMan(int x,int y, boolean black)
	{
		this.x = x ;
		this.y = y ;
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
	
	//var
	private int x, y;
	private boolean black;//记录棋子颜色
}
