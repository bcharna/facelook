package edu.cmu.cs214.hw9.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.cmu.cs214.hw9.resources.Constants;

public class FriendshipDAO extends SQLiteAdapter{

	
	public FriendshipDAO(String url) throws Exception{
		super(url);
	}

	
	/**
	 * Get all friendships in this shard.
	 * @return ArrayList of Friendships
	 */
	public ArrayList<Friendship> allFriendships(){
		String statement = "SELECT * FROM " + Constants.FRIENDSHIP_TABLE;
		ArrayList<Friendship> ret = new ArrayList<Friendship>();
		ResultSet rs = select(statement, conn);

		try {
			while(rs.next()){
				ret.add(new Friendship(rs.getInt("id"), rs.getString("user1Email"), rs.getString("user2Email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            try{
                rs.close();
            } catch (SQLException e){
            }
        }
		return ret;
	}
	

	/**
	 * See if User with user1Email is friends with User with
	 * user2Email. Note that order of arguments does not
	 * matter.  Therefore, areFriends(a,b) == areFriends(b,a)
	 * @param user1Email email of User 1
	 * @param user2Email email of User 2
	 * @return true if Users are friends
	 */
	public boolean areFriends(String user1Email, String user2Email)
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String statement = "SELECT * FROM " + Constants.FRIENDSHIP_TABLE +
				" WHERE user1Email=? AND user2Email=? OR user1Email=? AND user2Email=?";
		try {
			ps = conn.prepareStatement(statement);
			ps.setString(1, user1Email);
			ps.setString(2, user2Email);
			ps.setString(3, user2Email);
			ps.setString(4, user1Email);
			rs = ps.executeQuery();
			int size = rs.getFetchSize();
			if (size > 0) return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
				
	}
	
	

	/**
	 * Remove Friendship 'link' with user1Email on one side and 
	 * user2Email on the other.  Note that order of arguments does not
	 * matter.  Therefore, removeFriendship(a,b) == removeFriendship(b,a)
	 * @param user1Email of friend 1
	 * @param user2Email of friend 2
	 */
	public boolean removeFriendship(String user1Email, String user2Email)
	{
		Friendship f = new Friendship(user1Email, user2Email);
		PreparedStatement ps;
		String statement = "DELETE FROM " + Constants.FRIENDSHIP_TABLE +
				" WHERE user1Email=? AND user2Email=? OR user1Email=? AND user2Email=?";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, f.getUser1Email());
			ps.setString(2, f.getUser2Email());
			ps.setString(3, f.getUser2Email());
			ps.setString(4, f.getUser1Email());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	
	/**
	 * Create a new Friendship 'link' with user1Email on one side and 
	 * user2Email on the other.
	 * @param user1Email of friend 1
	 * @param user2Email of friend 2
	 */
	public boolean createFriendship(String user1Email, String user2Email)
	{
		// TODO add lookup here to see if link already exists
		Friendship f = new Friendship(user1Email, user2Email);
		PreparedStatement ps;
		String statement = "INSERT INTO " + Constants.FRIENDSHIP_TABLE + " (user1Email, user2Email) VALUES (?, ?)";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, f.getUser1Email());
			ps.setString(2, f.getUser2Email());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get all the Users that are friends with User with email.
	 * @param email of User to find friends of.
	 * @return ArrayList of Users
	 */
	public ArrayList<User> friendsOf(String email)
	{

		ArrayList<User> ret = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			UserDAO userDAO = new UserDAO(url); // allow us to get access to User table

			String statement = "SELECT * FROM " + Constants.FRIENDSHIP_TABLE + " WHERE user1Email=? OR user2Email=?;";
			ps = conn.prepareStatement(statement);
			ps.setString(1, email);
			ps.setString(2, email);
			
			rs = ps.executeQuery();
			while(rs.next()){
				User u = null;
				if (rs.getString("userEmail1").equals(email)) // add other side of Friendship link to list
					u = userDAO.findUser(rs.getString("userEmail2")).get(0);
				else
					u = userDAO.findUser(rs.getString("userEmail1")).get(0);

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
