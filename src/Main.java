import java.io.IOException;

public class Main {

	public static void main(String[] args){

		NetworkManager networkManager;
		if(args.length > 0){
			if(args[0].equals("-s")){
				try {
					networkManager = new NetworkManager(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			try {
				networkManager = new NetworkManager(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
