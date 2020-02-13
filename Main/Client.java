package finalDemo;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.xml.sax.helpers.NamespaceSupport;

public class Client {
	private String name,IPAddress;
	//private Scanner clientScanner=null;
	private BufferedReader clientScanner=null;
	private Socket clientSocket=null;
	private PrintStream clientPS=null;
	private DataInputStream clientDataInputStream=null;
	private DataOutputStream clientDataOutputStream=null;
	private int port,ID;
	private ByteArrayOutputStream byteOutputStream;
	private String phonenumber=null,email=null,address=null;
	
	
	RegisterPage Register;
	
	public Client(String name_func,String IPAddress_func ,int port_func,String nameOfDatabase_func){
		name=name_func;
		IPAddress=IPAddress_func;
		port=port_func;
		
		try {
			clientSocket=new Socket(IPAddress,port);
			System.out.println(name+" connect to server successfully!");
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(name+"couldn't connect to the server!");
			System.exit(0);
		}
		
		try {
			//clientScanner=new Scanner(clientSocket.getInputStream());
			clientScanner=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientPS=new PrintStream(clientSocket.getOutputStream());
			clientDataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
			clientDataInputStream=new DataInputStream(clientSocket.getInputStream());
			clientPS.println(name);//Send name to server
			clientPS.println(ID);//Send ID
			SqlConnection.CreateTable();
			InitializedDB();	
		}
		
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	public Client() {
		this("Anonymous","140.116.103.27",1234,"ClientDatabase");
	}
	
	public void Send(String message_func) {
		clientPS.println(message_func);
		clientPS.flush();
	}
	
	public String MessageReceive() throws IOException {
		return String.valueOf(clientScanner.readLine());
	}
	
	public String getName() {
		return name;
	}
	
	public void setPhonenumber(String phonenumber_func) {
		phonenumber=phonenumber_func;
	}
	
	public void setName(String name_func) {
		name=name_func;
	}
	public void setEmail(String email_func) {
		email=email_func;
	}
	
	public void setAddress(String address_func) {
		address=address_func;
	}
	
	public String getAddress() {
		return address;
	}
	

	public String getEmail() {
		return email;
	}
	

	public String getPhonenumber() {
		return phonenumber;
	}
	
	public void Chat() {
		ChatPage frame = new ChatPage(this);
		frame.setVisible(true);
		while(frame.isDisplayable()) {};
	}
	
	public void printFuncMessage() throws IOException {
		System.out.println(MessageReceive());//Receive welcome message
		System.out.println(MessageReceive());//Receive Func
		System.out.println(MessageReceive());//Receive Func
		System.out.println(MessageReceive());//Receive Func
		System.out.println(MessageReceive());//Receive Func

	}
	
	public int GetID() {
		return ID;
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
		System.out.println("Read");
		return immArray;
	}
	
	public void GetALlImage() {}
	
	public void Login() throws SQLException, IOException{
		LoginPage frame = new LoginPage(this);
		frame.setVisible(true);	
		while(frame.isDisplayable()) {};
	}
	
	public void Register() {
		Register = new RegisterPage(this);
		Register.setVisible(true);
		while(Register.isDisplayable()) {}
	}

	public void InitializedDB() throws SQLException, IOException {
		String temp_string=MessageReceive();
		while(temp_string.equalsIgnoreCase("true")) {
			int id=Integer.valueOf(MessageReceive());
			System.out.println("id:"+id);
			Send("Finish");
			byte [] image=getImage();
			String name=MessageReceive();
			System.out.println("name:"+name);
			int price=Integer.valueOf(MessageReceive());
			System.out.println("Price:"+price);
			String description=MessageReceive();
			System.out.println("Description"+description);
			Connection connectDB=SqlConnection.dbConnector();
			String query="INSERT INTO Food (ID,Photo,Name,Price,Description) VALUES ("
					+id+",?,'"+name+"',"+price+",'"+description+"')";
			PreparedStatement updateStatement=connectDB.prepareStatement(query);
			updateStatement.setBytes(1,image);
			updateStatement.executeUpdate();
			updateStatement.close();
			connectDB.close();
			Send("Finish");
			temp_string=MessageReceive();
		}	
	}
	
	public  void sendImage(byte []imageArray) {
		try {
			byteOutputStream=new ByteArrayOutputStream();
			byteOutputStream.write(imageArray,0,imageArray.length);
			System.out.println("Size send:"+imageArray.length);
			byte []size=ByteBuffer.allocate(4).putInt(imageArray.length).array();
			clientDataOutputStream.write(size);
			clientDataOutputStream.write(byteOutputStream.toByteArray());
			byteOutputStream.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	}
}
