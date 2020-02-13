package finalDemo;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame{
	public static Image img;
	public static Scanner keyboard=new Scanner(System.in);
	private  String name,IPAddress;
	private  Client client;
	private  static boolean running;
	private int port;
	
	public static final int X_POS = 450;
	public static final int Y_POS = 60;
	
	public static void main (String[] args) throws IOException {	
		running=true;

		
		Client client=new Client("Anonymous","172.20.10.2",1234,"ClientDatabase");
		keyboard=new Scanner(System.in);
		String tempString;
		MainPage window = new MainPage(client);
		window.frame.setVisible(true);
		while(window.frame.isDisplayable()) {}
		
		Food food = new Food(client);
		food.frame.setVisible(true);
		
//		while(running) {
//			client.printFuncMessage();
//			tempString=keyboard.nextLine();
//			client.Send("finish");
//			client.Send(tempString);//Send What function you wan to server
//			switch(Integer.valueOf(tempString)) {
//			case(1):{
//				client.Chat();
//				break;
//			}
//			case(2):{//Login
//			try {
//				client.Login();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
//			}
//			case(3):{//Register
//				client.Register();
//				break;
//			}
//			
//			case(4):{
//				running=false;
//				break;
//			}
//			}
//			
//		}
	}
}

