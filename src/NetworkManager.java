import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager extends Thread{

	private ServerSocket serverSocket	= null;
	private Socket socket 				= null;
	private BufferedReader in 			= null;
	private PrintStream out 			= null;
	private int PORT = 4524;

	public NetworkManager(boolean pServer) throws IOException{

		if(pServer){
			serverSocket = new ServerSocket(PORT);
			serverSocket.setSoTimeout(1000);
			acceptConnection();

			System.out.println("Server listening on port " + serverSocket.getLocalPort());
		} else {
			System.out.println("Connecting on port " + PORT);
			socket = new Socket("localhost", PORT);
			System.out.println("Connected to " + socket.getRemoteSocketAddress());

		}

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
	}

	private void acceptConnection() throws IOException{
		while(true){
			socket = serverSocket.accept();
			System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
		}
	}

}
