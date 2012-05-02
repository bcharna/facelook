package edu.cmu.cs214.hw9.db;


public class Subscription {

	private int id;
	private String toEmail, fromEmail;
	

	/**
	 * Create a new Subscription.
	 * @param id row id
	 * @param toEmail email address of User subscribed to
	 * @param fromEmail email address of User subscribing
	 */
	public Subscription(int id, String toEmail, String fromEmail) {
		this.setId(id);
		this.setToEmail(toEmail);
		this.setFromEmail(fromEmail);
	}
	
	/**
	 * Create a new Subscription.
	 * @param toEmail email address of User subscribed to
	 * @param fromEmail email address of User subscribing
	 */
	public Subscription(String toEmail, String fromEmail) {
		this.setToEmail(toEmail);
		this.setFromEmail(fromEmail);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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



}
