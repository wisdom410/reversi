/*
ID: lazydom1
LANG: JAVA
TASK: ChessFrame.java
Created on: 2012-2-18-下午7:57:13
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessFrame extends JFrame{

	public ChessFrame()
	{
		super();
		
		this.setTitle("黑白棋");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res\\ico.png");
		this.setIconImage(ico.getImage());
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1024, 730));
		this.setMaximumSize(new Dimension(1024, 730));
		this.setResizable(false);
		
		addPanel();
		
	}
	/*
	 * add the mainPanel and the rightPanel include the chatPanel and the players' information panel.
	 */
	private void addPanel()
	{
		

		chessManList = new ChessManList();
		chessManList.add(4, 4, false);
		chessManList.add(4, 5, true);
		chessManList.add(5, 4, true);
		chessManList.add(5, 5, false);
		
		canPlace = new ChessManList();
		canPlace.add(3, 4, true);
		canPlace.add(4, 3, true);
		canPlace.add(6, 5, true);
		canPlace.add(5, 6, true);
		
		
		mainPanel = new ImageChessBoard(chessManList, canPlace);
		
		undoButton = new JButton("悔棋");
		loseButton = new JButton("认输");
		saveButton = new JButton("保存");
		loadButton = new JButton("加载");
		
		JPanel downPanel = new JPanel();
		
		downPanel.add(undoButton);
		downPanel.add(loseButton);
		downPanel.add(saveButton);
		downPanel.add(loadButton);
		downPanel.setBounds(260, 630,270, 36);
		mainPanel.add(downPanel);
		this.addMouseListener(new MouseAction());
		this.add(mainPanel);
		
		
		rightPanel = new RightJPanel();
		this.add(rightPanel, BorderLayout.EAST);
		
	}
	
	/*
	 * 计算可以放置棋子的地方。
	 */
	private void findCanPlace(ChessMan chessMan)
	{
		final int[] dx = {0,-1,-1,-1,0,1,1,1};
		final int[] dy = {-1,-1,0,1,1,1,0,-1};
		boolean can = false;
		int x = chessMan.getX();
		int y = chessMan.getY();
		
		for(int i = 0; i<8;i++)
		{
			can = false;
			x = chessMan.getX();
			y = chessMan.getY();
			//System.out.println("start= "+x+" "+y);
			while(x +dx[i] >= 1 && y +dy[i] >= 1 && x +dx[i]<=8 && y+dy[i]<=8)
			{
				if(chessManList.havaChessman(x + dx[i], y + dy[i]) && chessManList.getColor(x + dx[i], y + dy[i]) != chessMan.isBlack())
				{
					x = x + dx[i];
					y = y + dy[i];
					can = true;
				//	System.out.println(x+" "+y);
				}else
				{
					break;
				}
			}
			if(x + dx[i] >= 1 && y + dy[i] >= 1 && x + dx[i] <=8 && y + dy[i]<=8 && !chessManList.havaChessman(x + dx[i], y + dy[i]) && can)
			{
			//	System.out.println("find= "+(x + dx[i])+" "+(y + dy[i]));
				canPlace.add(x + dx[i], y + dy[i], chessMan.isBlack());
			}
		}	
	}
	
	/*
	 * 计算翻转后的棋盘
	 */
	private void calcChessManList(int pos_x, int pos_y)
	{
		final int[] dx = {0,-1,-1,-1,0,1,1,1};
		final int[] dy = {-1,-1,0,1,1,1,0,-1};
		
		ChessManList chess = new ChessManList();
		
		for(int i = 1; i<=8;i++)
			for(int j = 1 ;j<=8;j++)
			{
				if(chessManList.getColor(i , j) == chessManList.getColor(pos_x, pos_y) && !(i == pos_x && j == pos_y))
				{
					for(int m = 0; m <8;m++)
					{
						
						int x = i;
						int y = j;
						chess.clear();
						
						while(x + dx[m] >= 1 && y + dy[m] >= 1 && x + dx[m] <=8 && y +dy[m]<=8)
						{
							if(chessManList.havaChessman(x + dx[m], y + dy[m]) && (chessManList.getColor(x + dx[m], y + dy[m]) != chessManList.getColor(pos_x, pos_y)))
							{
								x = x + dx[m];
								y = y + dy[m];
								chess.add(x, y, !chessManList.getColor(pos_x, pos_y));
								
							}else
							{
								break;
							}
						}
						
						if(x + dx[m] == pos_x && y + dy[m] == pos_y)
						{
							for(int k = 0; k<chess.getSize();k++)
							{
								//System.out.println((chess.getChessMan(k).getX())+" "+(chess.getChessMan(k).getY()));
								chessManList.turn(chess.getChessMan(k).getX() , chess.getChessMan(k).getY());
							}
						}
					}
				}
			}
		
	}
	
	//var
	private JPanel mainPanel, rightPanel;
	private ChessManList chessManList, canPlace;
	private JButton loseButton, undoButton, saveButton, loadButton, pointButton;
	private int startx = 181, starty = 165;
	private boolean black = true;
	private int count = 0;
	
	
	class MouseAction extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			
			
			
			
			int x = event.getX();
			int y = event.getY();
			if(x <= startx || y <= starty || x >= 628 || y >=	612) return ;
			
			
			x -= startx;
			y -= starty;
			
			int pos_x = 1;
			int pos_y = 1;
			
			pos_x += x / 56;
			pos_y += y / 56;
			
			
			
			if(chessManList.havaChessman(pos_x, pos_y) || !canPlace.havaChessman(pos_x, pos_y)) return ;
			
			chessManList.add(pos_x, pos_y, black);
			
			calcChessManList(pos_x, pos_y);
			
			canPlace.clear();
			for(int i = 0; i< chessManList.getSize();i++)
			{
				if(chessManList.getChessMan(i).isBlack() != black)
				{
					findCanPlace(chessManList.getChessMan(i));
				}
			}
		
			if(canPlace.getSize() != 0)
			{
				black = !black;
				count = 0;
			}else
			{
				count ++;
				if(count <= 2)
					System.out.println(black+"  is skip!");
				else
					System.out.println(chessManList.isBlackWin()+"");
				
			}
			
			System.out.println(black);
			
			repaint();
			
		}
	}
}

