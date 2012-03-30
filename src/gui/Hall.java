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
import javax.swing.table.AbstractTableModel;

import net.CreateRoomNet;
import net.IDNet;
import net.RoomListNet;

import core.User;
import core.Room;
import core.HallTableModel;
/*
 * 这个类 是大厅类，
 * 包含：
 * 房间列表等。
 * 功能：
 * 加入房间
 * 退出等。
 */
public class Hall extends JFrame{
		
	public Hall(User user) 
	{
		super();
		
		this.user = user;
		
		this.setTitle("网络版黑白棋大厅");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res"+File.separator+"ico.png");
		this.setIconImage(ico.getImage());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(800, 600));
		this.setMaximumSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		addelement();

		
	}
	
	
	private void addelement()
	{
		JPanel cenPanel = new JPanel();
		cenPanel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("<html><font size=10 color =blue>房间列表</font></html>",SwingConstants.CENTER);
		cenPanel.add(title,BorderLayout.NORTH);
		
		columnName = new Vector<String>();
		
		model = new HallTableModel();
		
		table = new JTable(model);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addData("test1",0,0,true,1,"等待玩家");
		addData("test2",9,10,true,2,"游戏中");
		cenPanel.add(new JScrollPane(table));
		
		this.add(cenPanel);
		
		
		JPanel southPanel = new JPanel();
		
		joinRoom = new JButton("<html><font size=6 color =black>加入游戏</font></html>");
		viewRoom = new JButton("<html><font size=6 color =black>观看游戏</font></html>");
		exit = new JButton("<html><font size=6 color =black>退出游戏</font></html>");
		createRoom = new JButton("<html><font size=6 color =black>创建房间</font></html>");
		refresh = new JButton("<html><font size=6 color =black>刷新列表</font></html>");
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				refreshFunc();
				
			}
		});
		
		createRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					String roomName = javax.swing.JOptionPane.showInputDialog("请输入房间名:");
					
					if(roomName.length() == 0)
					{
						showDialog("房间名不合法！");
						return ;
					}
					
					connect();
					
					CreateRoomNet requireCreateRoom = new CreateRoomNet(roomName, user);
					
					out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					out.writeObject(requireCreateRoom);
					out.flush();
					
					in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					IDNet backCmd = (IDNet)in.readObject();
					
					if(backCmd.getID()==6)
					{
						CreateRoomNet cmd = (CreateRoomNet) backCmd;
						
						if(cmd.getStatus() == 1)
						{
							showDialog("此房间已经存在！");
						}
						if(cmd.getStatus() == 0)
						{
							showDialog("创建成功！");
							refreshFunc();
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
				
			}
		});
		
		viewRoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				System.exit(0);
			}
		});
		
		
		southPanel.add(refresh);
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
			
			IDNet requireRoomList = new IDNet(5);
			
			out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
			
			out.writeObject(requireRoomList);
			out.flush();
			
			in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			
			IDNet backCmd = (IDNet)in.readObject();
			
			if(backCmd.getID()==5)
			{
				RoomListNet cmd = (RoomListNet) backCmd;
				
				model = new HallTableModel();
				model.addRoom(cmd.getRoomList());
				table.setModel(model);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			showDialog("网络连接错误！");
			//e1.printStackTrace();
			System.exit(0);
		}
				
	}
	/*
	 * 向表中添加数据
	 */
	 private void addData(String roomName,int score1,int score2,boolean canview,int num_pep,String status) {
	        model.addRow(roomName,score1,score2,canview,num_pep,status);
	        table.updateUI();
	    }
	 
	
	/*
	 * 对话框
	 */
	private void showDialog(String str)
	{
		optionPanel = new JOptionPane();
		optionPanel.showMessageDialog(this, str);

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
	
	public boolean getRun()
	{
		return isRun;
	}
	
	public ObjectInputStream getIn()
	{
		return in;
	}
	
	public ObjectOutputStream getOut()
	{
		return out;
	}
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket s;
	private static JOptionPane optionPanel;
	private Vector<Room> roomList;
	private User user;
	private JTable table;
	private Vector<String> columnName;
	private HallTableModel model;
	private JButton joinRoom,viewRoom,exit,createRoom,refresh;
	private boolean isRun = true;
	
	
}
