package edu.cmu.cs214.hw9.db;


import java.sql.*;

import edu.cmu.cs214.hw9.resources.Constants;



public class SQLiteAdapter {
	protected Connection conn1, conn2, conn3;
	
	public SQLiteAdapter() throws Exception {
		Class.forName(Constants.JDBC_NAME);
		
		conn1 = DriverManager.getConnection("jdbc:sqlite:15214.db");
		conn2 = DriverManager.getConnection("jdbc:sqlite:15214.db");
		conn3 = DriverManager.getConnection("jdbc:sqlite:15214.db");
	}
	
	public void createTables(){
		Statement stat1;
		Statement stat2;
		Statement stat3;
		try {
			stat1 = conn1.createStatement();
			stat1.executeUpdate("CREATE TABLE " + Constants.USERS_TABLE + " (id integer PRIMARY KEY, email varchar(50), password varchar(50), salt integer);");

			stat2 = conn2.createStatement();
			stat2.executeUpdate("CREATE TABLE " + Constants.USERS_TABLE + " (id integer PRIMARY KEY, email varchar(50), password varchar(50), salt integer);");

			stat3 = conn3.createStatement();
			stat3.executeUpdate("CREATE TABLE " + Constants.USERS_TABLE + " (id integer PRIMARY KEY, email varchar(50), password varchar(50), salt integer);");
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
