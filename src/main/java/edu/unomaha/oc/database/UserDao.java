package edu.unomaha.oc.database;

import java.util.List;

import edu.unomaha.oc.domain.User;

public interface UserDao {

	public User readUser(int id);
	
	public User readUserWithPass(int id);
	
	public List<User> searchByUsername(String username);
	
	public Number createUser(User user);
	
	public void updateUser(int id, User user);
	
	public void updateUserPassword(int id, String password);
	
	public void deleteUser(int id);
}
