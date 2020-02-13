package finalDemo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainPage window = new MainPage();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	// Constants
	public static final int WIDTH = 350;
	public static final int HEIGHT = 600;
	
	// Components
	private JButton btnContinue;
	private JLabel lblImage;
	private Client client;

	/**
	 * Create the application.
	 */
	public MainPage(Client client_func) 
	{
		initialize();
		client=client_func;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(Main.X_POS, Main.Y_POS, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Welcome Label
		JLabel lblWelcome = new JLabel("Welcome :)");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblWelcome.setBounds(43, 20, 250, 40);
		lblWelcome.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblWelcome);
		
		// Image Logo Label
		lblImage = new JLabel();
		Border borderImage = BorderFactory.createLineBorder(Color.BLACK);
		lblImage.setBorder(borderImage);
		lblImage.setHorizontalAlignment(JLabel.CENTER);
		lblImage.setBounds(10, 70, 316, 334);
		Image imgLogin = new ImageIcon(this.getClass().getResource("/Logo.jpg")).getImage();
		Image scaledImage = imgLogin.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
		lblImage.setIcon(new ImageIcon(scaledImage));
		frame.getContentPane().add(lblImage);
		
		// Continue Button
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				goToFoodPage();
			}
		});
		btnContinue.setBounds(73, 442, 191, 40);
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(btnContinue);
	}
	
	/*---> Functions <---*/
	
	public void goToFoodPage()
	{
		frame.dispose();

	}

}
