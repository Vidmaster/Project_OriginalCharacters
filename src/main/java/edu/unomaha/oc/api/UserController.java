package edu.unomaha.oc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.UserDao;
import edu.unomaha.oc.domain.ServiceResponse;
import edu.unomaha.oc.domain.User;
import edu.unomaha.oc.utilities.AuthUtilities;

@RestController
public class UserController {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthUtilities auth;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam(value="username") String username) {
		List<User> users = userDao.searchByUsername(username); 
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		User user = userDao.readUser(id);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users", method=RequestMethod.POST)
	public ServiceResponse createUser(@RequestParam(value="username") String username, @RequestParam(value="password") String password, 
						@RequestParam(value="email") String email, @RequestParam(value="description", defaultValue="") String description) {
		logger.debug("createuser(): username=" + username);
		
		try {
			String hashedPass = auth.encode(password);
			User user = new User(-1, username, email, description, -1, hashedPass, true);
			Number id = userDao.createUser(user);
			
			return new ServiceResponse("Created user ID " + id, true);
		} catch (Exception ex) {
			logger.error("Error creating user: " + ex.getMessage());
			return new ServiceResponse("Unable to create user", false);
		}
	}
	
	@RequestMapping(value="/api/users/{id}", method=RequestMethod.PUT)
	public void updateUser(@PathVariable(value="id") int id, @RequestParam(value="password", defaultValue="") String password, 
			@RequestParam(value="email", defaultValue="") String email, @RequestParam(value="description", defaultValue="") String description) {
		
		if (auth.isAuthorized(id)) {
			User user = userDao.readUser(id);
			if ( !(password.isEmpty() || auth.matches(password, user.getPassword() ) ) ) {
				user.setPassword(auth.encode(password));
			}
			user.setEmail(email);
			user.setDescription(description);
			
			userDao.updateUser(id, user);
		}
	}
	
	@RequestMapping(value="/api/user/{id}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable(value="id") int id) {
		if (auth.isAuthorized(id)) {
			userDao.deleteUser(id);
		}
	}
}
