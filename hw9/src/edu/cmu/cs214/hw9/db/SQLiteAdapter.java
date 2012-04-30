package edu.cmu.cs214.hw9.db;


import java.sql.*;

import resources.Constants;


public class SQLiteAdapter {
	protected Connection conn;
	
	public SQLiteAdapter() throws Exception {
		Class.forName(Constants.JDBC_NAME);
		
		conn = DriverManager.getConnection("jdbc:sqlite:15214.db");
	}
	
	public void createTables(){
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("CREATE TABLE " + Constants.USERS_TABLE + " (id integer PRIMARY KEY, email varchar(50), password varchar(50), salt integer);");
		} catch (SQLException e) {
		}
		
		return;
	}
	
	public ResultSet select(String statement) {
		try {
			
			Statement st = conn.createStatement();
			return st.executeQuery(statement);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null; 	
	}
	
	public void update(String statement){
		try {
			
			Statement st = conn.createStatement();
			st.executeUpdate(statement);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
