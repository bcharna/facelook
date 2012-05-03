package edu.cmu.cs214.hw9.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import edu.cmu.cs214.hw9.db.User;
import edu.cmu.cs214.hw9.db.UserDAO;
import edu.cmu.cs214.hw9.json.JSONException;
import edu.cmu.cs214.hw9.json.JSONObject;
import edu.cmu.cs214.hw9.json.JSONTokener;
import edu.cmu.cs214.hw9.resources.Constants;

/**
 * This thread accepts connections for logging in to the Facelook 
 * system.  Upon successfully validating credentials,
 * message will be sent back to client telling them the URL
 * and port of the server to connect to (the one with the
 * user's data)
 * @author bcharna
 *
 */
public class DispatchServerThread extends Thread {

	private boolean loggedIn = false;
	private Socket mySocket;
	PrintWriter out;
	BufferedReader in;

	/**
	 * Create a DispatchServerThread with socket s
	 * @param s socket to use
	 * @throws Exception NullPointerException
	 */
	public DispatchServerThread(Socket s) throws Exception {
		if (s == null) {
			throw new NullPointerException();
		}
		mySocket = s;
		mySocket.setSoTimeout(600000);
	}
	
	public void run()
	{
		try
		{
			out = new PrintWriter(mySocket.getOutputStream(),
					true);
			in = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
			
			while(true)
			{
				String msg = in.readLine();
				JSONObject o;
				if(msg.indexOf("REGISTER") == 0)
				{
					final int len = "REGISTER".length();
					o = new JSONObject(new JSONTokener(msg.substring(len)));
					String email = o.getString("email");
					int shardNumber = email.hashCode() % Constants.SHARD_COUNT;
					
					JSONObject serverInfo = new JSONObject();
					serverInfo.put("serverURL", "localhost");
					switch (shardNumber)
					{
						case 0:
							serverInfo.put("port", Constants.SERVER_1_PORT);
							serverInfo.put("sqlURL", Constants.DB_1_URL);
							break;
						case 1:
							serverInfo.put("port", Constants.SERVER_2_PORT);
							serverInfo.put("sqlURL", Constants.DB_2_URL);
							break;
						case 2:
							serverInfo.put("port", Constants.SERVER_3_PORT);
							serverInfo.put("sqlURL", Constants.DB_3_URL);
							break;
					}
					//out.println("REGISTER_GOTO " + serverInfo);
					

				}
				else if(msg.indexOf("LOGIN") == 0)
				{
					final int len = "LOGIN".length();
					o = new JSONObject(new JSONTokener(msg.substring(len)));
					String email = o.getString("email");
					String password = o.getString("password");
					int shardNumber = email.hashCode() % Constants.SHARD_COUNT;

					JSONObject serverInfo = new JSONObject();
					serverInfo.put("URL", "localhost");
					switch (shardNumber)
					{
						case 0:
							serverInfo.put("port", Constants.SERVER_1_PORT);
							serverInfo.put("sqlURL", Constants.DB_1_URL);
							break;
						case 1:
							serverInfo.put("port", Constants.SERVER_2_PORT);
							serverInfo.put("sqlURL", Constants.DB_2_URL);
							break;
						case 2:
							serverInfo.put("port", Constants.SERVER_3_PORT);
							serverInfo.put("sqlURL", Constants.DB_3_URL);
							break;
					}
					boolean loggedIn = login(email, password, serverInfo);

					if(loggedIn)
			        	//tell client to disconnect from me and connect to
			        	//server with serverInfo
			        	out.println("GOTO " + serverInfo);
					else
			        	out.println("INVALID"); //client must try again


				}
				else if(msg.indexOf("END") == 0)
				{
					break; // break out of loop and close connection
				}
			}
			out.close();
			in.close();
			mySocket.close();			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * Login to the system on server with serverInfo and User
	 * credentials
	 * @param email email of User logging in
	 * @param password password of User logging in
	 * @param serverInfo information of server that handles this User
	 * @return true if successful login
	 */
	public boolean login(String email, String password, JSONObject serverInfo){

		try {
			Socket otherSocket = new Socket(serverInfo.getString("URL"), serverInfo.getInt("port"));
			PrintWriter otherOut = new PrintWriter(otherSocket.getOutputStream(), true);
	        BufferedReader otherIn = new BufferedReader(new InputStreamReader(otherSocket.getInputStream()));
	        
	        JSONObject userInfo = new JSONObject();
	        userInfo.put("email", email);
	        userInfo.put("password", password);
	        otherOut.println("LOGIN " + userInfo);
	        
	        String response = otherIn.readLine();
	        
	        otherSocket.close();
	        otherOut.close();
	        otherIn.close();
	        
	        if(response.equals("LOGGED_IN"))
	        	return true;
	        else
	        	return false;
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;

	}

}
