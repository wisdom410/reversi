/*
ID: lazydom1
LANG: JAVA
TASK: ImageJPanel.java
Created on: 2012-2-19-上午10:17:59
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.ChessMan;
import core.ChessManList;

/*
 * 重载了JPanel 的 paint 方法，把背景图片画上。
 * 绘制黑色分割线棋盘。
 * @see javax.swing.JComponent#paint(java.awt.Graphics)
 */
public class ImageChessBoard extends JComponent{
	
	public ImageChessBoard()
	{
		super();
		this.setLayout(null);	
	}
	
	
	public void update(ChessManList list, ChessManList canPlace)
	{
		this.list = list;
		this.canPlace = canPlace;
		//System.out.println("listsize="+list.getSize());
	}
	
	
	public void paintComponent(Graphics g)
	{
		
		
		//System.out.println("paintComponent");
		super.paintComponent(g);
		ImageIcon img = new ImageIcon("res\\chess_background.png"); 
		g.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), this);
		
		ImageIcon chessBoard = new ImageIcon("res\\chess_board.png");
		g.drawImage(chessBoard.getImage(), 160, 120, chessBoard.getIconWidth(), chessBoard.getIconHeight(), this);
		
		for(int i = 0; i<8;i++)
			for(int j = 0; j<8;j++)
			{
				g.drawRect(startx + i * 56, starty + j * 56, 56, 56);
			}
		
		ImageIcon blackimg = new ImageIcon("res\\black_ball.png");
		ImageIcon whiteimg = new ImageIcon("res\\white_ball.png");
		
		int count = 0;
		for(int i = 0; i< list.getSize();i++)
		{
			
			
			ChessMan chessMan = list.getChessMan(i);
			if(chessMan.isBlack())
			{count ++;
				g.drawImage(blackimg.getImage(), startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1)*56+5, 46, 46, this);
			}
			else
				g.drawImage(whiteimg.getImage(), startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1)*56+5, 46, 46, this);
		}
		
		for(int i = 0; i< canPlace.getSize();i++)
		{
			
			ChessMan chessMan = canPlace.getChessMan(i);
			g.drawRect(startx + (chessMan.getX()-1)*56 + 5, starty + (chessMan.getY()-1) *56 + 5, 46, 46);
		}
		//System.out.println("paint:"+count);
	}
	
	//var
	int startx = 178, starty = 140;
	private ChessManList list, canPlace;
}

