
package edu.cmu.cs214.hw9.resources;

public class Constants {
	public static final int SERVER_PORT = 15214;
	
	public static final int MAX_TABLES = 50;
	
	public static final String JDBC_NAME = "org.sqlite.JDBC";
	public static final String DB_CONNECTION = "jdbc:sqlite:db/test.db";
	
	public static final String USERS_TABLE = "users";
	public static final String FRIENDSHIP_TABLE = "friendships";
	
	
	public static final int SHARD_COUNT = 3;
	public static final int TIMEOUT = 30000;
}
