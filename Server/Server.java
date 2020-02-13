package serverDemo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server  {
	private ServerSocket serverInitSocket=null;
	private Socket clientIncome=null;
	private int port,clientCount=0;
	private ExecutorService pool=null;
	
	
	public Server(int port_func) {
		port=port_func;
		pool=Executors.newFixedThreadPool(5);
	}
	
	public Server() {
		this(1234);
	}
	
	private void StartServer() {
		try {
			serverInitSocket=new ServerSocket(port);	
			System.out.println("Server Booted");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}		
		while (true) {
			try {
				clientIncome=serverInitSocket.accept();
			}
			catch(IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			clientCount++;
			ServerFunc serverFunc=new ServerFunc(this,clientIncome);			
			pool.execute(serverFunc);	
		}
	}

	public static void main(String []args) {
		Server serverobj=new Server(1234);
		serverobj.StartServer();
		System.out.println("Server:Bye Bye");
	}



}
