package edu.cmu.cs214.hw9.db;

public class FriendRequest {
	private int id;
	private String toEmail, fromEmail;
	
	/**
	 * Create a new FriendRequest.
	 * @param id row id
	 * @param toEmail email address of User being sent request
	 * @param fromEmail email address of User sending request
	 */
	
	public FriendRequest(int id, String toEmail, String fromEmail) {
		this.id = id;
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
	}
	
	/**
	 * Create a new FriendRequest.
	 * @param toEmail email address of User being sent request
	 * @param fromEmail email address of User sending request
	 */
	public FriendRequest(String toEmail, String fromEmail) {
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
