/*
ID: lazydom1
LANG: JAVA
TASK: Main.java
Created on: 2012-2-18-ÏÂÎç7:51:42
Author: lazydomino@163.com(pisces)
*/

package RunGame;


/**
 * @author will
 * 这个是运行客户端的主类
 * 用户名用single，密码为空则进入单机模式
 */
import gui.Login;

import java.awt.EventQueue;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try {
					new Login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}) ;
	}
}