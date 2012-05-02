package edu.cmu.cs214.hw9.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.cmu.cs214.hw9.resources.Constants;

public class FriendRequestDAO extends SQLiteAdapter{
	
	public FriendRequestDAO(String url) throws Exception{
		super(url);
	}
	
	/**
	 * Create a new FriendRequest.
	 * @param toEmail email address of User being sent request
	 * @param fromEmail email address of User sending request
	 * @return true if FriendRequest was created
	 */
	public boolean createFriendRequest(String toEmail, String fromEmail)
	{
		// TODO add lookup here to see if link already exists
		FriendRequest f = new FriendRequest(toEmail, fromEmail);
		PreparedStatement ps;
		String statement = "INSERT INTO " + Constants.FRIEND_REQUEST_TABLE + " (toEmail, fromEmail) VALUES (?, ?)";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, f.getToEmail());
			ps.setString(2, f.getFromEmail());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	/**
	 * Remove an existing FriendRequest.
	 * @param toEmail email address of User being sent request
	 * @param fromEmail email address of User sending request
	 * @return true if FriendRequest was removed
	 */
	public boolean removeFriendRequest(String toEmail, String fromEmail)
	{
		FriendRequest f = new FriendRequest(toEmail, fromEmail);
		PreparedStatement ps;
		String statement = "DELETE FROM " + Constants.FRIEND_REQUEST_TABLE + " WHERE toEmail=? AND fromEmail=?;";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, f.getToEmail());
			ps.setString(2, f.getFromEmail());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	/**
	 * Get all the Users who are requesting friendship with User with
	 * email argument
	 * @param email find people friend requesting User with this email
	 * @return ArrayList of Users
	 */
	public ArrayList<User> friendRequestsOf(String email)
	{
		ArrayList<User> ret = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			UserDAO userDAO = new UserDAO(url); // allow us to get access to User table

			String statement = "SELECT * FROM " + Constants.FRIEND_REQUEST_TABLE + " WHERE toEmail=?;";
			ps = conn.prepareStatement(statement);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()){
				User u = userDAO.findUser(rs.getString("fromEmail")).get(0);
				ret.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try{
            	if(rs != null){
            		rs.close();
            	}
            } catch (SQLException e){
            }
        }
		
		return ret;

	}


}
