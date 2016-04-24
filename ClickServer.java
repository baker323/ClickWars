//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.Closeable;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetAddress;
import java.net.Socket;
//import java.net.SocketAddress;
//import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.util.Date;

public class ClickServer { 

	static int portNumber = 8080;
	static ServerSocket serverSocket;
	int click_topleft = 0;
	int click_topright = 0;
	int click_bottomleft = 0;
	int click_bottomright = 0;
	
	public static void main(String[] args) { 

      Start_Server();
      /*
      try {
    	  serverSocket.close();
      }
      catch (Exception e) {
    	  System.out.println("Didn't close server socket");
      }
	  */
	}

	public static void Start_Server() {
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server is running");
	       	while (true) {
	       		//Repeat for 1 minute
	       		long start = System.currentTimeMillis();
	       		long diff = 60000;
	       		while(System.currentTimeMillis() - start < diff) {
		       		try {
			       		clientSocket = serverSocket.accept();
			       		System.out.println("Client Connected");
		       		} catch (IOException e) {
		       			System.out.println("Something's wrong here");
		       		}
		       		ClickThread clickThread = new ClickThread(clientSocket);
		       		clickThread.start();
	       		}
	       		// Repeat for 1 minute, exit loop
	       		// Send Clients the winner / end of game
	       		
	       		// Put loop here to prepare for next game
	       		
	       		// reset time to 0.
	       		}
	       	}
		} catch (IOException e) {
			System.out.println("ServerSocket(portNumber) failed");
	    }
	}
	

}