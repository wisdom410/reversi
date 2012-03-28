/*
ID: lazydom1
LANG: JAVA
TASK: TestLogin.java
Created on: 2012-3-27-下午12:46:46
Author: lazydomino[AT]163.com(pisces)
*/

package RunGame;

import gui.Login;

import java.awt.EventQueue;

public class TestLogin {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				
				//System.out.println("\u7880");
				new Login();
			}
		}) ;
		
	}
}
