/*
ID: lazydom1
LANG: JAVA
TASK: RightPanel.java
Created on: 2012-2-19-下午1:08:37
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.ChatNet;

import core.Room;
import core.User;

/**
 * @author will
 * 这个是房间界面右侧类，包含聊天框和聊天信息发送框
 */
public class RightJPanel extends JPanel{

	public RightJPanel(User user , Room room)
	{
		super();

		this.setLayout(new BorderLayout());
		addPanel();
		
		this.user = user;
		this.room = room;
	}

	private void addPanel()
	{
		downPanel = new JPanel();
		downPanel.setLayout(new BorderLayout());

		textArea = new JTextArea(40, 19);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		downPanel.add(scrollPane, BorderLayout.CENTER);

		textField = new JTextField();
		textField.addKeyListener(new EnterKeyListener());

		ImageIcon sendImg = new ImageIcon("res"+File.separator+"send.png");
		sendButton = new JButton(sendImg);
		sendButton.addActionListener( new EnterKeyAction());




		ImageIcon faceImg = new ImageIcon("res"+File.separator+"face.png");
		faceButton = new JButton(faceImg);

		sendButton.setPreferredSize(new Dimension(20, 20));
		faceButton.setPreferredSize(new Dimension(20, 20));

		JPanel funcPanel = new JPanel();
		funcPanel.add(sendButton);
		//funcPanel.add(faceButton);

		JPanel panelIndown = new JPanel();
		panelIndown.setLayout(new BorderLayout());
		panelIndown.add(textField);
		panelIndown.add(funcPanel, BorderLayout.EAST);

		downPanel.add(panelIndown, BorderLayout.SOUTH);

		this.add(downPanel, BorderLayout.SOUTH);

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
	

	

	//var
	private JPanel downPanel;
	public JTextArea textArea;
	private JTextField textField;
	private JButton sendButton, faceButton;
	private Socket s;
	private User user;
	private Room room;
	
	/*
	 * 内部enter键监听类
	 */
	class EnterKeyAction extends AbstractAction
	{

		public void actionPerformed(ActionEvent event)
		{
			if(textField.getText().equals("")) return;
			
			ChatNet chatNet = new ChatNet(user,room,textField.getText());
			try {
				
				connect();
				
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
				
				out.writeObject(chatNet);
				out.flush();
				
				
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			textField.setText("");
		}
	}
	/*
	 * 内部enter键盘键监听类
	 */
	class EnterKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent event)
		{
			if(event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(textField.getText().equals("")) return;
				
				ChatNet chatNet = new ChatNet(user,room,textField.getText());
				try {
					
					connect();
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(chatNet);
					out.flush();
					
					
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally
				{
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				textField.setText("");
			}
		}
	}


}