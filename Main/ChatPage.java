package finalDemo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatPage extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldClient;
	private Client client=null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChatPage frame = new ChatPage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
	
	private JTextArea textArea;
	private JButton btnClientSend,btnReceive;
	private String text;
	private boolean dispose=false;

	/**
	 * Create the frame.
	 */
	public ChatPage(Client client_func) 
	{
		client=client_func;
		text = "";
		initialize();
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(Main.X_POS, Main.Y_POS, 350, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Chat Area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 316, 383);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		// Client
		textFieldClient = new JTextField();
		textFieldClient.setBounds(10, 428, 205, 20);
		contentPane.add(textFieldClient);
		textFieldClient.setColumns(10);
		
		btnClientSend = new JButton("Send");
		btnReceive=new JButton("Receive");

		btnClientSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				sendMessage();
				btnReceive.doClick();
			}
		});
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					if(!dispose) {
						receiveMessage();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
			}
		});
		btnClientSend.setBounds(225, 427, 89, 23);
		contentPane.add(btnClientSend);
		
	}
	
	public void receiveMessage() throws IOException {
		String message="";
		message = client.MessageReceive();
		if(message.equalsIgnoreCase("Exit")) {
			dispose=true;
			this.dispose();
			Food food=new Food(client);
			food.frame.setVisible(true);
			}
		else {
			text += "Server: " + message + "\n\n";
			textArea.setText(text);
			textFieldClient.setEnabled(true);
			btnClientSend.setEnabled(true);
		}
	}
	
	public void sendMessage()
	{
		String message = "";
		try {
			message = textFieldClient.getText();
			if(message.equalsIgnoreCase("exit")) {
				client.Send(message);
				dispose=true;
				this.dispose();
				Food food=new Food(client);
				food.frame.setVisible(true);
			}
			else {
				textFieldClient.setText("");
				textFieldClient.setEnabled(false);
				btnClientSend.setEnabled(false);
				text += client.getName() + ": " + message + "\n\n";
				textArea.setText(text);
				client.Send(message);	
				}
			}catch(Exception e) {
				e.printStackTrace();
		}
	}
	
//	}
}

