/*
ID: lazydom1
LANG: JAVA
TASK: ServerMain.java
Created on: 2012-3-12-上午8:20:04
Author: lazydomino@163.com(pisces)
*/

package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.IDNetClass;
import net.LoginNetClass;

public class ServerMain {

	public ServerMain()
	{
		try {
			
			ServerSocket s = new ServerSocket(8090);
			
			while(true)
			{
				Socket incoming = s.accept();
				
				Runnable r = new Server(incoming);
				Thread t = new Thread(r);
				t.start();
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	class Server implements Runnable
	{
		public Server(Socket i)
		{
			incoming = i;
		}
		
		
		public void run()
		{
			
			try {
				
				
				ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(incoming.getInputStream()));
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(incoming.getOutputStream()));
				
				try {
					
					IDNetClass cmd = (IDNetClass) in.readObject();
					int cmdType = cmd.getID();
					
					LoginNetClass c = (LoginNetClass) cmd;
					String username = c.getUsername();
					System.out.println(username);

					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally
				{
					incoming.close();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		private Socket incoming;
	}
	
	
	
		
}
