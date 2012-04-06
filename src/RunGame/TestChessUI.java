/*
ID: lazydom1
LANG: JAVA
TASK: TestChessUI.java
Created on: 2012-2-18-����7:56:44
Author: lazydomino@163.com(pisces)
*/

package RunGame;

import gui.ChessFrame_old;

import java.awt.EventQueue;

public class TestChessUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				
				//System.out.println("\u7880");
				new ChessFrame_old();
			}
		}) ;
		
	}
}

