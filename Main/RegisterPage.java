package finalDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class RegisterPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterPage frame = new RegisterPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	
	// Constants
	public static final int FIELD_HEIGHT = 30;
	public static final int FIELD_WIDHT = 250;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_WIDHT = 250;
	
	// Components
	private JTextField textFieldName, textFieldUsername, textFieldPhoneNumber, textFieldEmail, textFieldAddress;
	private JPasswordField passwordField, confirmPasswordField;
	private JLabel lblImage;
	private JButton btnSignUp, btnUpload;
	private SqlConnection sqlConnection;
	private Connection connection = null;
	private byte[] byteArrayImage = null;
	private Client client=null;
	private boolean stopUpdate=true;

	/**
	 * Create the frame.
	 */
	public RegisterPage(Client client_func) 
	{
		initialize();
		sqlConnection = new SqlConnection();
		//connection = sqlConnection.dbConnector();
		client=client_func;
	}
	
	
	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Main.X_POS, Main.Y_POS, 350, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Image Label
		lblImage = new JLabel("Upload Your Photo");
		Border borderImage = BorderFactory.createLineBorder(Color.BLACK);
		lblImage.setBorder(borderImage);
		lblImage.setHorizontalAlignment(JLabel.CENTER);
		lblImage.setBounds(43, 23, 120, 120);
		contentPane.add(lblImage);
		
				
		// Upload Button
		btnUpload = new JButton("Upload");
		btnUpload.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
			uploadImage();
			}
		});
		btnUpload.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpload.setBounds(204, 61, 89, 40);
		contentPane.add(btnUpload);
		
		// Name
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(43, 141, 85, 34);
		contentPane.add(lblName);
			
		textFieldName = new JTextField();
		textFieldName.setBounds(43, 171, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
	
		// Username
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(43, 188, 85, 34);
		contentPane.add(lblUsername);
			
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(43, 218, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
				
		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(43, 237, 85, 34);
		contentPane.add(lblPassword);
				
		passwordField = new JPasswordField();
		passwordField.setBounds(43, 267, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(passwordField);
		
		// Confirm Password
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmPassword.setBounds(43, 284, 120, 34);
		contentPane.add(lblConfirmPassword);
						
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(43, 314, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(confirmPasswordField);
		
		// Phone Number
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhoneNumber.setBounds(43, 330, 120, 34);
		contentPane.add(lblPhoneNumber);
			
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(43, 360, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(43, 375, 120, 34);
		contentPane.add(lblEmail);
			
		textFieldEmail= new JTextField();
		textFieldEmail.setBounds(43, 405, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		// Address
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(43, 422, 120, 34);
		contentPane.add(lblAddress);
			
		textFieldAddress= new JTextField();
		textFieldAddress.setBounds(43, 452, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		// Sign Up Button
		btnSignUp = new JButton("SIGN UP");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addData();
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSignUp.setBounds(43, 500, BUTTON_WIDHT, BUTTON_HEIGHT);
		contentPane.add(btnSignUp);
		
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
	
	
	public void addData()
	{
		String result="true";
		stopUpdate=false;
		String name = textFieldName.getText();
		String username = textFieldUsername.getText();
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(confirmPasswordField.getPassword());
		String phoneNumber = textFieldPhoneNumber.getText();
		String email = textFieldEmail.getText();
		String address = textFieldAddress.getText();
		String text = "";
		
		if (name.isEmpty()) 
			text += "Please Input Your Name\n";
		if (username.isEmpty()) 
			text += "Please Input Your Username\n";
		if (password.isEmpty()) 
			text += "Please Input Your Password\n";
		if (confirmPassword.isEmpty()) 
			text += "Please Input Your Confirm Password\n";
		if (phoneNumber.isEmpty()) 
			text += "Please Input Your Phone Number\n";
		if (email.isEmpty()) 
			text += "Please Input Your Email\n";
		if (address.isEmpty()) 
			text += "Please Input Your Address\n";
		if (lblImage.getIcon() == null) 
			text += "Please Upload Your Profile Picture";
		
		if (!text.isEmpty())
        {
			JOptionPane.showMessageDialog(null, text);
			stopUpdate=true;
        }
        else if (!confirmPassword.equals(password))
        {
        	JOptionPane.showMessageDialog(null, "Password Doesn't Match");
			stopUpdate=true;
        }
		if(!stopUpdate) {
			client.Send(username);
			
			try {
				if(client.MessageReceive().equalsIgnoreCase("done")) {}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
			client.sendImage(byteArrayImage);
			
			try {
				if(client.MessageReceive().equalsIgnoreCase("done")) {}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			};
			client.Send(password);
			client.Send(name);
			client.Send(email);
			client.Send(address);
			client.Send(phoneNumber);

			
			result="true";
			try {
				result=client.MessageReceive();
			
			}catch(Exception e){
			e.printStackTrace();
			}
			if (result.equalsIgnoreCase("true")){
        	JOptionPane.showMessageDialog(null, "Username Is Already Exist");
        	client.Send("true");
        	}
			else if(result.equalsIgnoreCase("false"))
			{
				try 
				{
            	client.Send("false");
            	//String query = "INSERT INTO " + SqlConnection.TABLE_NAME + " (Name, Username, Password, Phonenumber, Email, Address) VALUES (? ,? ,? ,? ,?, ?)";
            	String query = "INSERT INTO Profile  (ID,Name, Username, Password, Phonenumber, Email, Address,Photo) VALUES (null,? ,? ,? ,? ,?, ?,?)";
            	Connection connectDB=SqlConnection.dbConnector();
            	PreparedStatement preparedStatement = connectDB.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, password);
				preparedStatement.setString(4, phoneNumber);
				preparedStatement.setString(5, email);
				preparedStatement.setString(6, address);
				preparedStatement.setBytes(7, byteArrayImage);
				preparedStatement.execute();
				preparedStatement.close();
				JOptionPane.showMessageDialog(null, "Data Saved Successfully");
				
				// Go to the next page
//				LoginPage.USERNAME = username;
				client.setName(name);
				client.setAddress(address);
				client.setPhonenumber(phoneNumber);
				client.setEmail(email);
				nextPage();
			} 
            catch (Exception e) {
				e.printStackTrace();
			}
         
		}
	}
	}
	public void uploadImage()
	{
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		
		if (result ==JFileChooser.APPROVE_OPTION) 
		{
			File file = fileChooser.getSelectedFile();
			String filePath = file.getAbsolutePath();
			
			try {
				File imageFile = new File(filePath);
				BufferedImage bufferedImage = ImageIO.read(imageFile);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
				byteArrayImage = byteArrayOutputStream.toByteArray();	
				Image image = Toolkit.getDefaultToolkit().createImage(byteArrayImage);
				Image scaledImage = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
	            lblImage.setIcon(new ImageIcon(scaledImage));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void nextPage()
	{
		this.dispose();
		client.Send("Finish");
		client.Send("2");
		LoginPage loginPage = new LoginPage(client);
		loginPage.setVisible(true);
	}
	
	public void goBack()
	{
		this.dispose();
		client.Send("false");
		Food food = new Food(client);
		food.frame.setVisible(true);
	}
}


