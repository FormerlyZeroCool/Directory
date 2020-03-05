package com.andrewrs.userinterface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class LoginForm extends JFrame 
{

	/**
	 * 
	 */
	private JTextField userIdTB;
	private JPasswordField passwordTB;
	private JButton loginBtn;
	private JPanel pnl5,pnl4,pnl3,pnl2,pnl1;
	private static final long serialVersionUID = 1L;
	public UserData currentUser;
	private static BigInteger salt=new BigInteger("28694504036815388098806041306");
	public LoginForm(String title)
	{
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentUser=new UserData("");
		this.setVisible(true);
		getContentPane().setLayout(new GridLayout(0, 1, 5, 0));
		userIdTB=new JTextField("");
		userIdTB.setHorizontalAlignment(SwingConstants.LEFT);
		userIdTB.setColumns(15);
		passwordTB=new JPasswordField("");
		passwordTB.setColumns(15);
		loginBtn=new JButton("Login");
		pnl1=new JPanel();
		pnl2=new JPanel();
		pnl3=new JPanel();
		pnl4=new JPanel();
		pnl5=new JPanel();
		pnl5.setLayout(new GridLayout(1,3,0,5));
		getContentPane().add(pnl1);
		getContentPane().add(pnl2);
		getContentPane().add(pnl3);
		getContentPane().add(pnl4);
		getContentPane().add(pnl5);
		pnl5.add(loginBtn);
		pnl1.setLayout(new GridLayout(0, 1, 0, 0));

		pnl1.add(new JLabel("User:"));
		pnl3.add(new JLabel("Password:"));
		pnl4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		pnl4.add(passwordTB);
		pnl3.setLayout(new GridLayout(1, 1, 0, 0));
		pnl2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		pnl2.add(userIdTB);
		pnl5.add(new JLabel(" "));
		pnl2.add(new JLabel(" "));
		pnl4.add(new JLabel(" "));
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				attemptLogin();
			}
		});
		this.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				
				if(e.getSource().equals(userIdTB))
				{
					currentUser.setUserName(userIdTB.getText());
				}
			}

			public void keyTyped(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}
		});
		passwordTB.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				
				currentUser.setUserName(userIdTB.getText());
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					attemptLogin();
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					currentUser.setUserName(userIdTB.getText());
					attemptLogin();
				}
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.setBounds(200,200,320, 180);
	}
	
	protected void attemptLogin() {

		currentUser.getPassFromWeb();
		System.out.println(hashPass(passwordTB.getPassword()));
		if(hashPass(passwordTB.getPassword()).equals(currentUser.getHashedPass()))
			currentUser.setLoggedIn(true);
		
	}
	public boolean isLoggedIn() {
		return currentUser.isLoggedIn();
	}
	public void setLoggedIn(boolean loggedIn) {
		currentUser.setLoggedIn(loggedIn);
	}
	public static BigInteger hashPass(char[] cs)
	{
		BigInteger hash=new BigInteger("0");
		//toLower
		for(int i = 0;i < cs.length;i++)
		{
			if(cs[i] < 97)
				cs[i] = (char) (cs[i]-32);
		}
		for(long i=0;i<cs.length;i++)
		{
			long charIndex=(int)cs[(int) i];
			hash=hash.add(new BigInteger(Long.toString((i*65536L+charIndex)<<2)).add(hash));
			hash = hash.multiply(new BigInteger("3"));
		}
		hash=hash.add(salt.add(hash));
		return hash;
	}
	public void resetUser()
	{
		userIdTB.setText("");
		passwordTB.setText("");
		currentUser.closeLoginSession();
	}
}
