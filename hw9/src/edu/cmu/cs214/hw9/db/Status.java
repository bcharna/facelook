package edu.cmu.cs214.hw9.db;

import java.util.Date;

public class Status {
	private int id;
	private String email, text;
	private Date date;
	
	/**
	 * Create a new status for User with email of email argument
	 * @param id row id
	 * @param email owner of this Status
	 * @param text the text with this Status
	 * @param date date Status was created
	 */
	public Status(int id, String email, String text, Date date) {
		this.setId(id);
		this.setEmail(email);
		this.setText(text);
		this.setDate(date);
	}
	
	/**
	 * Create a new status for User with email of email argument
	 * @param email owner of this Status
	 * @param text the text with this Status
	 * @param date date Status was created
	 */
	public Status(String email, String text, Date date) {
		this.setEmail(email);
		this.setText(text);
		this.setDate(date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
