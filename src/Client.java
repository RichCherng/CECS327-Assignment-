import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {


	private int 		mPort;
	private String 		mServerName;

	public Client(String pServerName, int port){
		mPort 		= port;
		mServerName = pServerName;
	}

	public void start(){
		Socket client = null;
		try{
			System.out.println("Connecting on port " + mPort);
			client = new Socket(mServerName, mPort);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF("Hello from " + client.getLocalSocketAddress());
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);

			System.out.println("Server: " + in.readUTF());
			while(true){

			}

		} catch(IOException e){
			e.printStackTrace();
		} finally{
			try {
				System.out.println("closing...");
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		Client client = new Client("localhost", 4524);
		client.start();
	}
}
