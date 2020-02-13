package serverDemo;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class InitialDB implements Runnable{

	private Connection connectDB;
	private ByteArrayOutputStream byteOutputStream;
	private String foodName,foodDescription;
	private int foodPrice,foodID;
	PreparedStatement updateStatement;
	private PrintStream clientPS;
	private DataOutputStream clientDataOutputStream;
	private Socket client;
	private BufferedReader clientInput;
	
	public  InitialDB(Socket client_func) throws IOException {
		client=client_func;
		clientPS=new PrintStream(client.getOutputStream());
		clientDataOutputStream=new DataOutputStream(client.getOutputStream());
		clientInput=new BufferedReader(new InputStreamReader( client.getInputStream()));
	}
	
	public void run(){
		String query="SELECT * FROM Food";
		connectDB=SqlConnection.dbConnector("Food");
		byte []imageArr=null;
		if(clientDataOutputStream==null) {
			System.out.println("NULL");
		}		
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			ResultSet dataRetrive=stmt.executeQuery();
			while(dataRetrive.next()) {
				clientPS.println("true");
				clientDataOutputStream.flush();
				foodID=dataRetrive.getInt(1);
				clientPS.println(foodID);
				clientDataOutputStream.flush();
				if(String.valueOf(clientInput.readLine()).equalsIgnoreCase("finish")) {}
				imageArr=dataRetrive.getBytes("Photo");
				ByteArrayOutputStream baos=new ByteArrayOutputStream(imageArr.length);
				System.out.println("Size send:"+imageArr.length);
				byte []size=ByteBuffer.allocate(4).putInt(imageArr.length).array();
				baos.write(imageArr,0,imageArr.length);
				clientDataOutputStream.write(size);
				clientDataOutputStream.write(baos.toByteArray());
				clientDataOutputStream.flush();
				foodName=dataRetrive.getString("Name");
				clientPS.println(foodName);
				clientDataOutputStream.flush();
				foodPrice=dataRetrive.getInt("Price");
				clientPS.println(foodPrice);
				clientDataOutputStream.flush();
				foodDescription=dataRetrive.getString("Description");
				clientPS.println(foodDescription);	
				clientDataOutputStream.flush();
				if(String.valueOf(clientInput.readLine()).equalsIgnoreCase("finish")) {}
			}
			clientPS.println("END");
			connectDB.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	}

