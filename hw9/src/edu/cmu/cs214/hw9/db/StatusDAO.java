package edu.cmu.cs214.hw9.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.cmu.cs214.hw9.resources.Constants;

public class StatusDAO extends SQLiteAdapter{
	
	public StatusDAO(String url) throws Exception{
		super(url);
	}
	
	/**
	 * Create a new status for User with email of email argument
	 * @param email owner of this Status
	 * @param text the text with this Status
	 * @param date date Status was created
	 */
	public boolean createStatus(String email, String text, Date date)
	{
		Status s = new Status(email, text, date);
		PreparedStatement ps;
		String statement = "INSERT INTO " + Constants.STATUS_TABLE + " (email, text, date) VALUES (?, ?, ?)";
		try{
			ps = conn.prepareStatement(statement);
			ps.setString(1, s.getEmail());
			ps.setString(2, s.getText());
			ps.setDate(3, (java.sql.Date) s.getDate());
			ps.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;

	}
	
	/**
	 * Get all of the statuses belonging to User with email of
	 * email argument
	 * @param email get statuses of this User's email.
	 * @return ArrayList of Statuses
	 */
	public ArrayList<Status> statusesOf(String email)
	{
		ArrayList<Status> ret = new ArrayList<Status>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			String statement = "SELECT * FROM " + Constants.STATUS_TABLE + " WHERE email=?;";
			ps = conn.prepareStatement(statement);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			while(rs.next()){
				Status s = new Status(rs.getString("email"), rs.getString("text"), rs.getDate("date"));
				ret.add(s);
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
