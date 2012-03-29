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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import net.RoomListNet;

import core.User;
import core.Room;

/*
 * 这个类 是大厅类，
 * 包含：
 * 房间列表等。
 * 功能：
 * 加入房间
 * 退出等。
 */
public class Hall extends JFrame{
		
	public Hall(String username)
	{
		super();
		
		this.username = username;
		
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
		
		table = new JTable(cells, columnName);
		table.setEnabled(false);
		
		
		cenPanel.add(new JScrollPane(table));
		
		this.add(cenPanel);
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
	
	
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static Socket s;
	private static JOptionPane optionPanel;
	private Vector<Room> roomList;
	private String username;
	private JTable table;
	private String[] columnName ={"房间名称","玩家1积分","玩家2积分","能否观战","人数","状态"};
	private Object[][] cells = {
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""},
			{"","","","","",""}
			
	};
	
	class IO implements Runnable
	{
		
		public IO (Socket i)
		{
			incoming = i;
			
			roomList = new RoomListNet();
			
			roomList.setStatus(0);
			
			try {
				out.writeObject(roomList);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				showDialog("网络连接错误！");
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				
				roomList = (RoomListNet) in.readObject();
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				showDialog("网络连接错误！");
				System.exit(0);
			}catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
	
		
		private Socket incoming;
		private RoomListNet roomList;
	}
	
}
