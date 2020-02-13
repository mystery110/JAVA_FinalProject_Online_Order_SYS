package serverDemo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class SendProfile implements Runnable{
	private PrintStream clientPS;
	private DataOutputStream clientDataOutputStream;
	private String username,password;
	private Scanner clientInput;
	
	public SendProfile(PrintStream clientPS_func,DataOutputStream clientDataOutputStream_func,String name_func,String password_func,Socket client_func) {
		clientDataOutputStream=clientDataOutputStream_func;
		clientPS=clientPS_func;
		username=name_func;
		password=password_func;
		try {
			clientInput=new Scanner(client_func.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public synchronized void run() {
		String []profile_info=new String[5];
		UpdateDB profile=new UpdateDB();
		boolean get=profile.GetProfile(username,password, profile_info);
		System.out.println(get);
		if(get) {
			byte []image=profile.GetImage(username);
			try {
				clientPS.flush();
				if(clientInput.nextLine().equalsIgnoreCase("Here")) {}
				ByteArrayOutputStream baos=new ByteArrayOutputStream(image.length);
				baos.write(image,0,image.length);
				System.out.println("Size sent:"+image.length);
				byte []size=ByteBuffer.allocate(4).putInt(image.length).array();
				clientDataOutputStream.write(size);
				clientDataOutputStream.write(baos.toByteArray());
				clientDataOutputStream.flush();
				
				clientPS.println(profile_info[0]);
				clientPS.println(profile_info[1]);
				clientPS.flush();
				
				clientPS.println(profile_info[2]);
				clientPS.println(profile_info[3]);
				clientPS.println(profile_info[4]);
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
	}

}
