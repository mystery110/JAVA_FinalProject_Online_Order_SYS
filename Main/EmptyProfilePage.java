package finalDemo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class EmptyProfilePage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EmptyProfilePage frame = new EmptyProfilePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	// Constants
	public static final int LABEL_WIDTH = 250;
	public static final int LABEL_HEIGHT = 30;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_WIDHT = 250;
	public static final int WIDTH = 350;
	public static final int HEIGHT = 600;
	public static final int TEXT_MARGIN = 43;
	public static final int BUTTON_MARGIN = 43;
	private Client client;

	/**
	 * Create the frame.
	 */
	public EmptyProfilePage(Client client_func) 
	{
		client=client_func;
		initialization();
	}
	
	public void initialization()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Main.X_POS, Main.Y_POS, WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Image Label
		JLabel lblImage = new JLabel("No Photo");
		Border borderImage = BorderFactory.createLineBorder(Color.BLACK);
		lblImage.setBorder(borderImage);
		lblImage.setHorizontalAlignment(JLabel.CENTER);
		lblImage.setBounds(108, 26, 120, 120);
		contentPane.add(lblImage);
				
		// Name
		JLabel lblName = new JLabel("Name: None");
		lblName.setBorder(borderImage);
		lblName.setHorizontalAlignment(JLabel.CENTER);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(43, 167, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblName);
		
	
		// Username
		JLabel lblUsername = new JLabel("Username: None");
		lblUsername.setBorder(borderImage);
		lblUsername.setHorizontalAlignment(JLabel.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(43, 208, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblUsername);
				
		// Password
		JLabel lblPassword = new JLabel("Password: None");
		lblPassword.setBorder(borderImage);
		lblPassword.setHorizontalAlignment(JLabel.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(43, 249, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblPassword);
		
		// Phone Number
		JLabel lblPhoneNumber = new JLabel("Phone Number: None");
		lblPhoneNumber.setBorder(borderImage);
		lblPhoneNumber.setHorizontalAlignment(JLabel.CENTER);
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhoneNumber.setBounds(43, 290, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblPhoneNumber);
		
		// Email
		JLabel lblEmail = new JLabel("Email: None");
		lblEmail.setBorder(borderImage);
		lblEmail.setHorizontalAlignment(JLabel.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(43, 331, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblEmail);
		
		// Address
		JLabel lblAddress = new JLabel("Address: None");
		lblAddress.setBorder(borderImage);
		lblAddress.setHorizontalAlignment(JLabel.CENTER);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(43, 372, LABEL_WIDTH, LABEL_HEIGHT);
		contentPane.add(lblAddress);
		
		// Login Button
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				goToLoginPage();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(43, 429, BUTTON_WIDHT, BUTTON_HEIGHT);
		contentPane.add(btnLogin);
		
		// Register Button
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				goToRegisterPage();
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegister.setBounds(43, 479, BUTTON_WIDHT, BUTTON_HEIGHT);
		contentPane.add(btnRegister);
		
		// Back Button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				goBack();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBack.setBounds(0, 533, 74, 30);
		contentPane.add(btnBack);
	}
	
	/*---> Functions <---*/
	
	public void goToLoginPage()
	{
		this.dispose();
		client.Send("Finish");
		client.Send("2");
		LoginPage loginPage = new LoginPage(client);
		loginPage.setVisible(true);
	}
	
	public void goToRegisterPage() 
	{
		this.dispose();
		client.Send("Finish");
		client.Send("3");
		RegisterPage registerPage = new RegisterPage(client);
		registerPage.setVisible(true);
	}
	
	public void goBack()
	{
		this.dispose();
		Food food = new Food(client);
		food.frame.setVisible(true);
	}

}
