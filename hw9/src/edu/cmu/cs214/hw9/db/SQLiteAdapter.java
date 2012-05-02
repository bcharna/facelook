package edu.cmu.cs214.hw9.db;


import java.sql.*;

import edu.cmu.cs214.hw9.resources.Constants;



public class SQLiteAdapter {
	protected Connection conn;
	protected String url;
	
	/**
	 * Create a new adapter to access DB
	 * @param url path to DB file
	 * @throws Exception
	 */
	public SQLiteAdapter(String url) throws Exception {
		Class.forName(Constants.JDBC_NAME);
		this.url = url;
		//conn = DriverManager.getConnection("jdbc:sqlite:15214.db");
		conn = DriverManager.getConnection(url);
	}
	
	public void createTables(){
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.USER_TABLE +
					" (id integer PRIMARY KEY, email varchar(50), password varchar(50), salt integer);");
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.FRIEND_REQUEST_TABLE +
					" (id integer PRIMARY KEY, toEmail varchar(50), fromEmail varchar(50));");
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.FRIENDSHIP_TABLE +
					" (id integer PRIMARY KEY, user1Email varchar(50), user2Email varchar(50));");
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.SUBSCRIPTION_TABLE +
					" (id integer PRIMARY KEY, toEmail varchar(50), fromEmail varchar(50));");
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.STATUS_TABLE +
					" (id integer PRIMARY KEY, email varchar(50), text varchar(255), date date, notification boolean);");

		} catch (SQLException e) {
		}
		
		return;
	}
	
	public ResultSet select(String statement, Connection conn) {
		try {
			
			Statement st = conn.createStatement();
			return st.executeQuery(statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null; 	
	}
	
	public void update(String statement, Connection conn){
		try {
			
			Statement st = conn.createStatement();
			st.executeUpdate(statement);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
