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

/**
 * 
 * @author will
 *房间类，包含当前房间内用户信息，棋盘信息，聊天信息
 */
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
		image1 = image2 = 0;

		
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
	
	/*
	 * 刷新房间信息
	 */
	private void flushRoom()
	{
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
	/*
	 * 设置房间是否能被观战
	 */
	public void setcanView(boolean can)
	{
		this.canview = can;
	}
	/*
	 * 获取此房间是否能被观看
	 */
	public boolean getcanView()
	{
		return this.canview;
	}
	/*
	 * 增加一个用户
	 */
	public void addUser(User user)
	{
		userList.add(user);
		flushRoom();
	}
	/*
	 * 获取当前房间内人数
	 */
	public int getnum_pep()
	{
		return userList.size();
	}
	
	/*
	 * 删除一个用户
	 */
	public void delUser(User user)
	{
		userList.remove(user);
		flushRoom();
	}
	
	/*
	 * 获取房间名
	 */
	public String getRoomName()
	{
		return roomName;
	}
	/*
	 * 获取次房间用户列表
	 */
	public Vector<User> getUserList()
	{
		return userList;
	}
	/*
	 * 获取玩家1的积分
	 */
	public int getScore1()
	{
		return score1;
	}
	
	/*
	 * 获取玩家2的积分
	 */
	public int getScore2()
	{
		return score2;
	}
	/*
	 * 获取某个玩家的积分
	 */
	public int getScore(String name)
	{
		for(User u:userList)
		{
			if(u.getUsername().equals(name))
				return u.getScore();
		}
		return 0;
	}
	/*
	 * 获取房间状态
	 */
	public String getStatus()
	{
		return status;
	}
	/*
	 * 设置房间状态
	 */
	public void setStatus(String s)
	{
		status = s;
	}
	/*
	 * 获取玩家1的名字
	 */
	public String getPlayer1()
	{
		return play1;
	}
	/*
	 * 获取玩家2的名字
	 */
	public String getPlayer2()
	{
		return play2;
	}
	/*
	 * 玩家1是否空缺
	 */
	public boolean isPlayer1isEmpty()
	{
		if(play1.length()==0)
			return true;
		return false;
	}
	/*
	 * 玩家2是否空缺
	 */
	public boolean isPlayer2isEmpty()
	{
		if(play2.length()==0)
			return true;
		return false;
	}
	/*
	 * 设置玩家1
	 */
	public void setPlayer1(User user)
	{
		this.play1 = user.getUsername();
		this.score1 = user.getScore();
		this.image1 = user.getImage();
	}
	/*
	 * 设置玩家2
	 */
	public void setPlayer2(User user)
	{
		this.play2 = user.getUsername();
		this.score2 = user.getScore();
		this.image2 = user.getImage();
	}
	
	/*
	 * 设置棋盘
	 */
	public void setChessManList (ChessManList c)
	{
		this.chessManList = c;
	}
	
	/*
	 * 获取棋盘
	 */
	public ChessManList getChessManList()
	{
		return this.chessManList;
	}
	/*
	 * 设置下一步下棋的人
	 */
	public void setNext(String s)
	{
		this.next = s;
	}
	/*
	 * 获取下一步下棋的人
	 */
	public String getNext()
	{
		return this.next;
	}
	/*
	 * 设置玩家1为准备状态
	 */
	public void setPlayer1Ready(boolean b)
	{
		player1Ready = b;
	}
	/*
	 * 获取玩家1是否为准备状态
	 */
	public boolean getPlayer1Ready()
	{
		return player1Ready;
	}
	/*
	 * 设置玩家2为准备状态
	 */
	public void setPlayer2Ready(boolean b)
	{
		player2Ready = b;
	}
	/*
	 * 获取玩家2是否为准备状态
	 */
	public boolean getPlayer2Ready()
	{
		return player2Ready;
	}
	/*
	 * 获取玩家1头像
	 */
	public int getImage1()
	{
		return image1;
	}
	/*
	 * 获取玩家2头像
	 */
	public int getImage2()
	{
		return image2;
	}
	/*
	 * 设置黑色棋子是谁
	 */
	public void setBlack(String s)
	{
		this.black = s;
	}
	/*
	 * 获取黑色棋子是谁
	 */
	public String getBlack()
	{
		return this.black;
	}
	/*
	 * 设置此棋盘下完了
	 */
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
	/*
	 * 设置棋盘下完了，并且设置谁是赢家
	 */
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
	/*
	 * 获取赢家名字
	 */
	public String getWin()
	{
		return win;
	}
	/*
	 * 获取败家名字
	 */
	public String getLose()
	{
		return lose;
	}

	/*
	 * 获取此棋盘是否结束
	 */
	public boolean isFinish()
	{
		return finish;
	}
	/*
	 * 设置为悔棋状态
	 */
	public void setUndo(int a)
	{
		this.undoStatus = a;
	}
	/*
	 * 得到悔棋状态
	 */
	public int getUndo()
	{
		return undoStatus;
	}
	/*
	 * 获取悔棋棋盘
	 */
	public ChessManList getUndoChessManList() {
		return undoChessManList;
	}

	/*
	 * 设置悔棋棋盘
	 */
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
