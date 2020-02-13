package finalDemo;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LoginPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginPage frame = new LoginPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	public static String USERNAME;
	public static final int FIELD_HEIGHT = 30;
	public static final int FIELD_WIDHT = 250;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_WIDHT = 250;
	public static final int WIDTH = 350;
	public static final int HEIGHT = 600;
	public static final int TEXT_MARGIN = 43;
	public static final int BUTTON_MARGIN = 43;
	
	// Components
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private SqlConnection sqlConnection;
	private Connection connection = null;
	private Client client=null;

	/**
	 * Create the frame.
	 */
	public LoginPage(Client client_func) 
	{
		client=client_func;
		initialize();
		
	}
	
	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Main.X_POS, Main.Y_POS, WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Logo
		JLabel lblImageLogo = new JLabel("New label");
		lblImageLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageLogo.setBounds(108, 56, 120, 120);
		Image imgLogin = new ImageIcon(this.getClass().getResource("/Logo.jpg")).getImage();
		Image scaledImage = imgLogin.getScaledInstance(lblImageLogo.getWidth(), lblImageLogo.getHeight(), Image.SCALE_SMOOTH);
		lblImageLogo.setIcon(new ImageIcon(scaledImage));
		contentPane.add(lblImageLogo);
		
		// Username
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(43, 219, 85, 34);
		contentPane.add(lblUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(43, 249, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(43, 290, 85, 34);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(43, 320, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(passwordField);
		
		// Login Button
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				login();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBounds(BUTTON_MARGIN, 381, BUTTON_WIDHT, BUTTON_HEIGHT);
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
		btnRegister.setBounds(43, 432, BUTTON_WIDHT, BUTTON_HEIGHT);
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
		btnBack.setBounds(10, 522, 74, 30);
		contentPane.add(btnBack);
	}
	
	public void login()
	{
		String username = textFieldUsername.getText();
		String password = new String(passwordField.getPassword());
		String text = "";

        if (username.isEmpty())
            text += "Please Input Your Username\n";
        if (password.isEmpty())
            text += "Please Input Your Password";
        
        if (!text.isEmpty())
        {
            JOptionPane.showMessageDialog(null, text);
        }
        
        else
        {
			client.Send(username);
			String found="false";
			try {
				found=client.MessageReceive();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(found.equalsIgnoreCase("true")) {
				client.Send(password);
				try {
					 found=client.MessageReceive();
				}catch(Exception e) {
					e.printStackTrace();
				}
	            if (found.equalsIgnoreCase("true"))
	            {
	            	JOptionPane.showMessageDialog(null, "Login Successfully");
	            	try {
	            		USERNAME = username;
	            		client.Send("Here");
	            		byte [] image=client.getImage();
	            		int id=Integer.valueOf(client.MessageReceive());
	            		System.out.println("id:"+id);         		
	            		String name=client.MessageReceive();
	            		System.out.println("name:"+name);            		
	            		String phonenumber=client.MessageReceive();
	            		System.out.println("Phonenumeber:"+phonenumber);
	            		String Email=client.MessageReceive();
	            		System.out.println("Email:"+Email);
	            		String address=client.MessageReceive();
	            		
	            		System.out.println("Address:"+address);
	            		Connection connectDB=SqlConnection.dbConnector();
	            		String query="INSERT INTO Profile (ID,Name,Username,Password,Phonenumber,"
	    					+ "Email,Address,Photo) VALUES ("+id+",'"+name+"','"+username+"','"
	    							+password+"','"+phonenumber+"','"+Email+"','"+address+"',?)";
	            		System.out.println(query);
	            		PreparedStatement stmt=connectDB.prepareStatement(query);
	            		stmt.setBytes(1,image);
	            		stmt.executeUpdate();
	            		connectDB.close();		
	            		
	            		client.setName(name);
	            		client.setAddress(address);
	    				client.setPhonenumber(phonenumber);
	    				client.setEmail(Email);
	            		// Go to next page
	    				
	                    goToProfilePage();
	                }catch (Exception e) {
						e.printStackTrace();
					}
	            	}
	            else
                {
                	JOptionPane.showMessageDialog(null, "Incorrect Password");
                }
			
            }
            else
            {
            	JOptionPane.showMessageDialog(null, "No Username Found");
            }
            
            }
        }
	
	public void goToProfilePage()
	{
		this.dispose();
		ProfilePage profilePage = new ProfilePage(client);
		profilePage.setVisible(true);
	}
	
	public void goToRegisterPage() 
	{
		this.dispose();
		client.Send("false");
		client.Send("finish");
		client.Send("3");
		RegisterPage registerPage = new RegisterPage(client);
		registerPage.setVisible(true);
	}
	
	
	public void goBack()
	{
		this.dispose();
		client.Send("false");
		Food food = new Food(client);
		food.frame.setVisible(true);
	}

}
