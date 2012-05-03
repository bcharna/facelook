
package edu.cmu.cs214.hw9.resources;

public class Constants {
	public static final int SERVER_DISPATCH_PORT = 15214;
	public static final int SERVER_1_PORT = 15215;
	public static final int SERVER_2_PORT = 15216;
	public static final int SERVER_3_PORT = 15217;
	
	
	public static final String JDBC_NAME = "org.sqlite.JDBC";
	public static final String DB_1_URL = "jdbc:sqlite:db1.db";
	public static final String DB_2_URL = "jdbc:sqlite:db2.db";
	public static final String DB_3_URL = "jdbc:sqlite:db3.db";
	
	public static final String USER_TABLE = "users";
	public static final String FRIENDSHIP_TABLE = "friendships";
	public static final String FRIEND_REQUEST_TABLE = "friend_requests";
	public static final String STATUS_TABLE = "statuses";
	public static final String SUBSCRIPTION_TABLE = "subcriptions";

	
	public static final int SHARD_COUNT = 3;
	public static final int TIMEOUT = 30000;

}
