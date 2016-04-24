import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class ClickThread extends Thread {
	static ServerSocket serverSocket; // The socket that the client originally connected to
	protected Socket socket; // The socket that the client and server can communicate on
	private PrintWriter out; // The output stream which the server can write to
	
	public ClickThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.out = null;
    }
	
	public void run() {
		InputStream inp = null;
		BufferedReader in = null;
		try {
			inp = socket.getInputStream();
			in = new BufferedReader(new InputStreamReader(inp));
  			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
            return;
        }
		try {
			while (true) {
				String message = in.readLine();
				System.out.println("Message: " + message);
				if (message != null) {
					if (message.length() > 3 && message.substring(0,3).equals("GET")) {
						// GET REQUEST FOR index.html
						// store the url Requested
						String urlRequested = message.substring(4);
						int space = urlRequested.indexOf(' ');
						urlRequested = urlRequested.substring(0, space);
						if (urlRequested.equals("/")) {
							//urlRequested = System.getProperty("user.dir") + "\\src\\" + "index.html";
							urlRequested="index.html";
						}
						else {
							//urlRequested = System.getProperty("user.dir") + "\\src\\" + urlRequested;
						}
						Path f = FileSystems.getDefault().getPath("src", urlRequested);
						String r_Message;
						if (f != null) {
							// start composing message of the file contents
							r_Message = "HTTP/1.x 200 OK\nContent-Type: text/html\n\n";
							Charset charset = Charset.forName("US-ASCII");

							try {
								BufferedReader reader =  Files.newBufferedReader(f, charset);
								String read = null;
								while((read = reader.readLine()) != null) {
									System.out.println(read);
									r_Message += read;
								}
							} catch (IOException x) {
								System.err.format("IOException: %s%n", x);
							}
//							FileInputStream fIn = new FileInputStream(f);
/*
							String r;
							while ((r = fIn.readLine()) != null) {
								r_Message += r;
							}
						}
*/
						}
						else {
							r_Message="HTTP/1.0 404FileNotFound\nContent-type: text/plain\n\nCould not find the specified URL. The server returned an error.";
						}
						System.out.println(r_Message);
						out.println(r_Message);
					}
					else {
						// OTHER POST REQUEST HANDLING
						
						if() {
							// The user clicked
						}
						else {
							// The client is asking for view update
						}
					}
				}
				else {
					break;
				}
			}
			socket.close();
			System.out.println("Closing Socket ");
		} catch (IOException e) {
			return;
		}
	}
}