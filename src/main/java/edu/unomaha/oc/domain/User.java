package edu.unomaha.oc.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	private long id;
	private String username;
	private String email;
	private String description;
	private int facebookId;
	private String password;
	private boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;
	
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getFacebookId() {
		return facebookId;
	}
	
	public void setFacebookId(int facebookId) {
		this.facebookId = facebookId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	
	public User(long id, String username, String email, String description, 
				int facebookId, String password, boolean enabled) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.description = description;
		this.facebookId = facebookId;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User() {
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
}
