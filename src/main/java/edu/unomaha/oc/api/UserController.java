package edu.unomaha.oc.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.database.UserRowMapper;
import edu.unomaha.oc.domain.ServiceResponse;
import edu.unomaha.oc.domain.User;
import edu.unomaha.oc.utilities.AuthorizationUtilities;

@RestController
public class UserController {
	@Autowired
	private DataSource dataSource;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/api/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam(value="username") String username) {
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("username", '%' + username + '%');
		
		List<User> users = template.query("SELECT id, username, email, description, facebookId, password FROM users WHERE username LIKE ':username'", paramMap, new UserRowMapper()); 
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		
		User user = template.queryForObject("SELECT id, username, email, description, facebookId, password FROM users WHERE id=:id", paramMap, new UserRowMapper());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/users", method=RequestMethod.POST)
	public ServiceResponse createUser(@RequestParam(value="username") String username, @RequestParam(value="password") String password, 
						@RequestParam(value="email") String email, @RequestParam(value="description", defaultValue="") String description) {
		logger.debug("createuser(): username=" + username);
		try {
			NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource paramMap = new MapSqlParameterSource();
			
			String hashedPass = AuthorizationUtilities.encode(password);
			paramMap.addValue("username", username);
			paramMap.addValue("password", hashedPass);
			paramMap.addValue("email", email);
			paramMap.addValue("description", description);
			
			template.update("INSERT INTO users (username, password, email, description) VALUES (:username, :password, :email, :description)", paramMap, keyHolder);
			template.update("INSERT INTO authorities (username, authority) VALUES (:username, 'ROLE_USER')", paramMap);
			
			return new ServiceResponse("Success!", true);
		} catch (Exception ex) {
			// TODO: Clean up the error messages here so we don't show sensitive table data
			return new ServiceResponse(ex.getMessage(), false);
		}
	}
	
	@RequestMapping(value="/api/users/{id}", method=RequestMethod.PUT)
	public void updateUser(@PathVariable(value="id") int id, @RequestParam(value="updates") String updates) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("updates", updates);
		
		template.update("UPDATE users SET (:updates) WHERE id=:id ", paramMap);
	}
	
}
