package edu.cmu.cs214.hw9.server;

import java.net.ServerSocket;

import edu.cmu.cs214.hw9.db.SQLiteAdapter;
import edu.cmu.cs214.hw9.resources.Constants;

public class DispatchServer {
	public static void main(String[] args){

		ServerSocket socket1 = null;
		
		try {
			socket1 = new ServerSocket(Constants.SERVER_DISPATCH_PORT);
			while(true){
				Thread connection = new DispatchServerThread(socket1.accept());
				connection.start();
			}
		} catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
