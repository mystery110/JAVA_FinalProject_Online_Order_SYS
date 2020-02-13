package finalDemo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class ProfilePage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProfilePage frame = new ProfilePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	// Constants
	public static final int FIELD_HEIGHT = 25;
	public static final int FIELD_WIDHT = 250;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_WIDHT = 250;
		
	// Components
	private JTextField textFieldName, textFieldUsername, textFieldPassword, textFieldPhoneNumber, textFieldEmail, textFieldAddress;
	private JLabel lblImage, lblName, lblUsername, lblPassword, lblPhoneNumber, lblEmail, lblAddress;
	private JButton  btnUpload, btnSave, btnBack, btnCancel;
	private SqlConnection sqlConnection;
	private Connection connection = null;
	private byte[] byteArrayImage = null;
	private JButton btnEdit;
	private Client client;

	/**
	 * Create the frame.
	 */
	public ProfilePage(Client client_func) 
	{
		client=client_func;
		initialization();
		sqlConnection = new SqlConnection();
		try {
			showData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readMode();
	}

	public void initialization()
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
		lblImage.setBounds(108, 26, 120, 120);
		contentPane.add(lblImage);
		
		// Upload Button
		btnUpload = new JButton("Change");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				uploadImage();
			}
		});
		btnUpload.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpload.setBounds(124, 154, 89, 30);
		contentPane.add(btnUpload);
		
		// Name
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(43, 184, 85, 34);
		contentPane.add(lblName);
			
		textFieldName = new JTextField();
		textFieldName.setBounds(43, 214, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
	
		// Username
		lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(43, 231, 85, 34);
		contentPane.add(lblUsername);
			
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(43, 261, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
				
		// Password
		lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(43, 280, 85, 34);
		contentPane.add(lblPassword);
				
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(43, 310, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		
		// Phone Number
		lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhoneNumber.setBounds(43, 330, 120, 34);
		contentPane.add(lblPhoneNumber);
			
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(43, 360, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		// Email
		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(43, 375, 120, 34);
		contentPane.add(lblEmail);
			
		textFieldEmail= new JTextField();
		textFieldEmail.setBounds(43, 405, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		// Address
		lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(43, 422, 120, 34);
		contentPane.add(lblAddress);
			
		textFieldAddress= new JTextField();
		textFieldAddress.setBounds(43, 452, FIELD_WIDHT, FIELD_HEIGHT);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		// Edit Button
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				editMode();
			}
		});
		btnEdit.setBounds(238, 68, 74, 34);
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnEdit);
		
		// Save Button
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				readMode();
				try {
					updateData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSave.setBounds(105, 500, 89, 30);
		contentPane.add(btnSave);
		
		// Cancel Button
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				readMode();
				try {
					showData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancel.setBounds(204, 500, 89, 30);
		contentPane.add(btnCancel);
		
		// Back Button
		btnBack = new JButton("Back");
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
	
	public void editMode()
	{
		textFieldName.setEnabled(true);
		textFieldPassword.setEnabled(true);
		textFieldPhoneNumber.setEnabled(true);
		textFieldEmail.setEnabled(true);
		textFieldAddress.setEnabled(true);
		btnUpload.setVisible(true);
		btnSave.setVisible(true);
		btnCancel.setVisible(true);
		btnEdit.setEnabled(false);
	}
	
	public void readMode()
	{
		textFieldName.setEnabled(false);
		textFieldUsername.setEnabled(false);
		textFieldPassword.setEnabled(false);
		textFieldPhoneNumber.setEnabled(false);
		textFieldEmail.setEnabled(false);
		textFieldAddress.setEnabled(false);
		btnUpload.setVisible(false);
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		btnEdit.setEnabled(true);
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
	
	public void showData() throws SQLException
	{
		connection=SqlConnection.dbConnector();
		String[] data = sqlConnection.getData(LoginPage.USERNAME, connection);
		byteArrayImage = sqlConnection.getImage(LoginPage.USERNAME, connection);
		connection.close();
		
		textFieldName.setText(data[0]);
		textFieldUsername.setText(data[1]);
		textFieldPassword.setText(data[2]);
		textFieldPhoneNumber.setText(data[3]);
		textFieldEmail.setText(data[4]);
		textFieldAddress.setText(data[5]);
		Image image = Toolkit.getDefaultToolkit().createImage(byteArrayImage);
        Image scaledImage = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        lblImage.setIcon(new ImageIcon(scaledImage));
	}
	
	public void updateData() throws SQLException
	{
		client.Send("finish");
		client.Send("5");
		String name = textFieldName.getText();
		String username=textFieldUsername.getText();
		String password = textFieldPassword.getText();
		String phoneNumber = textFieldPhoneNumber.getText();
		String email = textFieldEmail.getText();
		String address = textFieldAddress.getText();
		//String query = "UPDATE " + SqlConnection.TABLE_NAME + " SET";
		String query = "UPDATE  Profile SET";

		client.Send(username);
		
		try {
			if(client.MessageReceive().equalsIgnoreCase("done")) {}
		} catch (IOException e1) {
			e1.printStackTrace();
		};
		client.sendImage(byteArrayImage);
		try {
			if(client.MessageReceive().equalsIgnoreCase("done")) {}
		} catch (IOException e1) {
			e1.printStackTrace();
		};

		client.Send(password);
		client.Send(name);
		client.Send(email);
		client.Send(address);
		client.Send(phoneNumber);

		
		
		if (!name.isEmpty()) 
			query += " Name = '" + name + "',";
		if (!password.isEmpty()) 
			query += " Password = '" + password + "',";
		if (!phoneNumber.isEmpty()) 
			query += " Phonenumber = '" + phoneNumber + "',";
		if (!email.isEmpty()) 
			query += " Email = '" + email + "',";
		if (!address.isEmpty()) 
			query += " Address = '" + address + "',";
//		
		connection=SqlConnection.dbConnector();
		sqlConnection.updateData(query, byteArrayImage, connection);
		connection.close();
	}
	
	public void goBack()
	{
		this.dispose();
		Food food = new Food(client);
		food.frame.setVisible(true);
	}
}
