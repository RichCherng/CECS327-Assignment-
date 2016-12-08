import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenManager extends Thread{

	private int LOCAL = 1, REMOTE = 2;
	private Lock lock_que;
	private Lock lock_token;
	private Queue<Worker> que;
	private Queue<Integer> que_status;
	private String token = null; // Null is unavailable
	private boolean token_remote;

	public TokenManager(){

		que_status 	= new LinkedList<Integer>();
		que 		= new LinkedList<Worker>();
		lock_que	= new ReentrantLock();
		lock_token 	= new ReentrantLock();
	}

	public void run(){

		while(true){
			// If token is returned
				// Flag token is available
//			System.out.println("Manager");
			Worker requester = null;
			int request_status = 0;
			lock_que.lock();
			// If queue not empty or next requester is not known
			if(!que_status.isEmpty()){
				request_status = que_status.remove();
				if(token_remote){
					// Token at remote host
					// send request to remote host
				}
			}
			lock_que.unlock();

//			System.out.println("Request status  " + request_status);
			while(token == null || token_remote || (que_status.isEmpty() && request_status == 0)){
				// Wait for next action
//				System.out.println(token);
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			lock_token.lock();
			// If token is available || next requestor is waiting
			if(token != null && request_status != 0){

				// If requester is local
				if(request_status == LOCAL){
					// Allocate token to requester
					requester = que.remove();
					requester.setToken(token); // Give token
					token = null;
				}

				// If next requester is remote
				if(request_status == REMOTE){
					// Send token to remote host
					token = null;
					token_remote = true;
				}

			}
			lock_token.unlock();
		}
	}

	public void requestToken(Worker w){
		lock_que.lock();
		que.add(w);
		que_status.add(LOCAL);
		lock_que.unlock();
	}

	public void setToken(String pToken){
		token = pToken;
		token_remote = false;
	}

	public void returnToken(String pToken){
		lock_token.lock();
		token = pToken;
		lock_token.unlock();
	}

}
