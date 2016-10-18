package edu.unomaha.oc.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.UserRowMapper;
import edu.unomaha.oc.domain.User;

@RestController
public class UserController {
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam(value="username") String username) {
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		
		List<User> users = template.query("SELECT id, username, email, description, facebookId, salt, password FROM user WHERE username LIKE '%:username%'", paramMap, new UserRowMapper()); 
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		User user = template.queryForObject("SELECT id, username, email, description, facebookId, salt, password FROM User WHERE id=:id", paramMap, new UserRowMapper());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/users", method=RequestMethod.PUT)
	public void saveUser(@RequestParam(value="newuser") String newuser) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("newuser", newuser);
		
		template.update("INSERT INTO User VALUES (:newuser)", paramMap);
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.POST)
	public void updateUser(@PathVariable(value="id") int id, @RequestParam(value="updates") String updates) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", updates);
		
		template.update("UPDATE User SET (:updates) WHERE id=:id ", paramMap);
	}
	
}
