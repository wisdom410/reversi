/*
ID: lazydom1
LANG: JAVA
TASK: Register.java
Created on: 2012-3-27-下午12:43:22
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import core.User;

import net.LoginNet;
import net.RegNet;

/*
 * 这个是注册用户的类
 */
public class Register extends JFrame{

	public Register()
	{
		super();
		this.setTitle("注册用户");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon ico = new ImageIcon("res"+File.separator+"ico.png");
		this.setIconImage(ico.getImage());
		this.setVisible(true);
		this.setMinimumSize(new Dimension(320, 400));
		this.setMaximumSize(new Dimension(320, 400));
		this.setResizable(false);
		
		
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		addinfo();
		addbutton();
		
	}
	
	private void addbutton()
	{
		JButton yesButton = new JButton("确定");
		JButton clearButton = new JButton("清空");
		JButton cancelButton = new JButton("取消");
		
		yesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					
					connect();
					
					String username = usernameField.getText().trim();
					char[] passwd = passwdField.getPassword();
					String nickname = nicknameField.getText().trim();
					String email = emailField.getText().trim();
					
					if(username.length()>20||nickname.length()>20)
					{
						showDialog("用户名或昵称太长！");
						return;
					}
					
					if(username.length()==0||passwd.length==0||nickname.length()==0||email.length()==0)
					{
						showDialog("注册信息不合法！");
						return;
					}
					
					
					RegNet reg = new RegNet(username,passwd,sex,nickname,email,0,sex);
					
					out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(reg);
					out.flush();
					
					in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
					
					RegNet back = (RegNet) in.readObject();
					out.close();
					in.close();
					
					if(back.getStatus()==0)
					{
						showDialog("注册成功！");
						dispose();
					}
					if(back.getStatus()==1)
					{
						showDialog("该用户已经存在！");
					}
					
					
				}catch (IOException e2)
				{
				showDialog("服务器连接错误！");
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					showDialog("出现未知错误");
				}
				
				
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				usernameField.setText("");
				passwdField.setText("");
				sexBox.setSelectedIndex(0);
				nicknameField.setText("");
				emailField.setText("");
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				dispose();
			}
		});
		
		
		
		yesButton.setBounds(50, 300, 60, 40);
		clearButton.setBounds(130, 300, 60, 40);
		cancelButton.setBounds(210, 300, 60, 40);
		
		mainPanel.add(yesButton);
		mainPanel.add(clearButton);
		mainPanel.add(cancelButton);
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
	
	private void addinfo()
	{
		
		
		JLabel usernameLabel = new JLabel("用户名: ");
		JLabel passwdLabel = new   JLabel("密码:    ");
		JLabel sexLabel = new      JLabel("性别:    ");
		JLabel nicknameLabel = new JLabel("昵称:    ");
		JLabel emailLabel = new    JLabel("邮箱:    ");
		
		usernameField = new JTextField();
		passwdField = new JPasswordField();
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new BorderLayout());
		usernamePanel.add(usernameLabel,BorderLayout.WEST);
		usernamePanel.add(usernameField);
		usernamePanel.setBounds(50, 50, 200, 25);
		
		JPanel passwdPanel = new JPanel();
		passwdPanel.setLayout(new BorderLayout());
		passwdPanel.add(passwdLabel,BorderLayout.WEST);
		passwdPanel.add(passwdField);
		passwdPanel.setBounds(50, 90, 200, 25);
		
		
		JPanel sexPanel = new JPanel();
		sexPanel.setLayout(new BorderLayout());
		sexPanel.add(sexLabel,BorderLayout.WEST);
		
		sexBox = new JComboBox<String>();
		sexBox.addItem("男");
		sexBox.addItem("女");
		sexBox.addItem("保密");
		sexBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String tmp = (String) sexBox.getSelectedItem();
				sex = 2;
				
				if(tmp.equals("男"))
				{
					sex = 0;
				}
				if(tmp.equals("女"))
				{
					sex = 1;
				}
			}
		});
		
		sexPanel.add(sexBox);
		sexPanel.setBounds(50, 170, 200 ,25);
		
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.setLayout(new BorderLayout());
		nicknamePanel.add(nicknameLabel,BorderLayout.WEST);
		nicknameField = new JTextField();
		nicknamePanel.add(nicknameField);
		nicknamePanel.setBounds(50, 130, 200, 25);
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new BorderLayout());
		emailPanel.add(emailLabel,BorderLayout.WEST);
		emailField = new JTextField();
		emailPanel.add(emailField);
		emailPanel.setBounds(50, 210, 200, 25);
		
		mainPanel.add(usernamePanel);
		mainPanel.add(passwdPanel);
		mainPanel.add(nicknamePanel);
		mainPanel.add(sexPanel);
		mainPanel.add(emailPanel);
		
		this.add(mainPanel);
	}
	
	
	
	private JPanel mainPanel;	
	private String username = "";
	private String passwd = "";	
	private int sex = 2;//0->男,1->女,2->保密;
	private String nickname = "";
	private String email = "";
	private JTextField usernameField;
	private JPasswordField passwdField;
	private JComboBox<String> sexBox;
	private JTextField nicknameField;
	private JTextField emailField ;
	private static Socket s;
	private static JOptionPane optionPanel;
	private static ObjectInputStream in;
	private static ObjectOutputStream out;

}
