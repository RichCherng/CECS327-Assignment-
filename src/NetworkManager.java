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
	private TokenManager aTM 			= null;
	private int PORT = 4524;

	public NetworkManager(boolean pServer) throws IOException{

		if(pServer){
			/** Start off as Server **/
			serverSocket = new ServerSocket(PORT);
			serverSocket.setSoTimeout(10000);
			acceptConnection();

			System.out.println("Server listening on port " + serverSocket.getLocalPort());
		} else {
			/** Start off as client **/
			System.out.println("Connecting on port " + PORT);
			socket = new Socket("localhost", PORT);
			System.out.println("Connected to " + socket.getRemoteSocketAddress());

		}

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());
	}

	private void acceptConnection() throws IOException{
		socket = serverSocket.accept();
		System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
	}

	public void run(){
		while(true){
			try {
				String request = recv();
				System.out.println("Receive: " + request);

				if(request.equals("REQUEST_TOKEN")){
					aTM.remoteRequestToken();
				} else if(request != null){
					// recv token
					System.out.println("Got token");
					aTM.setToken(request);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				try {
					if(socket != null){
						socket.close();
					}

					if(serverSocket != null){
						serverSocket.close();
					}
					break;
				} catch (IOException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				}

			} finally{

			}
		}
	}

	private String recv() throws IOException{
		return in.readLine();
	}

	private void send(String data){
		out.println(data);
		out.flush();
	}

	public void requestToken(){
		send("REQUEST_TOKEN");
	}

	public void sendToken(String pToken){
		send(pToken);
	}

	public void setTokenManager(TokenManager pTM){
		aTM = pTM;
	}

}
