/*
ID: lazydom1
LANG: JAVA
TASK: RightPanel.java
Created on: 2012-2-19-下午1:08:37
Author: lazydomino@163.com(pisces)
*/

package gui;

import java.awt.event.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * RightPanel
 */
public class RightJPanel extends JPanel{

	public RightJPanel()
	{
		super();

		this.setLayout(new BorderLayout());
		addPanel();
	}

	private void addPanel()
	{
		downPanel = new JPanel();
		downPanel.setLayout(new BorderLayout());

		textArea = new JTextArea(12, 19);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		downPanel.add(scrollPane, BorderLayout.CENTER);

		textField = new JTextField();
		textField.addKeyListener(new EnterKeyListener());

		ImageIcon sendImg = new ImageIcon("res"+File.separator+"send.png");
		sendButton = new JButton(sendImg);
		sendButton.addActionListener( new EnterKeyAction());




		ImageIcon faceImg = new ImageIcon("res"+File.separator+"face.png");
		faceButton = new JButton(faceImg);

		sendButton.setPreferredSize(new Dimension(20, 20));
		faceButton.setPreferredSize(new Dimension(20, 20));

		JPanel funcPanel = new JPanel();
		funcPanel.add(sendButton);
		funcPanel.add(faceButton);

		JPanel panelIndown = new JPanel();
		panelIndown.setLayout(new BorderLayout());
		panelIndown.add(textField);
		panelIndown.add(funcPanel, BorderLayout.EAST);

		downPanel.add(panelIndown, BorderLayout.SOUTH);

		this.add(downPanel, BorderLayout.SOUTH);

	}


	//var
	JPanel downPanel;
	JTextArea textArea;
	JTextField textField;
	JButton sendButton, faceButton;

	/*
	 * 内部enter键监听类
	 */
	class EnterKeyAction extends AbstractAction
	{

		public void actionPerformed(ActionEvent event)
		{
			if(textField.getText().equals("")) return;
			textArea.append("\n"+textField.getText());
			textField.setText("");
		}
	}
	/*
	 * 内部enter键盘键监听类
	 */
	class EnterKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent event)
		{
			if(event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(textField.getText().equals("")) return;
				textArea.append("\n"+textField.getText());
				textField.setText("");
			}
		}
	}


}