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
	 * Create a new Friendship 'link' with user1ID on one side and 
	 * user2ID on the other.
	 * @param user1ID id of friend 1
	 * @param user2ID id of friend 2
	 * @return true if Friendship was created
	 */
	public boolean createFriendship(String user1Email, String user2Email)
	{
		// TODO add lookup here to see if link already exists
		Friendship f = new Friendship(user1Email, user2Email);
		PreparedStatement ps;
		String statement = "INSERT INTO " + Constants.FRIENDSHIP_TABLE + " (user1ID, user2ID) VALUES (?, ?)";
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
//	public ArrayList<User> friendsOf(String email)
//	{
//		/*
//		 * notes:
//		 * do not hash based off primary ID
//		 * hash off email => split 0 to max int into
//		 * n sections where n is amount of shards
//		 * have hash fxn map equally into shards
//		 */
//	}

	
}
