package com.andrewrs.userinterface;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;

public class NewUserForm extends ProgFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userName;
	private JPasswordField passwordField;
	private AdminMenu adminMenuBar;
	private JCheckBox chkIsAdmin;
	private JLabel lblPasswordMessage,lblUserMsg1,lblUsermsg2;
	public NewUserForm()
	{
		super("NewUser");
		init();
	}

	public NewUserForm(UserData user) 
	{
		super("NewUser");
		init();
	}
	private void init()
	{
		this.setTitle("Create new User");
		getContentPane().setLayout(new GridLayout(5, 2, 0, 0));
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_8);
		
		JLabel lblUsername = new JLabel("Username");
		panel_8.add(lblUsername);
		
		JPanel panel_7 = new JPanel();
		getContentPane().add(panel_7);
		panel_7.setLayout(new GridLayout(1, 2, 0, 0));
		
		chkIsAdmin = new JCheckBox("isAdmin");
		panel_7.add(chkIsAdmin);
		
		JPanel panel_6 = new JPanel();
		getContentPane().add(panel_6);
		
		userName = new JTextField();
		panel_6.add(userName);
		userName.setColumns(10);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel);
		
		lblUserMsg1 = new JLabel("                  ");
		panel.add(lblUserMsg1);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_1);
		
		JLabel lblPassword = new JLabel("Password:");
		panel_1.add(lblPassword);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_2.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_2);
		
		lblUsermsg2 = new JLabel("                  ");
		panel_2.add(lblUsermsg2);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_3.add(passwordField);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4);
		
		lblPasswordMessage = new JLabel("");
		lblPasswordMessage.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblPasswordMessage);
		
		JPanel panel_5 = new JPanel();
		getContentPane().add(panel_5);
		
		final JButton btnSave = new JButton("   Save   ");
		panel_5.add(btnSave);
		
		JPanel panel_9 = new JPanel();
		getContentPane().add(panel_9);
		
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isValidEmail())
				{
					lblUserMsg1.setText("Attempting to");
					lblUsermsg2.setText("Save");
					save();
				}
				else if(!isValidEmail())
				{
					lblUserMsg1.setText("Invalid Username:");
					lblUsermsg2.setText("Should be an Email");
				}
			}

		});
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER && isValidEmail())
				{
					lblUserMsg1.setText("Attempting to");
					lblUsermsg2.setText("Save");
					save();
				}
				else if(!isValidEmail())
				{
					lblUserMsg1.setText("Invalid Username:");
					lblUsermsg2.setText("Should be an Email");
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		adminMenuBar = new AdminMenu();
		adminMenuBar.refreshFields();
		this.setJMenuBar(adminMenuBar);

		adminMenuBar.refreshFields();
		this.setSize(280, 200);
	}
	protected void refreshForm() {
		// TODO Auto-generated method stub
		
	}
	private boolean isValidEmail()
	{
		return NewUserForm.validateEmail(userName.getText());
	}
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		private static boolean validateEmail(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
		}
	protected void save() 
	{
		UserData newUser=new UserData();
		newUser.setUserName(userName.getText());
		newUser.setAdmin(chkIsAdmin.isSelected());
		newUser.setHashedPass(LoginForm.hashPass(passwordField.getPassword()));
		newUser.postUser();
		{
			lblUserMsg1.setText("Accepted");
			ProgramState.setState("UserManager");
		}
		
	}

	public void clearForm() 
	{
		lblUserMsg1.setText("");
		userName.setText("");
		passwordField.setText("");
		chkIsAdmin.setSelected(false);
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whileRunning() {
		// TODO Auto-generated method stub
		
	}
}
