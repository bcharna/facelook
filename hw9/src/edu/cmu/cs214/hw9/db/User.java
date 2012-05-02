package edu.cmu.cs214.hw9.db;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class User {
	private int id;
	private int salt;
	private String name;
	private String email;
	private String password;
	
	/**
	 * Create a new user with some details
	 * @param id primary key of this row in DB
	 * @param name name of User
	 * @param email email of User
	 * @param password password of User
	 * @param salt salt of User
	 */
	public User(int id, String name, String email, String password, int salt){
		this.id = id;
		this.setName(name);
		this.email = email;
		this.password = password;
		this.salt = salt;
	}
	
	/**
	 * Create a new user with some details
	 * @param name name of User
	 * @param email email of User
	 * @param password password of User
	 * @param salt salt of User
	 */
	public User(String name, String email, String password, boolean hashed){
		this.setName(name);
		this.email = email;
		if(hashed){
			this.password = password;
		} else {
			this.salt = newSalt();
			this.password = hash(password);
		}
	}
	
	public int getID(){
		return id;
	}
	
	public int getSalt(){
		return salt;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}
	
	
	public boolean checkPassword(String password) {
		String curPassword = this.password;
		
		return curPassword.equals(hash(password));
	}
	
	private String hash(String clearPassword) {
		if (salt == 0) return null;

		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();
		
		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
