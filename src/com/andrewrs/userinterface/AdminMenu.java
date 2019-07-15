package com.andrewrs.userinterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AdminMenu extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu adminMenu;
	private JMenuItem manageLocation,manageUsers,addUser;
	public AdminMenu()
	{
		
		adminMenu = new JMenu("Admin Options");
		manageLocation=new JMenuItem("Location manager");
		manageUsers=new JMenuItem("Manage Users");
		addUser=new JMenuItem("Add User");
		//if(ProgramState.getState()!=ProgramState.NEW_APPT)
		//if(ProgramState.getState()!=ProgramState.MANAGE_REQUESTS)
			adminMenu.add(manageLocation);
		//if(ProgramState.getState()!=ProgramState.MANAGE_USERS)
			adminMenu.add(manageUsers);
		//if(ProgramState.getState()!=ProgramState.NEW_USER)
			adminMenu.add(addUser);
		this.add(adminMenu);
		manageUsers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			
					ProgramState.setState(ProgramState.MANAGE_USERS);
				
			}
			
		});
		addUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					ProgramState.setState(ProgramState.NEW_USER);
				
				
			}
			
		});
		manageLocation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					ProgramState.setState(ProgramState.MANAGE_LOCATIONS);
			}
			
		});
	}
	public void refreshFields()
	{
		adminMenu.removeAll();

		//if(ProgramState.getState()!=ProgramState.NEW_APPT)
		//if(ProgramState.getState()!=ProgramState.MANAGE_REQUESTS)
			adminMenu.add(manageLocation);
		//if(ProgramState.getState()!=ProgramState.MANAGE_USERS)
			adminMenu.add(manageUsers);
		//if(ProgramState.getState()!=ProgramState.NEW_USER)
			adminMenu.add(addUser);
	}
}
