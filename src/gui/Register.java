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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.LoginNet;

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
				
				connect();
				
				
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
	 * 连接服务器
	 */
	private void connect() 
	{
		
		s = new Socket();
		try {
			s.connect(new InetSocketAddress("localhost", 8090),3000);
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
}
