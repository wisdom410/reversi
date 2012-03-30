/*
ID: lazydom1
LANG: JAVA
TASK: login.java
Created on: 2012-3-27-下午12:42:42
Author: lazydomino[AT]163.com(pisces)
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import core.User;

import net.LoginNet;

public class Login extends JFrame{

	public Login() throws IOException
	{
		super();
		this.setTitle("登录网络版黑白棋");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res"+File.separator+"ico.png");
		this.setIconImage(ico.getImage());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(300, 200));
		this.setMaximumSize(new Dimension(300, 200));
		this.setResizable(false);
		add();
		
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
	
	/*
	 * 登录成功，现实下一窗口（大厅，房间）
	 */
	private void login()
	{
		dispose();
		new Hall(user);
		//showDialog("OK");
	}
	
	/*
	 * 添加登录框（用户名、密码）等；
	 */
	private void add()
	{
		panel = new JPanel();
		panel.setLayout(null);
		
		final JTextField usernameField = new JTextField();
		final JPasswordField passwdField = new JPasswordField();
		
		JLabel usernameLabel = new JLabel("用户名:  ");
		JLabel userpasswdLabel = new JLabel("密码:     ");
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(usernameLabel,BorderLayout.WEST);
		userPanel.add(usernameField);
		
		JPanel passwdPanel = new JPanel();
		passwdPanel.setLayout(new BorderLayout());
		passwdPanel.add(userpasswdLabel,BorderLayout.WEST);
		passwdPanel.add(passwdField);
		
		userPanel.setBounds(40, 40, 200, 20);
		passwdPanel.setBounds(40, 70, 200, 20);
		
		panel.add(userPanel);
		panel.add(passwdPanel);
		
		JButton login = new JButton("登录");
		JButton reg = new JButton("注册");
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				
				String username = usernameField.getText().trim();
				char[] pass = passwdField.getPassword();
				
				if(username.length()==0||pass.length==0)
				{
					showDialog("用户名或密码不能为空!");
					return;
				}
				
				user = new User(username,pass);
				
				LoginNet login = new LoginNet(user);
			
				
				//in= new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
				//out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
				
				try {
					connect();
					
					out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(login);
					out.flush();
					
					in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					LoginNet back = (LoginNet) in.readObject();
					out.close();
					in.close();
					
					if(back.getStatus()==0)
					{
						login();
					}
					if(back.getStatus()==1)
					{
						showDialog("用户名或密码错误");
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					showDialog("服务器连接失败");
					//e1.printStackTrace();
				}catch (Exception e2)
				{
					showDialog("出现未知错误！");
				}finally
				{
					try {
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				
			}

		});
		
		reg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			new Register();
			//setHide();
			
			
				
				
			}
		});
		
		login.setBounds(210, 110, 60, 40);
		reg.setBounds(30, 110, 60, 40);
		
		panel.add(login);
		panel.add(reg);
		this.add(panel);
		
	}
	
	
	private static JPanel panel;
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static Socket s;
	private static JOptionPane optionPanel;
	private User user;
	
}
