package edu.cmu.cs214.hw9.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.cmu.cs214.hw9.resources.Constants;



public class UserDAO extends SQLiteAdapter {

	/**
	 * Create a new adapter to access User from DB in OO way
	 * @param url path to DB file
	 * @throws Exception
	 */
	public UserDAO(String url) throws Exception{
		super(url);
	}
	
	public ArrayList<User> allUsers(){
		String statement = "SELECT * FROM " + Constants.USERS_TABLE;
		ArrayList<User> ret = new ArrayList<User>();
		ResultSet rs = select(statement, conn);
		
		try {
			while(rs.next()){
				ret.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("salt")));
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
	
	public ArrayList<User> findUser(String email){
		ArrayList<User> ret = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			String statement = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE email=?;";
			ps = conn.prepareStatement(statement);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ret.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("salt")));
			}
		} catch (SQLException e) {
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
	
	/**
	 * Creates a new user into system.  Row will automatically be sharded
	 * into a DB to balance load.
	 * @param email User's email
	 * @param password User's password
	 * @return true if User was created
	 */
	public synchronized boolean createUser(String name, String email, String password){
		
		ArrayList<User> lookup = findUser(email);
		if(lookup.size() != 0){
			return false;
		}
		
		
		User u = new User(name, email, password, false);
		PreparedStatement ps;
		String statement = "INSERT INTO " + Constants.USERS_TABLE + " (name, email, password, salt) VALUES (?, ?, ?, ?)";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, u.getName());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getPassword());
			ps.setInt(4, u.getSalt());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
//	public boolean incScore(String username){
//		ArrayList<User> lookup = findUser(username);
//		User u = lookup.get(0);
//		if(u == null){
//			return false;
//		}
//		
//		PreparedStatement ps;
//		String statement = "UPDATE " + Constants.USERS_TABLE + " SET score=? WHERE username=?";
//		try{
//			ps = conn.prepareStatement(statement);
//			ps.setInt(1, u.getScore() + 1);
//			ps.setString(2, username);
//			ps.executeUpdate();
//		} catch(SQLException e){
//			return false;
//		}
//		
//		return true;
//	}
	
}

