package com.andrewrs.userinterface;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class UserManagerForm extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private AdminMenu adminMenuBar;
	private ArrayList<UserData> users;
	private UserData currentUser;
	public UserManagerForm(UserData user)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String headers[]= {"User","Password","isAdmin"};
		table=new JTable();

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		JTableHeader header = new JTableHeader();
		header.add(new JLabel(headers[0]));
		header.add(new JLabel(headers[1]));
		header.add(new JLabel(headers[2]));
		table.setTableHeader(header);
		model.setColumnIdentifiers(headers);
		
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		{
			adminMenuBar = new AdminMenu();
			this.setJMenuBar(adminMenuBar);
		}
		
		final JButton btnNewUser = new JButton("New User");
		panel.add(btnNewUser);
		
		final JButton btnDeleteUser = new JButton("Delete");
		panel.add(btnDeleteUser);
		btnNewUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProgramState.setState(ProgramState.NEW_USER);
				
			}
		});
		btnDeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				users.get(table.getSelectedRow()).deleteOnWS();
				refreshTable();
				
			}
		});
		this.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					setVisible(false);
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		adminMenuBar.refreshFields();
		this.setSize(500, 500);
	}
	public void refreshTable()
	{
		
		int columns=3;
		users=currentUser.getAllUsersData();
		int rows=users.size();
		String data[][]=new String[rows][columns];

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if(table.getRowCount()>0)
			model.setRowCount(0);
		for(int i=0;i<users.size();i++)
		{
			data[i][0]=users.get(i).getUserName();
			data[i][1]=users.get(i).getHashedPass().toString();
			data[i][2]=users.get(i).isAdmin()?"Admin":"User";
			model.addRow(data[i]);
		}
		String headers[]= {"User","Password","isAdmin"};
		
		model.setColumnIdentifiers(headers);
	}
	public void setUser(UserData currentUser2) {
		currentUser=currentUser2;
		
	}
	

}
