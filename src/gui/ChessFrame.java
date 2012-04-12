/*
ID: lazydom1
LANG: JAVA
TASK: ChessFrame.java
Created on: 2012-4-1-上午9:22:43
Author: lazydomino[AT]163.com(pisces)
*/

package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.ChessFrameNet;
import net.ExitRoomNet;
import net.Ready;

import core.ChessMan;
import core.ChessManList;
import core.Room;
import core.User;

public class ChessFrame extends JFrame{
	
	public ChessFrame(Room room,User u)
	{
		super();

		this.setTitle("黑白棋--"+room.getRoomName()+"--"+u.getUsername());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res"+File.separator+"ico.png");
		this.setIconImage(ico.getImage());
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1024, 730));
		this.setMaximumSize(new Dimension(1024, 730));
		this.setResizable(false);
		
		this.room = room;
		this.user = u;
				
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosed(WindowEvent e){
				
				user.setPlayer(false);
				ExitRoomNet exit = new ExitRoomNet(user);
				
				try {

					if(t!=null)
						t.stop();
						
					connect();
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(exit);
					out.flush();
					out.close();
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		addPanel();
		
		if(room.getStatus().equals("游戏中...")||!user.isPlayer())
		{
			canPlace.clear();
			
			readyButton.setEnabled(false);
			undoButton.setEnabled(false);
			loseButton.setEnabled(false);
			saveButton.setEnabled(false);
			loadButton.setEnabled(false);

			try {
				
				t = new ListenFromServer(user.isPlayer());
				
				t.start();
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}else
		{
			showDialog("点击准备开始新游戏，点击加载恢复保存棋盘");
		}
		

	}
	
	/*
	 * add the mainPanel and the rightPanel include the chatPanel and the players' information panel.
	 */
	private void addPanel()
	{


		chessManList = room.getChessManList();
		
		if(room.getBlack().equals(user.getUsername()))
		{
			black = true;
		}else
		{
			black = false;
		}

		canPlace = new ChessManList();
		


		mainPanel = new ImageChessBoard();
		mainPanel.update(chessManList, canPlace);
		
		readyButton = new JButton("准备");
		readyButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				undoButton.setEnabled(true);
				loseButton.setEnabled(true);
				saveButton.setEnabled(true);
				loadButton.setEnabled(false);
				
				if(user.getUsername().equals(room.getPlayer1()))
				{
					room.setPlayer1Ready(true);
				}else
				{
					room.setPlayer2Ready(true);
				}
				readyButton.setEnabled(false);
				
				try {
					
					Ready ready = new Ready(room,user);
					
					connect();
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(ready);
					out.flush();
					
					out.close();
					s.close();
					
					t = new ListenFromServer(user.isPlayer());
					t.start();
					
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
			
			
		});
		
		
		
		undoButton = new JButton("悔棋");
		undoButton.setEnabled(false);
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			

			}
		});
		loseButton = new JButton("认输");
		loseButton.setEnabled(false);
		saveButton = new JButton("保存");
		saveButton.setEnabled(false);
		loadButton = new JButton("加载");

		JPanel downPanel = new JPanel();

		downPanel.add(readyButton);
		downPanel.add(undoButton);
		downPanel.add(loseButton);
		downPanel.add(saveButton);
		downPanel.add(loadButton);
		downPanel.setBounds(260, 630,330, 36);
		mainPanel.add(downPanel);
		this.addMouseListener(new MouseAction());
		this.add(mainPanel);


		rightPanel = new RightJPanel(user,room);
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
				if(chessManList.havaChessman(x + dx[i], y + dy[i]) && chessManList.isBlack(x + dx[i], y + dy[i]) != chessMan.isBlack())
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
	 * 计算放置棋子后应该反转的棋子。
	 */
	private ChessManList turnChessMan(ChessMan chessMan)
	{
		final int[] dx = {0,-1,-1,-1,0,1,1,1};
		final int[] dy = {-1,-1,0,1,1,1,0,-1};
		boolean can = false;
		int x = chessMan.getX();
		int y = chessMan.getY();


		ChessManList newList = chessManList;
		ChessManList shouldTurn = new ChessManList();

		for(int l = 1 ;l<=8;l++)
			for(int m = 1; m<=8;m++)
				if(chessManList.havaChessman(l, m) && chessManList.isBlack(l, m) == chessMan.isBlack())

				for(int i = 0; i<8;i++)
				{
					x = l;
					y = m; 
					shouldTurn.clear();
					can = false;

					if(x == chessMan.getX() && y == chessMan.getY()) break;

					while(x +dx[i] >= 1 && y +dy[i] >= 1 && x +dx[i]<=8 && y+dy[i]<=8)
					{
						if(chessManList.havaChessman(x + dx[i], y + dy[i]) && chessManList.isBlack(x + dx[i], y + dy[i]) != chessMan.isBlack())
						{
							x = x + dx[i];
							y = y + dy[i];
							can = true;
							shouldTurn.add(x, y, chessMan.isBlack());
							//System.out.println("shouldTurn:"+x+" "+y);
						}else
						{
							//System.out.println("break:"+x+" "+y);
							break;
						}
					}
					if((x + dx[i])==chessMan.getX() && (y + dy[i]) == chessMan.getY() && can)
					{
						for(int k = 0 ;k < shouldTurn.getSize();k++)
						{
							//System.out.println("turned:"+shouldTurn.getChessMan(k).getX()+" "+shouldTurn.getChessMan(k).getY());
							newList.turn(shouldTurn.getChessMan(k).getX(), shouldTurn.getChessMan(k).getY());
						}
					}

				}

		return newList;



	}



	public void finish()
	{
		if(chessManList.isBlackWin())
		{
			showDialog("黑棋获胜！");
		}else
		{
			showDialog("白棋获胜！");
		}
		return;
	}
	
	
	/*
	 * 连接服务器
	 */
	private void connect() throws Exception 
	{
		
		
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("config"+File.separator+"serverAddress.props");
		props.load(in);
		in.close();
		
		String serveraddr = props.getProperty("Server");
		
		s = new Socket();
		try {
			s.connect(new InetSocketAddress(serveraddr, 8090),3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}


	/*
	 * 对话框
	 */
	private void showDialog(String str)
	{
		JOptionPane.showMessageDialog(this, str);

	}
	
	/*
	 * 刷新房间信息
	 */
	private void refreshRoom()
	{
		
		
		
		try{
			connect();
			
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
		
		
			ChessFrameNet chess = new ChessFrameNet(this.room);
			
			out.writeObject(chess);
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			chess = (ChessFrameNet) in.readObject();
			
			
			this.room = chess.getRoom();
			
			//System.out.println("read chessFrameNet From server OK!");
			chessManList = room.getChessManList();		
			
			
			//System.out.println(room.chat);
			
			System.out.println(room.getBlack());
			
			canPlace.clear();
			
				if((room.getBlack().equals(user.getUsername())))
				{
					canPlace.clear();
					for(int i = 0; i< chessManList.getSize();i++)
					{
						if(chessManList.getChessMan(i).isBlack() != black) 
						{
							findCanPlace(chessManList.getChessMan(i));
						}
					}
				}
			mainPanel.repaint();
			
			rightPanel.textArea.setText(room.chat);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	//var
	private RightJPanel  rightPanel;
	private ImageChessBoard mainPanel;
	private ChessManList chessManList, canPlace;
	private ChessManList[] undoChessManList = new ChessManList[65];
	private JButton loseButton, undoButton, saveButton, loadButton, readyButton;
	private int startx = 181, starty = 165;
	private int mouseX,mouseY;
	private boolean black;
	private int count = 0;
	private int now_step = 0;
	private static Socket s;
	private Room room;
	private User user;
	private Thread t;


	class MouseAction extends MouseAdapter 
	{
		public void mousePressed(MouseEvent event)
		{

			mouseX = event.getX();
			mouseY = event.getY();



			int x = mouseX;
			int y = mouseY;

			//System.out.println(x+" "+y);
			if(x <= startx || y <= starty || x >= 628 || y >=	612) return ;


			x -= startx;
			y -= starty;

			int pos_x = 1;
			int pos_y = 1;

			pos_x += x / 56;
			pos_y += y / 56;



			if(chessManList.havaChessman(pos_x, pos_y) || !canPlace.havaChessman(pos_x, pos_y)) return ;

			chessManList = turnChessMan(new ChessMan(pos_x, pos_y, black));

			chessManList.add(pos_x, pos_y, black);
			mainPanel.update(chessManList, canPlace);
			//System.out.println(pos_x+" "+pos_y);

			
			
			
			


			canPlace.clear();

			for(int i = 0; i< chessManList.getSize();i++)
			{
				if(chessManList.getChessMan(i).isBlack() != black) 
				{
					findCanPlace(chessManList.getChessMan(i));
				}
			}


			
			
			mainPanel.repaint();

			if(chessManList.getSize() == 64)
			{
				finish();
				return;
			}

			if(canPlace.getSize() != 0)
			{
				black = !black;
				count = 0;
			}else
			{
				count ++;
				System.out.println(!black+"  is skip!");
				System.out.println("count="+count);
				for(int i = 0; i< chessManList.getSize();i++)
				{
					if(chessManList.getChessMan(i).isBlack() == black)
					{
						findCanPlace(chessManList.getChessMan(i));
					}
				}
				if(canPlace.getSize() ==0)
					count ++;
				if(count ==2)
				{
					finish();
					return;
				}


			}

			try {
				undoChessManList[++now_step] = new ChessManList();
				for(ChessMan c : chessManList.getList())
				{
					undoChessManList[now_step].add(c.clone());
				}

				if(!black)
					undoChessManList[now_step].black = true;
				else
					undoChessManList[now_step].black = false;
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//System.out.println(black);

			

		}
	}
	
	class ListenFromServer extends Thread
	{
		public ListenFromServer(boolean isPlayer)
		{
			this.isPlayer = isPlayer;
			
		}
		
		public void run()
		{
			

			while(true)
			{
				
				refreshRoom();
				
				try {
					sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		
		
			
		}
		
		private boolean isPlayer;
	}
}


