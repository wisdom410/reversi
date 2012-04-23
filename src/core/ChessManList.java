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

/*
 * 描述整个棋盘的类。每个棋子都存在一个ArrayList里面，遍历ArrayList就可以找到各个棋子位置。
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


	public void add(int x,int y, boolean black)//the x and y must be start from 1,1
	{
		if(chessMan[x][y] != 0) return ;
		list.add(new ChessMan(x, y, black));
		if(black) chessMan[x][y] = 1;else chessMan[x][y] = 2;
		if(black) numBlack++;else numWhite++;
	}

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

	public int getSize()
	{
		return list.size();
	}

	public ChessMan getChessMan(int index)
	{
		return list.get(index);
	}

	public boolean isBlack(int x,int y)//
	{
		if(chessMan[x][y] == 1) return true;
		return false;
	}

	public ArrayList<ChessMan> getList()
	{
		return list;
	}

	public int[][] getChessManArray()
	{
		return chessMan;
	}

	public boolean havaChessman(int x, int y)//the x and y must be start from 1,1
	{
		if(chessMan[x][y] != 0) return true;
		return false;
	}

	public int getBlackNum()
	{
		return numBlack;
	}

	public int getwhiteNum()
	{
		return numWhite;
	}

	public boolean isBlackWin()
	{
		return numBlack > numWhite ? true:false;
	}

	public void clear()
	{
		list.clear();
		for(int i = 0; i<= 8;i++)
			for(int j = 0; j<=8;j++)
			{
				chessMan[i][j] = 0;
			}
	}

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
	
	public boolean equals(ChessManList c)
	{
		for(int i = 0;i<9;i++)
			for(int j = 0;j<9;j++)
				if(chessMan[i][j]!=c.getChessManArray()[i][j])
					return false;
		return true;
	}

}
