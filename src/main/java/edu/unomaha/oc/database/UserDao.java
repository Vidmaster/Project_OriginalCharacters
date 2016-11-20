package edu.unomaha.oc.database;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.unomaha.oc.domain.User;

public interface UserDao {

	public User readUser(int id);
	
	public List<User> searchByUsername(String username);
	
	public Number createUser(String username, String password, String email, String description);
	
	public void updateUser(int id);
	
	public void deleteUser(int id);
}
