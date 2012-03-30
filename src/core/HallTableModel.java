package core;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/*
 ID: lazydom1
 LANG: JAVA
 TASK: TableModel.java
 Created on: 2012-3-30-下午1:21:32
 Author: lazydomino[AT]163.com(pisces)
 */

/*
 * 这个类是游戏大厅现实房间列表table的model
 */
public class HallTableModel extends AbstractTableModel {

	public HallTableModel() {
		content = new Vector();
	}

	public void addRow(String roomName, int score1, int score2,
			boolean canview, int num_pep, String status) {
		Vector v = new Vector(6);
		//v.add(0, new Integer(content.size()));
		v.add(0, roomName);
		v.add(1, score1);
		v.add(2, score2);
		v.add(3, canview);
		v.add(4, num_pep);
		v.add(5, status);
		content.add(v);
	}

	public void addRoom(Vector<Room>  roomList) {
		content.clear();
		
		for(Room r:roomList)
		{
			addRow(r.getRoomName(), r.getScore1(), r.getScore2(), r.getcanView(), r.getnum_pep(), r.getStatus());
		}
		
	}
	
	public void removeRow(int row) {
		content.remove(row);
	}

	public int getColumnCount() {
		return title.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public String getColumnName(int r) {
		return title[r];
	}

	public Object getValueAt(int r, int c) {
		//System.out.println(((Vector) content.get(r)).get(c));
		return ((Vector) content.get(r)).get(c);
	}

	public void clearRoom()
	{
		content.clear();
	}

	private String[] title = { "房间名称", "玩家1积分", "玩家2积分", "能否观战", "房间人数", "房间状态" };
	private Vector content;
}
