/*
ID: lazydom1
LANG: JAVA
TASK: hall.java
Created on: 2012-3-29-下午7:35:05
Author: lazydomino[AT]163.com(pisces)
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.CreateRoomNet;
import net.ExitRoomNet;
import net.IDNet;
import net.JoinRoomNet;
import net.RoomListNet;
import net.ViewRoomNet;

import core.User;
import core.Room;
import core.HallTableModel;
/**
 * @author will
 * 这个类 是大厅类，
 * 包含：
 * 房间列表等。
 * 功能：
 * 加入房间
 * 退出等。
 */
public class Hall extends JFrame{
		
	public Hall(User u) 
	{
		super();
		
		this.user = u;
		
		this.setTitle("网络版黑白棋大厅--"+user.getUsername());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res"+File.separator+"ico.png");
		this.setIconImage(ico.getImage());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e){
				
				ExitRoomNet exit = new ExitRoomNet(user);
				
				try {
					connect();
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(exit);
					out.flush();
					
					out.close();
					
					
//					System.out.println("send exit");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		addelement();
		
		Thread t = new refreshTable();
		t.start();

		
	}
	
	
	private void addelement()
	{
		JPanel cenPanel = new JPanel();
		cenPanel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("<html><font size=10 color =blue>房间列表</font></html>",SwingConstants.CENTER);
		cenPanel.add(title,BorderLayout.NORTH);
		
		
		model = new HallTableModel();
		
		table = new JTable(model);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//addData("test1",0,0,true,1,"等待玩家");
		//addData("test2",9,10,true,2,"游戏中");
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(table.getSelectedRow());
				if(table.getSelectedRow()==-1) return;
				Room selectRoom = roomList.get(table.getSelectedRow());
				if(selectRoom.getPlayer1().length()==0||selectRoom.getPlayer2().length()==0)
				{
					joinRoom.setEnabled(true);
					viewRoom.setEnabled(false);
				}else
				{
					joinRoom.setEnabled(false);
					viewRoom.setEnabled(true);
				}
			}
		});
		
		cenPanel.add(new JScrollPane(table));
		
		this.add(cenPanel);
		
		
		JPanel southPanel = new JPanel();
		
		joinRoom = new JButton("<html><font size=6 color =black>加入游戏</font></html>");
		viewRoom = new JButton("<html><font size=6 color =black>观看游戏</font></html>");
		exit = new JButton("<html><font size=6 color =black>退出游戏</font></html>");
		createRoom = new JButton("<html><font size=6 color =black>创建房间</font></html>");
		refresh = new JButton("<html><font size=6 color =black>刷新列表</font></html>");
		
		joinRoom.setEnabled(false);
		viewRoom.setEnabled(false);
	
		//已经抛弃
		//-------------------
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				refreshFunc();
				
			}
		});
		//-------------------
		createRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					table.clearSelection();
					
					
					String roomName = null;
					roomName = javax.swing.JOptionPane.showInputDialog("请输入房间名:");
					
