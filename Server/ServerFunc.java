package serverDemo;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerFunc implements Runnable{

	protected Server server=null;
	protected Socket client=null;
	private Scanner clientInput=null;
	private PrintStream clientPS=null;
	protected DataOutputStream clientDataOutputStream=null;
	protected DataInputStream clientDataInputStream=null;
	protected int ID;
	protected String name;
	protected Scanner keyboard=new Scanner(System.in);
	private ExecutorService serverService=null;
	private boolean running;
	private int no_food;
	

	public ServerFunc(Server server_func,Socket client_func) {
		running=true;
		serverService=Executors.newFixedThreadPool(3);
		client=client_func;
		server=server_func;
		try {
			clientInput=new Scanner(client.getInputStream());
			clientPS=new PrintStream(client.getOutputStream());
			clientDataOutputStream=new DataOutputStream(client.getOutputStream());
			clientDataInputStream=new DataInputStream(client.getInputStream());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		name=clientInput.nextLine();
		ID=clientInput.nextInt();
		String temp_string=clientInput.nextLine();
		System.out.println(name+"("+ID+") connect to server successfully");
	}
	
	public ServerFunc() {}
	

	public void run(){
		try {
			InitialDB initializeDb=new InitialDB(client);
			initializeDb.run();	
			System.out.println("Finish Initialize");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		while(running) {
//			clientPS.println("Server:Welcome,how can I help you ?\nPress 1 for Chatting\nPress 2 to Login\n"
//					+ "Press 3 to Register \nPress 4 to Exit");
//			clientPS.flush();
			if(clientInput.nextLine().equalsIgnoreCase("finish")) {}
			System.out.println("Waiting");
			while(!clientInput.hasNextInt()) {}
			int func=clientInput.nextInt();
			clientInput.nextLine();//Remove next line \n	
			switch(func) {	
			case(1):{
				System.out.println("Chatting");
			    ChatServer frame = new ChatServer(clientPS,clientInput,name);
				frame.setVisible(true); 
				while(frame.isDisplayable()) {}
				break;
				}
			
			case(2):{
				System.out.println("Login");
				do {
					String name=clientInput.nextLine();
					if(name.equalsIgnoreCase("false")) {
						break;
					}
					UpdateDB findprofile=new UpdateDB();
					boolean found=false;
					try {
						found=findprofile.GetProfile(name);
						clientPS.println(String.valueOf(found));
						} catch (SQLException e) {
							e.printStackTrace();
							}
					if(found) {
						String password=clientInput.nextLine();
						try {
							found=findprofile.GetProfile(name,password);
							clientPS.println(String.valueOf(found));
							} catch (SQLException e) {
								e.printStackTrace();
						}
						if(found) {
							SendProfile sendProfile=new SendProfile(clientPS,clientDataOutputStream,name,password,client);
							sendProfile.run();
							break;
							}
				}
				}while(true);
				break;
			}
			case(3):{
				String result;
				do {
				System.out.println("Register Profile");
				String username=clientInput.nextLine();
				if(username.equals("false")) {
					break;
				}
				clientPS.println("done");
				
				byte []image=getImage();
				
				String password=clientInput.nextLine();
				String name=clientInput.nextLine();
				String email=clientInput.nextLine();
				String address=clientInput.nextLine();
				String phonenumber=clientInput.nextLine();
				UpdateDB findProfile=new UpdateDB();
				boolean found=true;
				try {
					 found=findProfile.GetProfile(username);
					 clientPS.println(String.valueOf(found));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(!found) {
					findProfile.Update(name,username,password,phonenumber,email,address);
					try {
						findProfile.updateImage(username,image);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setName(name);
				}
				result=clientInput.nextLine();
				}while(result.equalsIgnoreCase("true"));	
				break;
			}
			case(4):{
				running=false;
				break;
			}
			case(5):{
				System.out.println("Update Data");	

				String username=clientInput.nextLine();
				System.out.println(username);
				clientPS.println("done");
				byte []image=getImage();

				String password=clientInput.nextLine();
				String name=clientInput.nextLine();
				String email=clientInput.nextLine();
				String address=clientInput.nextLine();
				String phonenumber=clientInput.nextLine();
				UpdateDB findProfile=new UpdateDB();
				
				try {
					findProfile.UpdateData(name,username,password,phonenumber,email,address);
					findProfile.updateImage(username,image);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setName(name);
				break;
				}
			case(6):{
				boolean skip=false;
				String tempstring;
				do {
					tempstring=clientInput.nextLine();
					if(tempstring.equalsIgnoreCase("false")) {
						skip=true;
						break;
					}
					System.out.print("Food:"+tempstring+"     ");
						System.out.println("Price:"+clientInput.nextLine());
						System.out.println("Food Total:"+clientInput.nextLine());
						
					}while(!clientInput.nextLine().equalsIgnoreCase("end"));
				if(!skip) {
					System.out.println("Total Price:"+clientInput.nextLine());
					System.out.println("");
					System.out.println("Personal Information:");
					System.out.println("Name:"+clientInput.nextLine());
					System.out.println("PhoneNumber:"+clientInput.nextLine());
					System.out.println("Email:"+clientInput.nextLine());
					System.out.println("Address:"+clientInput.nextLine());
				}
					
				}
				break;
			}
			}
			}
		
	
			
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public void GetImageFromClient() throws IOException {
		byte[] sizeArray=new byte[4];
		clientDataInputStream.read(sizeArray);
		int size=ByteBuffer.wrap(sizeArray).asIntBuffer().get();
		byte[] image=new byte[size];
		clientDataInputStream.readFully(image);
		
	}
	public void GetImageFromDB() {
		System.out.println("here in server");
		UpdateDB temp=new UpdateDB();
		byte [] imageArray=temp.GetImage("Burger");
		try {
			clientDataOutputStream.writeInt(imageArray.length);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			baos.write(imageArray,0,imageArray.length);
			clientDataOutputStream.write(imageArray,0,imageArray.length);
			System.out.println("Sent");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setName(String name_func) {
		name=name_func;
	}
	
	public byte[] getImage() {
		byte[] immArray=null;
		try {
			byte []sizeByteArr=new byte[4];
			clientDataInputStream.read(sizeByteArr);
			int size=ByteBuffer.wrap(sizeByteArr).asIntBuffer().get();
			System.out.println("Size receive:"+size);
			 immArray=new byte[size];
			clientDataInputStream.readFully(immArray);
		}catch(Exception e) {
			e.printStackTrace();
		}
		clientPS.println("done");
		return immArray;
	}
}
