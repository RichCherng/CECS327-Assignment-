import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private static int NUM_WORKER = 5;
	public static void main(String[] args){

//		NetworkManager networkManager;
//		if(args.length > 0){
//			if(args[0].equals("-s")){
//				try {
//					networkManager = new NetworkManager(true);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} else {
//			try {
//				networkManager = new NetworkManager(false);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		TokenManager tokenMng = null;
		NetworkManager networkMng = null;
		if(args.length > 0){
			// Start first, get the token
			if(args[0].equals("-s")){
				try {
					// Start socket as listener
					networkMng = new NetworkManager(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tokenMng = new TokenManager(networkMng);
				tokenMng.setToken("1");
			}
		} else {
			// Start socket as client
			try {
				networkMng = new NetworkManager(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Start with no token
			tokenMng = new TokenManager(networkMng);
		}

		// Spawn worker
		ArrayList<Worker> workerList = new ArrayList<Worker>();
		for(int i = 0; i < NUM_WORKER; i++){
			Worker w = new Worker(i, tokenMng);
			w.start();
			workerList.add(w);
		}
		networkMng.setTokenManager(tokenMng);
		networkMng.start();
		tokenMng.start();
//		tokenMng.addWorker()
	}
}
