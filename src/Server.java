import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

	private ServerSocket serverSocket;

	public Server(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
	}

	public void start(){
		Socket server = null;
		while(true){
			System.out.println("Server listening on port " + serverSocket.getLocalPort());
			try{
				server = serverSocket.accept();
				System.out.println("Just coinnected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());

				System.out.println(in.readUTF());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");

				while(true){

				}
			} catch(SocketTimeoutException s){
				System.out.println("Socket timed out");
			} catch (IOException e){
				e.printStackTrace();
				break;
			} finally{
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	public static void main(String[] args){
		Server server = null;
		try {
			server = new Server(4524);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
