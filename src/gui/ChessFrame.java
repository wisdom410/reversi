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
		
		mainPanel = new ImageChessBoard(chessManList);
		
		undoButton = new JButton("悔棋");
		loseButton = new JButton("认输");
		saveButton = new JButton("保存");
		loadButton = new JButton("加载");
		
		JPanel downPanel = new JPanel();
		
		downPanel.add(undoButton, BorderLayout.SOUTH);
		downPanel.add(loseButton, BorderLayout.SOUTH);
		downPanel.add(saveButton, BorderLayout.SOUTH);
		downPanel.add(loadButton, BorderLayout.SOUTH);
		downPanel.setBounds(271, 630,280, 40);
		mainPanel.add(downPanel);
		this.addMouseListener(new MouseAction());
		this.add(mainPanel);
		
		
		rightPanel = new RightJPanel();
		this.add(rightPanel, BorderLayout.EAST);
		
	}
	
	/*
	 * 计算可以放置棋子的地方。
	 */
	
	
	//var
	private JPanel mainPanel, rightPanel;
	private ChessManList chessManList, canPlace;
	private JButton loseButton, undoButton, saveButton, loadButton;
	private int startx = 181, starty = 165;
	boolean flag = true;
	
	
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
			
			
			
			if(chessManList.havaChessman(pos_x, pos_y)) return ;
			
			chessManList.add(pos_x, pos_y, flag);
			
			if(flag)
				flag = false;
			else
				flag = true;
			
			repaint();
			
		}
	}
}

