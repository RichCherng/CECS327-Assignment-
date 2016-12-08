
public class Worker extends Thread{

	private int id;
	private TokenManager aTM;
	private String token;
	public Worker(int pID, TokenManager pTM){
		id = pID;
		aTM = pTM;
	}

	public void run(){
		for(int i = 0; i < 100; i++){
			// Request for local token manager
			aTM.requestToken(this);

			// wait for token to be allocated by local TokenManager
			while(token == null){
//				System.out.println(id + " : " + token);
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			System.out.println(token);

			// return token to local Token manager
			aTM.returnToken(token);
			token = null;
			// sleep for 50 msec
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(id + " : " + i);
		}
	}

	public void setToken(String pToken){
		token = pToken;
	}
}
