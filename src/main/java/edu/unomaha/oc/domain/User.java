package edu.unomaha.oc.domain;

public class User {
	private long id;
	private String username;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	public User(long id, String username) {
		this.id = id;
		this.username = username;
	}
	public User() {
		
	}
	
	
}
