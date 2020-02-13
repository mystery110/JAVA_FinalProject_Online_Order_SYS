package finalDemo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Label;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Trolley extends JFrame {

	public int Total = 0;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	public ArrayList<JLabel> foodNameList = new ArrayList<JLabel>(20);
	public ArrayList<JLabel> priceList = new ArrayList<JLabel>(20);
	public ArrayList<JLabel> totalPriceList = new ArrayList<JLabel>(20);
	private JButton btnConfirm;
	private Client client;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Trolley frame = new Trolley();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Trolley(Client client_func) {
		client=client_func;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Main.X_POS, Main.Y_POS, 350, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int in=0;
				for(int i=0;i<Food.buyingArray.length;i++) 
				{
					if(Food.buyingArray[i] > 0) 
					{
						in++;
					}
				}
				if(in==0) 
				{
					client.Send("false");
				}
				else 
				{
					for(int i=0;i<Food.buyingArray.length;i++) 
					{
						if(Food.buyingArray[i] > 0) 
						{
							
							client.Send(Food.nameList.get(i));
							client.Send("$" + Food.costList.get(i) + " * " + Food.buyingArray[i]);
							client.Send(""+Food.costList.get(i)*Food.buyingArray[i]);
							Total += Food.costList.get(i)*Food.buyingArray[i];
						}
					}
					JOptionPane.showMessageDialog(null, "Purchase Successfully");
					client.Send("end");
					client.Send(""+Total);
					client.Send(client.getName());
					client.Send(client.getPhonenumber());
					client.Send(client.getAddress());
					client.Send(client.getEmail());
				}
				goToFoodPage();
			}
		});
		
		if(client.getName().equals("Anonymous")) {
			btnConfirm.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Please login or register a new account ");
			this.dispose();
			goToFoodPage();
		}
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.Send("False");
				goToFoodPage();
			}
		});
		btnCancel.setBackground(new Color(255, 192, 203));
		btnCancel.setForeground(Color.GRAY);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnCancel.setBounds(0, 505, 169, 58);
		contentPane.add(btnCancel);
		
		btnConfirm.setForeground(new Color(128, 128, 128));
		btnConfirm.setBackground(new Color(210, 180, 140));
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnConfirm.setBounds(169, 505, 175, 58);
		contentPane.add(btnConfirm);
		panel.setBackground(new Color(255, 248, 220));
		panel.setBounds(0, 0, 336, 84);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 10, 213, 33);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(10, 46, 213, 33);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(192, 192, 192));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(272, 26, 64, 48);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setForeground(new Color(220, 20, 60));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 19));
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 248, 220));
		panel_1.setBounds(0, 84, 346, 84);
		contentPane.add(panel_1);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(10, 10, 213, 33);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		label_1.setBounds(10, 38, 213, 33);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setForeground(new Color(220, 20, 60));
		label_2.setFont(new Font("Tahoma", Font.BOLD, 19));
		label_2.setBounds(272, 23, 64, 48);
		panel_1.add(label_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(255, 248, 220));
		panel_2.setBounds(0, 170, 346, 84);
		contentPane.add(panel_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label_3.setBounds(10, 10, 213, 33);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("");
		label_4.setForeground(Color.LIGHT_GRAY);
		label_4.setFont(new Font("Tahoma", Font.ITALIC, 13));
		label_4.setBounds(10, 38, 213, 33);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setForeground(new Color(220, 20, 60));
		label_5.setFont(new Font("Tahoma", Font.BOLD, 19));
		label_5.setBounds(272, 23, 64, 48);
		panel_2.add(label_5);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(255, 248, 220));
		panel_3.setBounds(0, 256, 346, 84);
		contentPane.add(panel_3);
		
		JLabel label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label_6.setBounds(10, 10, 213, 33);
		panel_3.add(label_6);
		
		JLabel label_7 = new JLabel("");
		label_7.setForeground(Color.LIGHT_GRAY);
		label_7.setFont(new Font("Tahoma", Font.ITALIC, 13));
		label_7.setBounds(10, 38, 213, 33);
		panel_3.add(label_7);
		
		JLabel label_8 = new JLabel("");
		label_8.setForeground(new Color(220, 20, 60));
		label_8.setFont(new Font("Tahoma", Font.BOLD, 19));
		label_8.setBounds(272, 23, 64, 48);
		panel_3.add(label_8);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(255, 248, 220));
		panel_4.setBounds(0, 341, 346, 84);
		contentPane.add(panel_4);
		
		JLabel label_9 = new JLabel("");
		label_9.setHorizontalAlignment(SwingConstants.LEFT);
		label_9.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label_9.setBounds(10, 10, 213, 33);
		panel_4.add(label_9);
		
		JLabel label_10 = new JLabel("");
		label_10.setForeground(Color.LIGHT_GRAY);
		label_10.setFont(new Font("Tahoma", Font.ITALIC, 13));
		label_10.setBounds(10, 38, 213, 33);
		panel_4.add(label_10);
		
		JLabel label_11 = new JLabel("");
		label_11.setForeground(new Color(220, 20, 60));
		label_11.setFont(new Font("Tahoma", Font.BOLD, 19));
		label_11.setBounds(272, 23, 64, 48);
		panel_4.add(label_11);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(255, 248, 220));
		panel_5.setBounds(0, 424, 346, 84);
		contentPane.add(panel_5);
		
		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setForeground(new Color(220, 20, 60));
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblTotal.setBounds(10, 23, 163, 48);
		panel_5.add(lblTotal);
		
		JLabel label_12 = new JLabel("");
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setForeground(new Color(220, 20, 60));
		label_12.setFont(new Font("Tahoma", Font.BOLD, 19));
		label_12.setBounds(272, 23, 64, 48);
		panel_5.add(label_12);
		
		////////////////////////
		label_12.setText("$ " + 0);
		
		foodNameList.add(lblNewLabel);
		foodNameList.add(label);
		foodNameList.add(label_3);
		foodNameList.add(label_6);
		foodNameList.add(label_9);
		priceList.add(lblNewLabel_1);
		priceList.add(label_1);
		priceList.add(label_4);
		priceList.add(label_7);
		priceList.add(label_10);
		totalPriceList.add(lblNewLabel_2);
		totalPriceList.add(label_2);
		totalPriceList.add(label_5);
		totalPriceList.add(label_8);
		totalPriceList.add(label_10);
		
		
		
		
		for(int i=0;i<Food.buyingArray.length;i++) {
			if(Food.buyingArray[i] > 0) {
				foodNameList.get(i).setText(Food.nameList.get(i));
				priceList.get(i).setText("$" + Food.costList.get(i) + " * " + Food.buyingArray[i]);			
				totalPriceList.get(i).setText("$ " + Food.costList.get(i)*Food.buyingArray[i]);
				Total += Food.costList.get(i)*Food.buyingArray[i];
				
			}
		}
		label_12.setText("$ " + Total);

		
	}

	public void goToFoodPage()
	{
		this.dispose();
		Food food = new Food(client);
		food.frame.setVisible(true);
	}
}
