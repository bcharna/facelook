package edu.cmu.cs214.hw9.db;

/**
 * Friendship represents a link between two Users in the system that are
 * 'friends', at least for now...
 * @author bcharna
 *
 */
public class Friendship {
	private int id;
	private String user1Email, user2Email;
	
	/**
	 * Create a new Friendship 'link' with user1Email on one side and 
	 * user2Email on the other.
	 * @param id row id
	 * @param user1Email of friend 1
	 * @param user2Email of friend 2
	 */
	public Friendship(int id, String user1Email, String user2Email) {
		this.id = id;
		this.setUser1Email(user1Email);
		this.setUser2Email(user2Email);
	}
	
	
	/**
	 * Create a new Friendship 'link' with user1Email on one side and 
	 * user2Email on the other.
	 * @param user1Email id of friend 1
	 * @param user2Email id of friend 2
	 */
	public Friendship(String user1Email, String user2Email) {
		this.setUser1Email(user1Email);
		this.setUser2Email(user2Email);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getUser1Email() {
		return user1Email;
	}


	public void setUser1Email(String user1Email) {
		this.user1Email = user1Email;
	}


	public String getUser2Email() {
		return user2Email;
	}


	public void setUser2Email(String user2Email) {
		this.user2Email = user2Email;
	}


}
