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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.LoginNetClass;

public class Login extends JFrame{

	public Login()
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
		this.add(panel);
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
		
		JLabel username = new JLabel("用户名:  ");
		JLabel userpasswd = new JLabel("密码:     ");
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanel.add(username,BorderLayout.WEST);
		userPanel.add(usernameField);
		
		JPanel passwdPanel = new JPanel();
		passwdPanel.setLayout(new BorderLayout());
		passwdPanel.add(userpasswd,BorderLayout.WEST);
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
			
				String user = usernameField.getText().trim();
				char[] pass = passwdField.getPassword();
				
				if(user.equals("-1"))
					user = "-1";
				
				LoginNetClass login = new LoginNetClass(user, pass);
				
				Socket s = new Socket();
				try {
					
					s.connect(new InetSocketAddress("localhost",8090), 500);
					
					ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
					
					out.writeObject(login);
					out.flush();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		login.setBounds(210, 110, 60, 40);
		reg.setBounds(30, 110, 60, 40);
		
		panel.add(login);
		panel.add(reg);
		
		
	}
	
	
	JPanel panel;
	
}
