/*
ID: lazydom1
LANG: JAVA
TASK: ChessMan.java
Created on: 2012-2-19-上午11:34:11
Author: lazydomino@163.com(pisces)
*/

package core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author will
 *描述整个棋盘的类。每个棋子都存在一个ArrayList里面，遍历ArrayList就可以找到各个棋子位置。
 */
public class ChessManList  implements Cloneable,Serializable{


	public ChessManList()
	{

	}
	//var

	private ArrayList<ChessMan> list = new ArrayList<ChessMan>();
	private int[][] chessMan = new int[9][9];//0 -> 没有棋子.1->黑色。2->白色。
	private int numBlack = 0;
	private int numWhite = 0;
	public boolean black = false;

	/*
	 * 添加棋子
	 */
	public void add(int x,int y, boolean black)//the x and y must be start from 1,1
	{
		if(chessMan[x][y] != 0) return ;
		list.add(new ChessMan(x, y, black));
		if(black) chessMan[x][y] = 1;else chessMan[x][y] = 2;
		if(black) numBlack++;else numWhite++;
	}
	/*
	 *添加棋子 
	 */
	public void add(ChessMan c)//the x and y must be start from 1,1
	{
		int x = c.getX();
		int y = c.getY();
		boolean black = c.isBlack();
		if(chessMan[x][y] != 0) return ;
		list.add(new ChessMan(x, y, black));
		if(black) chessMan[x][y] = 1;else chessMan[x][y] = 2;
		if(black) numBlack++;else numWhite++;
	}

	/*
	 * 翻转棋子
	 */
	public void turn(int x, int y)//the x and y must be start from 1,1
	{
		if(chessMan[x][y] == 1)
		{
			numBlack--;
			numWhite++;
			chessMan[x][y] = 2;
		}else
		{
			numWhite--;
			numBlack++;
			chessMan[x][y] = 1;
		}
		for(int i = 0 ;i <list.size();i++)
			if(list.get(i).getX() == x && list.get(i).getY() == y)
			{
				list.get(i).setColor();
			}
	}
	/*
	 * 获取棋盘中棋子个数
	 */
	public int getSize()
	{
		return list.size();
	}
	/*
	 * 得到某个棋子
	 * 
	 */
	public ChessMan getChessMan(int index)
	{
		return list.get(index);
	}

	/*
	 * 
	 */
	public boolean isBlack(int x,int y)//
	{
		if(chessMan[x][y] == 1) return true;
		return false;
	}

	/*
	 * 获取棋子列表
	 */
	public ArrayList<ChessMan> getList()
	{
		return list;
	}

	/*
	 * 获取棋子列表
	 */
	public int[][] getChessManArray()
	{
		return chessMan;
	}

	/*
	 * 判断当前位置是否有棋子
	 */
	public boolean havaChessman(int x, int y)//the x and y must be start from 1,1
	{
		if(chessMan[x][y] != 0) return true;
		return false;
	}

	/*
	 * 获取黑色棋子的数量
	 */
	public int getBlackNum()
	{
		return numBlack;
	}

	/*
	 * 获取白色棋子的数量
	 */
	public int getwhiteNum()
	{
		return numWhite;
	}

	/*
	 * 判断是否是黑色棋子胜利了，否则是白色棋子胜利了
	 */
	public boolean isBlackWin()
	{
		return numBlack > numWhite ? true:false;
	}

	/*
	 * 清除棋盘
	 */
	public void clear()
	{
		list.clear();
		for(int i = 0; i<= 8;i++)
			for(int j = 0; j<=8;j++)
			{
				chessMan[i][j] = 0;
			}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ChessManList clone() throws CloneNotSupportedException
	{
		ChessManList cloned = (ChessManList) super.clone();
		cloned.chessMan = (int[][]) chessMan.clone();
		cloned.list = new ArrayList<ChessMan>();
		for(ChessMan c : list)
		{
			cloned.list.add(c.clone());
		}
		cloned.numBlack = numBlack;
		cloned.numWhite = numWhite;
		return cloned;
	}
	
	/*
	 * 判断两个棋盘是否相同
	 */
	public boolean equals(ChessManList c)
	{
		for(int i = 0;i<9;i++)
			for(int j = 0;j<9;j++)
				if(this.getChessManArray()[i][j]!=c.getChessManArray()[i][j])
					return false;
		return true;
	}

}
