/*
ID: lazydom1
LANG: JAVA
TASK: User.java
Created on: 2012-3-29-下午6:11:41
Author: lazydomino[AT]163.com(pisces)
*/

package core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * 这个类是用来储存用户信息的，
 * 包括但不限于记录当前用户的input，output流，
 * 所属房间标记，是否是玩家（对应于观赛者），
 * 积分，头像，性别，邮箱，用户名等。。。
 */
public class User {


	public User(String username, char[] passwd,int sex,String nickname,String email,int score,int image)
	{
		this.username = username;
		this.sex = sex;
		this.nickname = nickname;
		this.email = email;
		this.score = score;
		this.image = image;
		this.passwd = passwd;
	}
	
	
	public char[] getPasswd() {
		return passwd;
	}
	public void setPasswd(char[] passwd) {
		this.passwd = passwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public boolean isPlayer() {
		return isPlayer;
	}
	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	private String username;
	private char[] passwd;
	private String nickname;
	private String email;
	private int sex,score,image;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int room;
	private boolean isPlayer;
}