//					System.out.println("roomName="+roomName);
					
					if(roomName==null||roomName.length() == 0)
					{
						showDialog("房间名不合法！");
						return ;
					}
					
					connect();

					user.setPlayer(true);
					CreateRoomNet requireCreateRoom = new CreateRoomNet(roomName, user);
					
					out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					out.writeObject(requireCreateRoom);
					out.flush();
					
					in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					IDNet backCmd = (IDNet)in.readObject();
					
					if(backCmd.getID()==6)
					{
						CreateRoomNet cmd = (CreateRoomNet) backCmd;
						
						if(cmd.getStatus()==2)
						{
							showDialog("您已经在房间中，请退出房间后重试！");
							return;
						}
						if(cmd.getStatus() == 1)
						{
							showDialog("此房间已经存在！");
							return;
						}
						if(cmd.getStatus() == 0)
						{
							//showDialog("创建成功！");
							refreshFunc();
							new ChessFrame(roomList.get(roomList.size()-1),user);
							
						}
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
					showDialog("网络连接错误！");
					System.exit(0);
				}
			}
		});
		

		joinRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selected = table.getSelectedRow();
				Room r = roomList.get(selected);
				user.setPlayer(true);
				JoinRoomNet join = new JoinRoomNet(user,r);
				
				try {
					
					connect();
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(join);
					out.flush();
					
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					join = (JoinRoomNet) in.readObject();
					
					if(join.getStatus()==1)
					{
						showDialog("您已经在房间中，请退出房间后重试！");
						return;
					}
					
					if(join.getStatus()==0)
					{
						//showDialog("加入游戏成功！");
						user.setPlayer(true);
						new ChessFrame(roomList.get(selected),user);
						
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				table.clearSelection();
				
				
			}
		});
		
		viewRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selected = table.getSelectedRow();
				Room r = roomList.get(selected);
				user.setPlayer(false);
				ViewRoomNet view = new ViewRoomNet(user,r);
				
				try {
					connect();
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(view);
					out.flush();
					
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					view = (ViewRoomNet) in.readObject();
					
					if(view.getStatus()==2)
					{
						showDialog("您已经在房间中，请退出房间后重试！");
						return;
					}
					if(view.getStatus()==1)
					{
						showDialog("此房间不允许观看！");
						return;
					}
					if(view.getStatus()==0)
					{
						//showDialog("进入观看成功！");
						new ChessFrame(roomList.get(selected),user);
					}
			
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				table.clearSelection();
				
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ExitRoomNet exit = new ExitRoomNet(user);
				
				try {
					connect();
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(exit);
					out.flush();
					
					out.close();
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				System.exit(0);
			}
		});
		
		
		//southPanel.add(refresh);
		southPanel.add(createRoom);
		southPanel.add(joinRoom);
		southPanel.add(viewRoom);
		southPanel.add(exit);
		
		this.add(southPanel,BorderLayout.SOUTH);
		
	}
	
	/*
	 * 申请刷新列表
	 */
	private void refreshFunc()
	{
		try {
			connect();
			
			RoomListNet requireRoomList = new RoomListNet(user);
			
			out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
			
			out.writeObject(requireRoomList);
			out.flush();
			
			in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			
			IDNet backCmd = (IDNet)in.readObject();
			
			if(backCmd.getID()==5)
			{
				RoomListNet cmd = (RoomListNet) backCmd;
				
				roomList = cmd.getRoomList();
				try
				{
					model.clearRoom();
					//model = new HallTableModel();
					model.addRoom(roomList);
					//table.setModel(model);
					table.updateUI();
				}catch (Exception e)
				{
					
				}
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			showDialog("网络连接错误！");
			e1.printStackTrace();
			System.exit(0);
		}
				
	}
	/*
	 * 向表中添加数据
	 */
	 private void addData(String roomName,String player1,int score1,String player2,int score2,boolean canview,int num_pep,String status) {
	        model.addRow(roomName,player1,score1,player2,score2,canview,num_pep,status);
	        table.updateUI();
	    }
	 
	
	/*
	 * 对话框
	 */
	private void showDialog(String str)
	{
		JOptionPane.showMessageDialog(this, str);

	}
	
	/*
	 * 连接服务器
	 */
	private void connect() throws Exception 
	{
		
		
		Properties props = new Properties();
		FileInputStream config = new FileInputStream("config"+File.separator+"serverAddress.props");
		props.load(config);
		config.close();
		
		String serveraddr = props.getProperty("Server");
		
		s = new Socket();
		s.connect(new InetSocketAddress(serveraddr, 8090),3000);
		
		
			
	}
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket s;
	private Vector<Room> roomList;
	private User user;
	private JTable table;
	private HallTableModel model;
	private JButton joinRoom,viewRoom,exit,createRoom,refresh;
	
	
	class refreshTable extends Thread
	{
		public void run()
		{
			while(true)
			{
				refreshFunc();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
	}
	
}
