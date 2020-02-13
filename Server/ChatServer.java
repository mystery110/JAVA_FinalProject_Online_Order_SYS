package serverDemo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Scanner;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatServer extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldServer;

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
	private JButton  btnServerSend;
	private String text;
	private PrintStream clientPS;
	private Scanner clientInput;
	private String name;
	private boolean dispose=false;

	/**
	 * Create the frame.
	 */
	public ChatServer(PrintStream clientPS_func,Scanner clientInput_func,String name_func) 
	{
		clientInput=clientInput_func;
		clientPS=clientPS_func;
		name=name_func;
		text="";
		initialize();
	}
	
	public void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 600);
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
		
		// Server
		textFieldServer = new JTextField();
		textFieldServer.setBounds(10, 477, 205, 20);
		contentPane.add(textFieldServer);
		textFieldServer.setColumns(10);
		textFieldServer.setEnabled(false);
		
		String message = "";
		message=clientInput.nextLine();
		text += name+": " + message + "\n\n";
		textArea.setText(text);

		btnServerSend = new JButton("Send");
		textFieldServer.setEnabled(true);
		btnServerSend.setEnabled(true);
		btnServerSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				sendMessage();
				try {
					if(!dispose) {
						receiveMessage();
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnServerSend.setBounds(225, 476, 89, 23);
		contentPane.add(btnServerSend);
	}
	
	public void receiveMessage() throws InterruptedException {
		String message="";
		message=clientInput.nextLine();
		if(message.equalsIgnoreCase("exit")){//Client exit
//			clientPS.println("Server:Bye Bye. See you next time!");
//			clientPS.flush();
//			message=clientInput.nextLine();
//			text += name+": " + message + "\n\n";
//			textArea.setText(text);
			dispose=true;
			this.dispose();
		}
		else {
			textArea.setText(text);
			text += name+": " + message + "\n\n";
			textArea.setText(text);
			textFieldServer.setEnabled(true);
			btnServerSend.setEnabled(true);
		}
	}
	
	public void sendMessage()
	{
		String message = "";
		try {
			message=textFieldServer.getText();
			if(!message.equalsIgnoreCase("exit")) {
				textFieldServer.setText("");
				textFieldServer.setEnabled(false);
				btnServerSend.setEnabled(false);
				clientPS.println(message);
				text += "Server: " + message + "\n\n";
				textArea.setText(text);

			}
			else {//server exit
				clientPS.println(message);
//				clientPS.println("server"+":Bye Bye.Thank you for your help.");
//				clientPS.flush();
//				message=clientInput.nextLine();
//				text += name+": " + message + "\n\n";
//				textArea.setText(text);
//				textFieldServer.setText("");
//				Thread.sleep(1000);
				this.dispose();
			}
			}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}

