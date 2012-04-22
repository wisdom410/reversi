/*
ID: lazydom1
LANG: JAVA
TASK: Room.java
Created on: 2012-3-29-下午8:42:19
Author: lazydomino[AT]163.com(pisces)
*/

package core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Vector;

import sql.ExecSql;

public class Room  implements Serializable{

	public Room(String roomName)
	{
		this.roomName = roomName;
		
		userList = new Vector<User>();
		chat ="";
		
		
		play1 = "";
		play2 = "";
		
		status = "等待玩家";
		
		player1Ready = player2Ready = false;
		
		canview = true;
		
		chessManList = new ChessManList();
		
		chessManList.add(4, 4, false);
		chessManList.add(4, 5, true);
		chessManList.add(5, 4, true);
		chessManList.add(5, 5, false);
		next ="";
		black = "";
		finish = false;

		
	}
	
	/*
	 * 下棋结束后更新用户信息
	 */
	private void upUserInfo(String name,int Score)
	{
		
		String cm = "UPDATE users SET score = "+Score+" WHERE username = \'"+name+"\'";
		System.out.println(cm);
		
		
		try {
			ExecSql.state.execute(cm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void flushRoom()
	{
		//play1 = "";
		//play2 = "";
		score1 = 0;
		score2 = 0;
		image1 = 0;
		image2 = 0;
		
		for(User u:userList)
		{
			if(u.isPlayer())
			{
				if(play1.length()==0)
				{
					play1 = u.getUsername();
					score1 = u.getScore();
					image1 = u.getImage();
				}else
				{
					if(play2.length()==0)
					{
						play2 = u.getUsername();
						score2 = u.getScore();
						image2 = u.getImage();
					}
				}
				
			}
		}
	}
	
	public void setcanView(boolean can)
	{
		this.canview = can;
	}
	
	public boolean getcanView()
	{
		return this.canview;
	}
	
	public void addUser(User user)
	{
		userList.add(user);
		flushRoom();
	}
	
	public int getnum_pep()
	{
		return userList.size();
	}
	
	public void delUser(User user)
	{
		userList.remove(user);
		flushRoom();
	}
	
	public String getRoomName()
	{
		return roomName;
	}
	public Vector<User> getUserList()
	{
		return userList;
	}
	
	public int getScore1()
	{
		return score1;
	}
	
	public int getScore2()
	{
		return score2;
	}
	
	public int getScore(String name)
	{
		for(User u:userList)
		{
			if(u.getUsername().equals(name))
				return u.getScore();
		}
		return 0;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String s)
	{
		status = s;
	}
	public String getPlayer1()
	{
		return play1;
	}
	
	public String getPlayer2()
	{
		return play2;
	}
	
	public boolean isPlayer1isEmpty()
	{
		if(play1.length()==0)
			return true;
		return false;
	}
	
	public boolean isPlayer2isEmpty()
	{
		if(play2.length()==0)
			return true;
		return false;
	}
	
	public void setPlayer1(User user)
	{
		this.play1 = user.getUsername();
		this.score1 = user.getScore();
		this.image1 = user.getImage();
	}
	
	public void setPlayer2(User user)
	{
		this.play2 = user.getUsername();
		this.score2 = user.getScore();
		this.image2 = user.getImage();
	}
	
	public void setChessManList (ChessManList c)
	{
		this.chessManList = c;
	}
	
	public ChessManList getChessManList()
	{
		return this.chessManList;
	}
	
	public void setNext(String s)
	{
		this.next = s;
	}
	
	public String getNext()
	{
		return this.next;
	}
	
	public void setPlayer1Ready(boolean b)
	{
		player1Ready = b;
	}
	public boolean getPlayer1Ready()
	{
		return player1Ready;
	}
	
	public void setPlayer2Ready(boolean b)
	{
		player2Ready = b;
	}
	public boolean getPlayer2Ready()
	{
		return player2Ready;
	}
	
	public int getImage1()
	{
		return image1;
	}
	
	public int getImage2()
	{
		return image2;
	}
	
	public void setBlack(String s)
	{
		this.black = s;
	}
	
	public String getBlack()
	{
		return this.black;
	}
	
	public void setFinish(boolean b)
	{
		this.finish = b;
		
		if(chessManList.getBlackNum()>=chessManList.getwhiteNum())
		{
			if(play1.equals(black))
				win = play1;
			else
				win = play2;
		}else
		{
			if(play1.equals(black))
				win = play2;
			else
				win = play1;
		}
		
		if(win.equals(play1))
		{
			upUserInfo(play1, this.getScore(play1)+10);
			upUserInfo(play2, this.getScore(play2)-10);
		}else
		{
			upUserInfo(play1, this.getScore(play1)-10);
			upUserInfo(play2, this.getScore(play2)+10);
		}
	}
	
	public void setFinish(boolean b,String player)
	{
		this.finish = b;
		
		if(player.equals(play1))
		{
			win = play2;
		}else
		{
			win = play1;
		}
		
		if(win.equals(play1))
		{
			upUserInfo(play1, this.getScore(play1)+10);
			upUserInfo(play2, this.getScore(play2)-10);
		}else
		{
			upUserInfo(play1, this.getScore(play1)-10);
			upUserInfo(play2, this.getScore(play2)+10);
		}
		
	}
	
	public String getWin()
	{
		return win;
	}
	
	public String getLose()
	{
		return lose;
	}

	public boolean isFinish()
	{
		return finish;
	}
	
	public void setUndo(int a)
	{
		this.undoStatus = a;
	}
	public int getUndo()
	{
		return undoStatus;
	}
	
	public ChessManList getUndoChessManList() {
		return undoChessManList;
	}

	public void setUndoChessManList(ChessManList undoChessManList) {
		this.undoChessManList = undoChessManList;
	}
	
//	public String getChat()
//	{
//		return chat;
//	}
//	
//	public void addChat(String s)
//	{
//		this.chat+=s;
//		System.out.println("Sth. add "+s);
//	}
	
	private String roomName;
	private String play1,play2,win,lose;
	private boolean player1Ready,player2Ready;
	private String status;
	private int score1,score2;
	private int image1,image2;
	private boolean canview;
	private Vector<User> userList;
	private ChessManList chessManList,undoChessManList;
	


	private String next;
	private String black;
	public String chat;
	private boolean finish;
	private int undoStatus = 0;//0->normal,1->play1 want to undo,2->play2 want to undo,3->yes to undo,4-> no to undo
}
