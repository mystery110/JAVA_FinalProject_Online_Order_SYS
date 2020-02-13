package finalDemo;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class Food {

	public JFrame frame;
	public Image image;
	public int counter = 0;
	public int Maximum = 20;
	public String name = "",description = "";
	public int cost = 0;
	public static int[] buyingArray = new int[20];
	public static ArrayList<String> nameList = new ArrayList<String>();
	public static ArrayList<Integer> costList = new ArrayList<Integer>();
	public static ArrayList<String> descriptionList = new ArrayList<String>();
	public static ArrayList<Image> imageList = new ArrayList<Image>();
	private Client client;

	/////
	
	JLabel labelCounter;
	JLabel lblPicture;
	JLabel lblName;
	JLabel lblCost;
	JLabel lblDescription;
	JButton btnPurchase;
	JButton btnClear;
	JLabel lblPicture2;
	JLabel lblName2;
	JLabel lblCost2;
	JLabel lblDescription2;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton btnPurchase2;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Food window = new Food();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	private SqlConnection sqlConnection;
	private Connection connection = null;
	private static boolean set = false;

	/**
	 * Create the application.
	 */
	public Food(Client client_func) 
	{
		client=client_func;
		sqlConnection = new SqlConnection();
		connection = sqlConnection.dbConnector();	
		if (!set) {
			showFood();
			set = true;
		}
		initialize();
		//test();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{	
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.info);
		frame.setBackground(new Color(255, 255, 255));
		frame.setForeground(new Color(220, 220, 220));
		frame.getContentPane().setForeground(new Color(220, 220, 220));
		frame.setBounds(Main.X_POS, Main.Y_POS, 350, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//initialize buyingArray to zero
		for(int i=0;i<buyingArray.length;i++) {
			buyingArray[i]= 0; 
		}
		//initialize totalCost
		//calculateTotalCost();
		
		// Initialize Components
		lblPicture = new JLabel();
		labelCounter = new JLabel("New label");
		lblPicture = new JLabel("Picture");
		lblName = new JLabel("");
		lblCost = new JLabel("");
		lblDescription = new JLabel("");
		btnPurchase = new JButton("Purchase");
		btnClear = new JButton("Clear");
		lblPicture2 = new JLabel("");
		lblName2 = new JLabel("");
		lblCost2 = new JLabel("");
		lblDescription2 = new JLabel("");
		panel = new JPanel();
		panel_1 = new JPanel();
		panel_2 = new JPanel();
		btnPurchase2 = new JButton("Purchase");
		
		
		lblPicture.setBounds(38, 10, 259, 154);
		//image = new ImageIcon(this.getClass().getResource("/burger.jpg")).getImage();		
		frame.getContentPane().add(lblPicture);
		lblName.setForeground(Color.GRAY);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));			
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(38, 174, 197, 20);		
		frame.getContentPane().add(lblName);
		lblCost.setForeground(Color.RED);
		lblCost.setFont(new Font("Tahoma", Font.BOLD, 20));			
		lblCost.setHorizontalAlignment(SwingConstants.CENTER);
		lblCost.setBounds(212, 174, 77, 20);		
		frame.getContentPane().add(lblCost);
		lblDescription.setForeground(new Color(128, 128, 128));
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));				
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setBounds(38, 204, 259, 20);		
		frame.getContentPane().add(lblDescription);			
		labelCounter.setBounds(280, 540, 46, 13);
		labelCounter.setText("");
		frame.getContentPane().add(labelCounter);
					
		lblPicture2.setBounds(38, 234, 259, 154);		
		frame.getContentPane().add(lblPicture2);
		lblName2.setHorizontalAlignment(SwingConstants.CENTER);
		lblName2.setForeground(Color.GRAY);
		lblName2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName2.setBounds(38, 397, 197, 20);		
		frame.getContentPane().add(lblName2);
		lblCost2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCost2.setForeground(Color.RED);
		lblCost2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCost2.setBounds(212, 397, 77, 20);		
		frame.getContentPane().add(lblCost2);
		lblDescription2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription2.setForeground(new Color(128, 128, 128));
		lblDescription2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDescription2.setBounds(38, 427, 259, 20);		
		frame.getContentPane().add(lblDescription2);

		panel.setBackground(new Color(255, 182, 193));
		panel.setBounds(0, 0, 336, 229);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		btnPurchase.setBounds(251, 204, 75, 21);
		panel.add(btnPurchase);
		btnPurchase.setFont(new Font("Tahoma", Font.BOLD, 8));
			
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyingArray[counter]++;
				//calculateTotalCost();
			}
		});
				panel_1.setBackground(new Color(255, 218, 185));
				panel_1.setBounds(0, 228, 336, 229);
				frame.getContentPane().add(panel_1);
				panel_1.setLayout(null);
				btnPurchase2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buyingArray[counter+1]++;
						//calculateTotalCost();
					}
				});
				btnPurchase2.setFont(new Font("Tahoma", Font.BOLD, 8));
				btnPurchase2.setBounds(251, 198, 75, 21);
				
				panel_1.add(btnPurchase2);
				panel_2.setBackground(SystemColor.info);
				panel_2.setBounds(0, 459, 336, 104);
				
				frame.getContentPane().add(panel_2);
				panel_2.setLayout(null);
				btnClear.setBounds(122, 26, 85, 21);
				panel_2.add(btnClear);
				
				JButton btnPrevious = new JButton("Previous");
				btnPrevious.setBounds(27, 26, 85, 21);
				panel_2.add(btnPrevious);
				
				
				
				JButton btnNext = new JButton("Next");
				btnNext.setBounds(217, 26, 85, 21);
				panel_2.add(btnNext);
				
				JButton btnTrolley = new JButton("Trolley");
				btnTrolley.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(client.getName().equals("Anonymous")) {
							JOptionPane.showMessageDialog(null, "Please login or register a new account ");
							}
						else {
						frame.dispose();
						client.Send("finish");
						client.Send("6");
						Trolley trolley = new Trolley(client);
						trolley.setVisible(true);
						}
					}
				});
				btnTrolley.setBounds(122, 62, 85, 21);
				panel_2.add(btnTrolley);
				
				JButton btnProfile = new JButton("Profile");
				btnProfile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						if(client.getName().equals("Anonymous")) {
							EmptyProfilePage epp=new EmptyProfilePage(client);
							epp.setVisible(true);
						}
						else {
							ProfilePage profilePage = new ProfilePage(client);
							profilePage.setVisible(true);
						}

					}
				});
				btnProfile.setBounds(217, 62, 85, 21);
				panel_2.add(btnProfile);
				
				JButton btnChat = new JButton("Chat");
				btnChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						client.Send("finish");
						client.Send("1");
						ChatPage chatPage = new ChatPage(client);
						chatPage.setVisible(true);
					}
				});
				btnChat.setBounds(27, 62, 85, 21);
				panel_2.add(btnChat);
				btnNext.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(counter < imageList.size() - 2) {
							counter = counter + 2;
							//Show counter number for checking
							labelCounter.setText("" + counter);
							//Show image to labelimage							
							lblPicture.setIcon(new ImageIcon(ScaledImage(imageList.get(counter), lblPicture.getWidth(), lblPicture.getHeight())));						
							lblPicture2.setIcon(new ImageIcon(ScaledImage(imageList.get(counter+1), lblPicture2.getWidth(), lblPicture2.getHeight())));
							//Show name to labelname							
							lblName.setText(nameList.get(counter));						
							lblName2.setText(nameList.get(counter + 1));
							//Show cost to labelcost							
							lblCost.setText("$ " + costList.get(counter));							
							lblCost2.setText("$ " + costList.get(counter + 1));
							//Show description to labeldescription							
							lblDescription.setText(descriptionList.get(counter));							
							lblDescription2.setText(descriptionList.get(counter + 1));
							
							//Show total cost
							//calculateTotalCost();
						}
					}
				});
				btnPrevious.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(counter >= 2) {
							counter = counter - 2;
							//Show counter number for checking
							labelCounter.setText("" + counter);
							//Show image to labelimage							
							lblPicture.setIcon(new ImageIcon(ScaledImage(imageList.get(counter), lblPicture.getWidth(), lblPicture.getHeight())));							
							lblPicture2.setIcon(new ImageIcon(ScaledImage(imageList.get(counter+1), lblPicture2.getWidth(), lblPicture2.getHeight())));
							//Show name to labelname							
							lblName.setText(nameList.get(counter));							
							lblName2.setText(nameList.get(counter + 1));
							//Show cost to labelcost
							cost = costList.get(counter);
							lblCost.setText("$ " + cost);
							cost = costList.get(counter + 1);
							lblCost2.setText("$ " + cost);
							//Show description to labeldescription
							description = descriptionList.get(counter);
							lblDescription.setText(description);
							description = descriptionList.get(counter + 1);
							lblDescription2.setText(description);
							
							//Show total cost
							//calculateTotalCost();
						}
					}
				});
				btnClear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for(int i=0;i<buyingArray.length;i++) {
							buyingArray[i]= 0;					
						}
						//calculateTotalCost();
					}
				});
		/////////////////////////////////////////////////////////////////
		//Initialize all the labels
		//Show image to labelpicture
		lblPicture.setIcon(new ImageIcon(ScaledImage(imageList.get(0), lblPicture.getWidth(), lblPicture.getHeight())));
		lblPicture2.setIcon(new ImageIcon(ScaledImage(imageList.get(1), lblPicture2.getWidth(), lblPicture2.getHeight())));		
		//Show name to labelname				
		lblName.setText(nameList.get(0));
		lblName2.setText(nameList.get(1));
		//Show cost to labelcost				
		lblCost.setText("$ " + costList.get(0));
		lblCost2.setText("$ " + costList.get(1));
		//Show description to labeldescription				
		lblDescription.setText(descriptionList.get(0));
		lblDescription2.setText(descriptionList.get(1));
		//Show total cost
		//calculateTotalCost();
	}
	
	private Image ScaledImage(Image img,int width,int height) {
		BufferedImage resizeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = resizeImage.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.drawImage(img, 0, 0,width, height, null);
		return resizeImage;
		
	}
	
	
	public void showFood()
	{
//		nameList.clear();
//		imageList.clear();
//		costList.clear();
//		descriptionList.clear();
		
		try {
			String query = "SELECT * FROM Food";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int count = 0;
			while (resultSet.next())
			{
				nameList.add(resultSet.getString("Name"));
				imageList.add(convertToImage(resultSet.getBytes("Photo")));
				costList.add(Integer.parseInt(resultSet.getString("Price")));
				descriptionList.add(resultSet.getString("Description"));
				count++;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Image convertToImage(byte[] imageByte)
	{
		Image image = Toolkit.getDefaultToolkit().createImage(imageByte);
		return image;
	}
	
	public void test()
	{
		lblPicture.setIcon(new ImageIcon(ScaledImage(imageList.get(0), lblPicture.getWidth(), lblPicture.getHeight())));
		lblPicture2.setIcon(new ImageIcon(ScaledImage(imageList.get(1), lblPicture2.getWidth(), lblPicture2.getHeight())));		
		//Show name to labelname				
		lblName.setText(nameList.get(0));
		lblName2.setText(nameList.get(1));
		//Show cost to labelcost				
		lblCost.setText("$ " + costList.get(0));
		lblCost2.setText("$ " + costList.get(1));
		//Show description to labeldescription				
		lblDescription.setText(descriptionList.get(0));
		lblDescription2.setText(descriptionList.get(1));
	}
}
