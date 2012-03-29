/*
ID: lazydom1
LANG: JAVA
TASK: TestHall.java
Created on: 2012-3-29-下午10:07:38
Author: lazydomino[AT]163.com(pisces)
*/

package RunGame;

import gui.Hall;
import gui.Login;

import java.awt.EventQueue;
import java.io.IOException;

public class TestHall {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				
				new Hall("sdlwwlp");
			}
		}) ;
		
	}
}
